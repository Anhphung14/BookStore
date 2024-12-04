package bookstore.Controller.client;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.DTO.OTPDTO;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Service.MailService;
import bookstore.Service.OTPService;
import bookstore.Util.PasswordUtil;

@Controller
@Transactional
public class AuthClientController {

	@Autowired
	SessionFactory factory;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	OTPService otpService;

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
	public String saveUser(@ModelAttribute("user") UsersEntity user, Model model, RedirectAttributes redirectAttributes) {
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

			RolesEntity role = (RolesEntity) session.get(RolesEntity.class, 3L); // role_id = 3
			if (role != null) {
				Set<RolesEntity> roles = new HashSet<>();
				roles.add(role);
				user.setRoles(roles);
			}
			
			String otpCode = otpService.storeOtp(user.getEmail());
			
			String emailContent = "<html><body>"
	                + "<h5>Hello " + user.getEmail() + ",</h5>"
	                + "<p>Click the following link to confirm and activate your account:</p>"
	                + "<h5 style=\"color: #4CAF50;\">" + "http://127.0.0.1:8080/bookstore/client/verify-email.htm?code="+ otpCode + "</h5>"
	                + "<p>Bạn có 1 phút để xác thực tài khoản 😎</p>"
	                + "<p>Regards,<br>BookStore</p>"
	                + "<footer style=\"font-size: 0.8em; color: #777;\">This is an automated email. Please do not reply.</footer>"
	                + "</body></html>";
			
			mailService.sendMail(emailContent, user.getEmail(), "Complete Your Registration with This Link");
			
			user.setEnabled(0);

			session.save(user);
	        model.addAttribute("alertMessage", "The account activation link has been sent to your email, please check it!");
	        model.addAttribute("alertType", "warning");
	        
	       
	        return "client/auth/signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("alertType", "error");
			return "redirect:client/signup.htm";
		}

	}
	
	@RequestMapping("/client/verify-email")
	public String verify_email(ModelMap model, @RequestParam(value = "code", required = false) String code) {
		OTPDTO otp = otpService.getOTPByCode(code);
		
		String alertMessage = "";
		String alertType = "";
		
	    if (otp == null) {
	        alertMessage = "The link is incorrect or does not exist!";
	        alertType = "error";
	    } else {

		    if (otp.isExpired()) {
		        alertMessage = "The link has expired!";
		        alertType = "error";
		        model.addAttribute("email", otp.getEmail());
		    }
	
		    else if (otp.isUsed()) {
		    	alertMessage = "The link has already been activated!";
		        alertType = "error";
		    }
	
		    else if (otp.getCode().equals(code)) {
		        otp.setUsed(true);
		        otpService.removeOTPCode(code);
		        
		        alertMessage = "Congratulations, you have successfully registered!";
		        alertType = "success";
		    } else {
		        alertMessage = "The link is incorrect or does not exist!";
		        alertType = "error";
		    }
	    
	    }
		
	    model.addAttribute("alertMessage", alertMessage);
	    model.addAttribute("alertType", alertType);
	    
		return "client/auth/confirmOTP";
		
	}
	
	@RequestMapping(value = "/client/resend-link", method = RequestMethod.POST)
	public String resend(@RequestParam("email") String email) {
		
		String otpCode = otpService.storeOtp(email);
		
		String emailContent = "<html><body>"
                + "<h5>Hello " + email + ",</h5>"
                + "<p>Click the following link to confirm and activate your account:</p>"
                + "<h5 style=\"color: #4CAF50;\">" + "http://127.0.0.1:8080/bookstore/client/verify-email.htm?code="+ otpCode + "</h5>"
                + "<p>Bạn có 1 phút để xác thực tài khoản 😎</p>"
                + "<p>Regards,<br>BookStore</p>"
                + "<footer style=\"font-size: 0.8em; color: #777;\">This is an automated email. Please do not reply.</footer>"
                + "</body></html>";
		
		mailService.sendMail(emailContent, email,  "Complete Your Registration with This Re-Link");
		
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
