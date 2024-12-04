package bookstore.Controller.client;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bookstore.Entity.CartsEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Util.PasswordUtil;

@Controller
@Transactional
public class AuthClientController {

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/client/signin", method = RequestMethod.GET)
	public String login() {
//		Session session = factory.getCurrentSession();
		return "client/auth/signin";
	}

	@Transactional
	@RequestMapping(value = "/client/signin", method = RequestMethod.POST)
	public String handle_login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {

		if (email.isEmpty() || password.isEmpty()) {
			return "redirect:signin.htm";
		}
		
		UsersEntity user = this.authenticateUser(email, password);

		if (user == null) {
			return "redirect:signin.htm";
		} else {
			session.setAttribute("user", user);
			return "redirect:/index.htm";
		}
	}

	@RequestMapping(value = "/client/signup", method = RequestMethod.GET)
	public String signup() {
		return "client/auth/signup";
	}

	@RequestMapping(value = "/client/user/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UsersEntity user, Model model) {
	    Session session = factory.getCurrentSession();

	    try {
	        if (getUserByEmail(user.getEmail()) != null) {
	            model.addAttribute("alertMessage", "Email đã tồn tại vui lòng nhập email khác!");
	            model.addAttribute("alertType", "error");
	            return "client/auth/signup";
	        }

	        user.setAvatar("https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733041850/images/avatars/vo-anh-phungg/drmxjyaok8d8b8ofgiwl.png");

	        LocalDateTime now = LocalDateTime.now();
	        Timestamp currentDate = Timestamp.valueOf(now);
	        user.setCreated_at(currentDate);
	        user.setUpdated_at(currentDate);

	        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
	        user.setPassword(hashedPassword);
	        user.setGender(1);

	        RolesEntity role = (RolesEntity) session.get(RolesEntity.class, 3L);
	        if (role != null) {
	            Set<RolesEntity> roles = new HashSet<>();
	            roles.add(role);
	            user.setRoles(roles);
	        }

	        session.saveOrUpdate(user);

	        CartsEntity cart = new CartsEntity();
	        cart.setUser(user);
	        cart.setStatus(1); 
	        cart.setCreatedAt(new Date());
	        cart.setUpdatedAt(new Date());

	        session.save(cart);

	        model.addAttribute("alertMessage", "Đăng ký thành công, bạn có thể đăng nhập ngay bây giờ!");
	        model.addAttribute("alertType", "success");
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("alertType", "error");
	        return "redirect:client/signup.htm";
	    }
	    return "client/auth/signup";
	}


	public UsersEntity getUserByEmail(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		return (UsersEntity) query.uniqueResult();
	}

	public UsersEntity getUserByEmailPass(String email, String password) {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email AND password = :password";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("password", password);

		UsersEntity user = (UsersEntity) query.uniqueResult();

		return user;
	}
	
	public UsersEntity authenticateUser(String email, String password) {
	    Session session = factory.getCurrentSession();
	    String hql = "FROM UsersEntity WHERE email = :email";
	    Query query = session.createQuery(hql);
	    query.setParameter("email", email);

	    UsersEntity user = (UsersEntity) query.uniqueResult();

	    if (user != null) {
	        String hashedPassword = user.getPassword();
	        
	        if (!PasswordUtil.verifyPassword(password, hashedPassword)) {
	            return null; // Mật khẩu không khớp
	        }
	    }

	    return user;
	}

}
