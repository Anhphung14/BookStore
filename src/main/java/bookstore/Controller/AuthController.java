package bookstore.Controller;

import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	
	// SIGNOUT
	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	public String signout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:signin.htm";
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
	
	@Autowired
	JavaMailSender mailer;
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String handle_forgotpassword(ModelMap model, @RequestParam("email") String email) {
		try {
			MimeMessage mail = mailer.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom("n21dcat014@student.ptithcm.edu.vn", "n21dcat014@student.ptithcm.edu.vn");
			helper.setTo(email);
			helper.setReplyTo("n21dcat014@student.ptithcm.edu.vn", "n21dcat014@student.ptithcm.edu.vn");
			helper.setSubject("Dat lai mat khau");
			helper.setText("Day la mat khau moi cua ban: abcdefgh123456789", true);
			
			mailer.send(mail);
			model.addAttribute("message", "Gửi email thành công !");
		} catch (Exception ex) {
			model.addAttribute("message", "Gửi email thất bại !");
		}
		
		return "auth/forgotpassword";
		
	}

	// RESET PASSWORD
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String resetpassword() {
		return "auth/resetpassword";
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

}
