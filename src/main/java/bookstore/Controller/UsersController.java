package bookstore.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.DAO.UserDAO;
import bookstore.Entity.OrdersEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Utils.PasswordUtil;

@Transactional
@Controller
@RequestMapping("/admin1337")
public class UsersController {

	@Autowired
	private SessionFactory factory;

	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/users")
	public String users(ModelMap model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "fullname", required = false) String fullname,
			@RequestParam(value = "role", required = false) String role,
			@RequestParam(value = "enabled", required = false) Integer enabled,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws ParseException {

		List<UsersEntity> listUsers = userDAO.searchUsers(fullname, role, enabled, fromDate, toDate);

		long count = listUsers.size();
		int totalPages = (int) Math.ceil((double) count / size);

		int startIndex = (page - 1) * size;
		int endIndex = Math.min(startIndex + size, listUsers.size());
		List<UsersEntity> paginatedUsers = listUsers.subList(startIndex, endIndex);

		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM RolesEntity");
		List<RolesEntity> roles = query.list();

		model.addAttribute("users", paginatedUsers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("search", fullname);
		model.addAttribute("selectedRole", role);
		model.addAttribute("roles", roles);

		return "users/index";
	}

	@RequestMapping(value = "/user/new", method = RequestMethod.GET)
	public String userNew(ModelMap model) {
		model.addAttribute("user", new UsersEntity());
		model.addAttribute("task", "new");

		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM RolesEntity");
		List<RolesEntity> roles = query.list();

		model.addAttribute("roles", roles);

		return "users/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable("id") Long id, ModelMap model) {
		UsersEntity user = getUserById(id);

		// Initialize the roles collection to avoid LazyInitializationException
		Hibernate.initialize(user.getRoles());

		// Fetch roles for the user
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM RolesEntity");
		List<RolesEntity> roles = query.list();

		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("task", "edit");
		return "users/edit";
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UsersEntity user, @RequestParam("task") String task,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
			@RequestParam(value = "enabled", required = false) Integer enabled, RedirectAttributes redirectAttributes,
			ModelMap model) {
		Session session = factory.getCurrentSession();

		try {
			// Chuẩn hóa tên người dùng để kiểm tra trùng lặp
			String normalizedFullname = user.getFullname().trim().toLowerCase();

			// Kiểm tra tên người dùng trùng lặp
			String hql = "FROM UsersEntity WHERE LOWER(TRIM(fullname)) = :fullname AND id != :userId";
			Query query = session.createQuery(hql);
			query.setParameter("fullname", normalizedFullname);
			query.setParameter("userId", id != null ? id : -1L);
			List<UsersEntity> existingUsers = query.list();

			if (!existingUsers.isEmpty()) {
				model.addAttribute("message", "This fullname is already registered.");
				return "users/edit";
			}

			if ("new".equals(task)) {
				if (getUserByEmail(user.getEmail()) != null) {
					model.addAttribute("message", "This email is already registered.");
					return "users/edit";
				}

				user.setAvatar("resources/images/default-avatar.png");
				String hashedPassword = PasswordUtil.hashPassword("bookstore");
				user.setPassword(hashedPassword);

				Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
				user.setCreated_at(currentDate);
				user.setUpdated_at(currentDate);
				user.setEnabled(1); // Chuyển enabled thành Boolean

				session.save(user);

			} else if ("edit".equals(task)) {
				UsersEntity existingUser = getUserById(id);
				if (existingUser == null) {
					model.addAttribute("message", "User not found.");
					return "redirect:/admin1337/users.htm";
				}

				existingUser.setFullname(user.getFullname());
				existingUser.setEmail(user.getEmail());
				existingUser.setPhone(user.getPhone());
				existingUser.setGender(user.getGender());
				existingUser.setEnabled(enabled);

				Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
				existingUser.setUpdated_at(currentDate);

				UsersEntity emailCheck = getUserByEmail(user.getEmail());
				if (emailCheck != null && !emailCheck.getId().equals(existingUser.getId())) {
					model.addAttribute("message", "This email is already registered.");
					return "users/edit";
				}

				if (roleIds != null) {
					Set<RolesEntity> roles = new HashSet<>();
					for (Long roleId : roleIds) {
						RolesEntity role = (RolesEntity) session.get(RolesEntity.class, roleId);
						if (role != null) {
							roles.add(role);
						}
					}
					existingUser.setRoles(roles);
				} else {
					existingUser.setRoles(new HashSet<>()); // Xóa hết roles nếu không chọn
				}

				session.merge(existingUser);
			}

			redirectAttributes.addFlashAttribute("alertMessage", "User saved successfully!");
			redirectAttributes.addFlashAttribute("alertType", "success");

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("alertMessage", "Error occurred while saving the User.");
			redirectAttributes.addFlashAttribute("alertType", "error");
			return "redirect:/admin1337/users.htm";
		}

		return "redirect:/admin1337/users.htm";
	}

	public UsersEntity getUserByEmail(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		return (UsersEntity) query.uniqueResult();
	}

	@RequestMapping(value = "/user/delete/{id}.htm", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		UsersEntity user = (UsersEntity) session.get(UsersEntity.class, id);
		if (user != null) {
			session.delete(user);
		}
		return "redirect:/admin1337/users.htm";
	}

	@SuppressWarnings("unchecked")
	private List<UsersEntity> listUsers() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT DISTINCT u FROM UsersEntity u LEFT JOIN FETCH u.roles";
		Query query = session.createQuery(hql);
		return query.list();

	}

	private UsersEntity getUserById(Long id) {
		Session session = factory.getCurrentSession();
		return (UsersEntity) session.get(UsersEntity.class, id);
	}
}