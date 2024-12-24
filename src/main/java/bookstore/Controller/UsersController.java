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
import java.util.UUID;

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
import bookstore.Entity.CartsEntity;
import bookstore.Entity.OrdersEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Utils.EscapeHtmlUtil;
import bookstore.Utils.PasswordUtil;
import bookstore.Utils.UUIDUtil;

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

	@RequestMapping(value = "/user/edit/{uuid}", method = RequestMethod.GET)
	public String userEdit(@PathVariable("uuid") String uuid, ModelMap model) {
		UsersEntity user = getUserByUuid(uuid);

		Hibernate.initialize(user.getRoles());

		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM RolesEntity");
		List<RolesEntity> roles = query.list();

		boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
		boolean isUser = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"));
		boolean isStaff = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_STAFF"));

		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("isUser", isUser);
		model.addAttribute("isStaff", isStaff);
		model.addAttribute("task", "edit");
		return "users/edit";
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UsersEntity user, @RequestParam("task") String task,
			@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
			@RequestParam(value = "enabled", required = false) Integer enabled, RedirectAttributes redirectAttributes,
			ModelMap model) {
		Session session = factory.getCurrentSession();
		
		try {
			if (uuid == null || uuid.isEmpty()) {
				uuid = UUID.randomUUID().toString();
				user.setUuid(uuid);
			}
			user.setEmail(EscapeHtmlUtil.encodeHtml(user.getEmail()));
			user.setFullname(EscapeHtmlUtil.encodeHtml(user.getFullname()));
			user.setPhone(EscapeHtmlUtil.encodeHtml(user.getPhone()));
			
			// Chuẩn hóa tên người dùng để kiểm tra trùng lặp
			String normalizedFullname = user.getFullname().trim().toLowerCase();
			
			// Kiểm tra tên người dùng trùng lặp
			String hql = "FROM UsersEntity WHERE LOWER(TRIM(fullname)) = :fullname AND uuid != :userUuid";
			Query query = session.createQuery(hql);
			query.setParameter("fullname", normalizedFullname);
			query.setParameter("userUuid", uuid != null ? uuid : "");
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

				user.setAvatar(
						"https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733041850/images/avatars/vo-anh-phungg/drmxjyaok8d8b8ofgiwl.png");
				String hashedPassword = PasswordUtil.hashPassword("bookstore");
				user.setPassword(hashedPassword);

				Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
				user.setCreated_at(currentDate);
				user.setUpdated_at(currentDate);
				user.setEnabled(1);

				// Lưu người dùng mới
				session.save(user);
				CartsEntity cart = new CartsEntity();
				cart.setUser(user);
				cart.setStatus(1);
				cart.setCreatedAt(new java.util.Date());
				cart.setUpdatedAt(new java.util.Date());

				session.save(cart);

			} else if ("edit".equals(task)) {
				UsersEntity existingUser = getUserByUuid(uuid); // Sử dụng UUID để tìm người dùng


				if (existingUser == null) {
					model.addAttribute("message", "User not found.");
					return "redirect:/admin1337/users.htm";
				}

				existingUser.setUuid(uuid); // Đảm bảo UUID được gán vào người dùng
				existingUser.setFullname(user.getFullname());
				existingUser.setEmail(user.getEmail());
				existingUser.setPhone(user.getPhone());
				existingUser.setGender(user.getGender());
				existingUser.setEnabled(enabled);

				Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
				existingUser.setUpdated_at(currentDate);

				UsersEntity emailCheck = getUserByEmail(user.getEmail());
				if (emailCheck != null && !emailCheck.getUuid().equals(existingUser.getUuid())) { 
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
					existingUser.setRoles(new HashSet<>()); 
				}

				// Merge và lưu người dùng đã cập nhật
				session.merge(existingUser);
				

				// Thông báo thành công
				redirectAttributes.addFlashAttribute("alertMessage", "User saved successfully!");
				redirectAttributes.addFlashAttribute("alertType", "success");
			}

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

	public UsersEntity getUserByUuid(String uuid) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM UsersEntity WHERE uuid = :uuid");
		query.setParameter("uuid", uuid);
		return (UsersEntity) query.uniqueResult();
	}

}