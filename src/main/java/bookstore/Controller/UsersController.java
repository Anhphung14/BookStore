package bookstore.Controller;

import bookstore.Util.PasswordUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;

@Transactional
@Controller
public class UsersController {

	@Autowired
	private SessionFactory factory;

	@RequestMapping(value = "/users")
	public String users(ModelMap model,
	                    @RequestParam(value = "page", defaultValue = "1") int page,
	                    @RequestParam(value = "size", defaultValue = "10") int size,
	                    @RequestParam(value = "search", required = false) String search) {

	    Session session = factory.getCurrentSession();
	    String hql = "FROM UsersEntity u";
	    String countQuery = "SELECT count(u) FROM UsersEntity u";

	    if (search != null && !search.isEmpty()) {
	        hql += " WHERE u.fullname LIKE :search";
	        countQuery += " WHERE u.fullname LIKE :search";
	    }

	    Query countQ = session.createQuery(countQuery);
	    if (search != null && !search.isEmpty()) {
	        countQ.setParameter("search", "%" + search + "%");
	    }
	    Long count = (Long) countQ.uniqueResult();
	    int totalPages = (int) Math.ceil((double) count / size);

	    Query query = session.createQuery(hql);
	    if (search != null && !search.isEmpty()) {
	        query.setParameter("search", "%" + search + "%");
	    }
	    query.setFirstResult((page - 1) * size);
	    query.setMaxResults(size);

	    List<UsersEntity> users = query.list();

	    for (UsersEntity user : users) {
	        Set<RolesEntity> roles = user.getRoles();
	        user.setRoles(roles);
	    }

	    model.addAttribute("users", users);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("search", search);

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
	        @RequestParam(value = "roleIds", required = false) Set<Long> roleIds, // Nhận mảng các ID
	        ModelMap model) {
	    Session session = factory.getCurrentSession();
	    
	    try {
	        if ("new".equals(task)) {
	            if (getUserByEmail(user.getEmail()) != null) {
	                model.addAttribute("message", "This email is already registered.");
	                return "users/edit";
	            }

	            user.setAvatar("resources/images/default-avatar.png");

	            String hashedPassword = PasswordUtil.hashPassword("bookstore");
	            user.setPassword(hashedPassword);

	            LocalDateTime now = LocalDateTime.now();
	            Timestamp currentDate = Timestamp.valueOf(now);
	            user.setCreated_at(currentDate);
	            user.setUpdated_at(currentDate);

	            session.save(user); // Lưu người dùng

	            if (roleIds != null && roleIds.size() > 0) {
	                Set<RolesEntity> roles = new HashSet<>();
	                for (Long roleId : roleIds) {
	                    RolesEntity role = (RolesEntity) session.get(RolesEntity.class, roleId); // Lấy role từ DB
	                    if (role != null) {
	                        roles.add(role); // Thêm role vào Set
	                    }
	                }
	                user.setRoles(roles); // Gán roles cho người dùng
	                session.update(user); // Cập nhật người dùng với các roles
	            }

	        } else if ("edit".equals(task)) {
	            UsersEntity existingUser = getUserById(id);

	            if (existingUser == null) {
	                model.addAttribute("message", "User not found.");
	                return "redirect:/users.htm";
	            }

	            existingUser.setFullname(user.getFullname());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setPhone(user.getPhone());
	            existingUser.setGender(user.getGender());

	            Date currentDate = new Date(System.currentTimeMillis());
	            existingUser.setUpdated_at(currentDate);

	            UsersEntity emailCheck = getUserByEmail(user.getEmail());
	            if (emailCheck != null && !emailCheck.getId().equals(existingUser.getId())) {
	                model.addAttribute("message", "This email is already registered.");
	                return "users/edit";
	            }

	            if (roleIds != null) {
	                Set<RolesEntity> roles = new HashSet<>();
	                for (Long roleId : roleIds) {
	                    RolesEntity role = (RolesEntity) session.get(RolesEntity.class, roleId); // Lấy role từ DB
	                    if (role != null) {
	                        roles.add(role); // Thêm role vào Set
	                    }
	                }
	                existingUser.setRoles(roles); // Cập nhật roles cho người dùng
	            } else {
	                existingUser.setRoles(new HashSet<>()); // Nếu không chọn quyền nào thì xóa hết roles
	            }

	            session.merge(existingUser); // Cập nhật người dùng
	            return "redirect:/users.htm";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "An error occurred: " + e.getMessage());
	        return "redirect:/users.htm";
	    }

	    return "redirect:/users.htm";
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
		return "redirect:/users.htm";
	}

	@SuppressWarnings("unchecked")
	private List<UsersEntity> ListUsers() {
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