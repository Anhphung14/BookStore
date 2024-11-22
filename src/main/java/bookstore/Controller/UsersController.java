package bookstore.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import bookstore.Entity.UsersEntity;

@Transactional
@Controller
public class UsersController {

	@Autowired
	private SessionFactory factory;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String users(ModelMap model) {
		List<UsersEntity> users = ListUsers();
		model.addAttribute("users", users);
		return "users/index";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable("id") Long id, ModelMap model) {
		UsersEntity user = getUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("task", "edit");
		return "users/edit";
	}

	@RequestMapping(value = "/user/new", method = RequestMethod.GET)
	public String userNew(ModelMap model) {
		model.addAttribute("user", new UsersEntity());
		model.addAttribute("task", "new");
		return "users/edit";
	}

	@RequestMapping(value = "/user/save.htm", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UsersEntity user,
	                       @RequestParam("task") String task,
	                       @RequestParam(value = "id", required = false) Long id, 
	                       ModelMap model) {
	    Session session = factory.getCurrentSession();


	    try {
	        if ("new".equals(task)) {
	            if (getUserByEmail(user.getEmail()) != null) {
	                model.addAttribute("message", "This email is already registered.");
	                return "users/edit";
	            }

	            try {
	            	user.setGender(1);
	            	session.save(user);
	                
	            } catch (Exception e) {
	                e.printStackTrace();
	                model.addAttribute("message", "Failed to save new user: " + e.getMessage());
	                return "users/edit";
	            }
	        }
	        else if ("edit".equals(task)) { 
	            UsersEntity existingUser = getUserById(id);

	            if (existingUser == null) {
	                model.addAttribute("message", "User not found.");
	                return "redirect:/users.htm"; 
	            }

	            existingUser.setFullname(user.getFullname());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setPhone(user.getPhone());
	            UsersEntity emailCheck = getUserByEmail(user.getEmail());
	            if (emailCheck != null && !emailCheck.getId().equals(existingUser.getId())) {
	                model.addAttribute("message", "This email is already registered.");
	                return "users/edit";  // Trở lại trang chỉnh sửa nếu email đã tồn tại
	            }

	            session.merge(existingUser);  // Cập nhật thông tin người dùng
	            return "redirect:/users.htm";  // Chuyển hướng về trang danh sách người dùng
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "An error occurred: " + e.getMessage());
	        return "redirect:/users.htm";  // Nếu có lỗi, trả về trang lỗi
	    }

	    return "redirect:/users.htm";  // Trả về trang danh sách người dùng mặc định
	}
	

	
	/*
	 * public Long generateNewId() { Session session = factory.getCurrentSession();
	 * String hql = "SELECT MAX(id) FROM UsersEntity"; // Lấy ID lớn nhất Long maxId
	 * = (Long) session.createQuery(hql).uniqueResult();
	 * 
	 * if (maxId == null) { return 1; // Nếu chưa có người dùng nào, bắt đầu từ 1 }
	 * else { return maxId + 1; // Tăng ID lên 1 } }
	 */


	public UsersEntity getUserByEmail(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		return (UsersEntity) query.uniqueResult();
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		UsersEntity user = (UsersEntity) session.get(UsersEntity.class, id);
		if (user != null) {
			session.delete(user);
		}
		return "redirect:/users";
	}

	@SuppressWarnings("unchecked")
	private List<UsersEntity> ListUsers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	private UsersEntity getUserById(Long id) {
		Session session = factory.getCurrentSession();
		return (UsersEntity) session.get(UsersEntity.class, id);
	}
}
