	package bookstore.Controller;

import java.util.Random;

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
	@Transactional
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String handle_forgotpassword(ModelMap model, @RequestParam("email") String email) {
	    try {
	        Session session = factory.getCurrentSession();

	        String hql = "FROM UsersEntity WHERE email = :email";
	        Query query = session.createQuery(hql);
	        query.setParameter("email", email);
	        UsersEntity user = (UsersEntity) query.uniqueResult();

	        if (user == null) {
	            System.out.println("No user found with email: " + email);
	            model.addAttribute("alertMessage", "Email not found in the system!");
	            model.addAttribute("alertType", "error");
	            return "auth/forgotpassword";
	        }
  
	        String newPassword = generateRandomPassword(); 

	        String emailContent = "<html><body>"
	                + "<h5>Hello " + user.getEmail() + ",</h5>"
	                + "<p>We received a request to reset your password. Below is your new password:</p>"
	                + "<h5 style=\"color: #4CAF50;\">" + newPassword + "</h5>"
	                + "<p>If you did not request this, please ignore this email.</p>"
	                + "<p>Regards,<br>BookStore</p>"
	                + "<footer style=\"font-size: 0.8em; color: #777;\">This is an automated email. Please do not reply.</footer>"
	                + "</body></html>";

	        MimeMessage message = mailer.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setTo(user.getEmail());
	        helper.setSubject("Password Reset Request");
	        helper.setText(emailContent, true); 

	        mailer.send(message);


	        user.setPassword(newPassword);
	        session.update(user);

	        model.addAttribute("alertMessage", "Password reset email has been sent successfully!");
	        model.addAttribute("alertType", "success");

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        model.addAttribute("alertMessage", "An error occurred while sending the email!");
	        model.addAttribute("alertType", "error");
	    }

	    return "auth/forgotpassword";
	}


	private String generateRandomPassword() {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
	    StringBuilder password = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < 12; i++) { 
	        password.append(chars.charAt(random.nextInt(chars.length())));
	    }
	    return password.toString();
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
