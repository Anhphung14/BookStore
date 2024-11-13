package bookstore.Controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.Entity.UsersEntity;

@Controller
public class AuthController {
	@Autowired
	SessionFactory factory;

	// LOGIN
	@Transactional
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String login() {
		Session session = factory.getCurrentSession();
		return "auth/signin";
	}

	@Transactional
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String handle_login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {

		if (email.isEmpty() || password.isEmpty()) {
			return "redirect:signin.htm";
		}

		UsersEntity user = this.getUserByEmailPass(email, password);

		if (user == null) {
			return "redirect:signin.htm";
		} else {
			session.setAttribute("user", user);
			return "redirect:home.htm";
		}
	}

	// REGISTER
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "auth/signup";
	}

	// FORGOT PASSWORD
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotpassword() {
		return "auth/forgotpassword";
	}

	// RESET PASSWORD
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String resetpassword() {
		return "auth/resetpassword";
	}

	public UsersEntity getUserByEmailPass(String email, String password) {
		Session session = factory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = '" + email + "' AND password = '" + password + "'";
		Query query = session.createQuery(hql);
		UsersEntity user = (UsersEntity) query.uniqueResult();

		return user;
	}

}
