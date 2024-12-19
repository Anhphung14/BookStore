package bookstore.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.http.client.ClientProtocolException;
import org.cloudinary.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bookstore.DAO.CartDAO;
import bookstore.DAO.RolesDAO;
import bookstore.DAO.UserDAO;
import bookstore.DTO.OTPDTO;
import bookstore.Entity.CartsEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Models.auth.GithubPojo;
import bookstore.Models.auth.GooglePojo;
import bookstore.Service.CustomUserDetailsService;
import bookstore.Service.MailService;
import bookstore.Service.OTPService;
import bookstore.Utils.GithubUtils;
import bookstore.Utils.GoogleUtils;
import bookstore.Utils.PasswordUtil;

@Controller
public class AuthController {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	GoogleUtils googleUtils;

	@Autowired
	GithubUtils githubUtils;
	
	@Autowired
	RolesDAO rolesDAO;
	
	@Autowired
	CartDAO cartDAO;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired private 
	CustomUserDetailsService userDetailsService;
	

	// LOGIN
	@Transactional
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String login() {
		Session session = factory.getCurrentSession();
		return "auth/signin";
	}
	
	@RequestMapping("/login-google")
	public String loginGoogle(HttpSession session, HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		
		if (code == null || code.isEmpty()) {
			return "redirect:/index.htm?error=true";
		}
		
		String accessToken = googleUtils.getToken(code);
		
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		
		UsersEntity user = userDAO.getUserByEmai(googlePojo.getEmail());
		
		if (user == null) {
			user = new UsersEntity();
			user.setAvatar(googlePojo.getPicture());
			user.setEmail(googlePojo.getEmail());
			user.setPassword("");
			user.setFullname(googlePojo.getName());
			user.setGender(1);
			user.setPhone("");
			user.setCreated_at(new Date());
			user.setUpdated_at(new Date());
			user.setEnabled(1);
			
			RolesEntity role = rolesDAO.getRoleByName("ROLE_USER");
			if (role != null) {
	            Set<RolesEntity> roles = new HashSet<>();
	            roles.add(role);
	            user.setRoles(roles);
	        }
			
			boolean isSavedUser = userDAO.saveNewUser(user);
			
			if (isSavedUser) {
				CartsEntity cart = new CartsEntity();
		        cart.setUser(user);
		        cart.setStatus(1); 
		        cart.setCreatedAt(new Date());
		        cart.setUpdatedAt(new Date());

		        
		        boolean isSavedCart = cartDAO.saveNewCart(cart);
		        if (!isSavedCart) {
		        	System.out.println("CO LOI ROI!!!!!");
		        	return "redirect:/signin.htm?error=true";
		        } else {
		        	user.setCart(cart);
		        }
			}
		}
		
//		UserDetails userDetails = googleUtils.buildUser(googlePojo);
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//		        user.getEmail(), 
//		        "", // Không cần mật khẩu trong trường hợp này
//		        AuthorityUtils.createAuthorityList("ROLE_USER")
//		    );
//
//		    // Xác thực người dùng thông qua AuthenticationManager
//	    Authentication auth = authenticationManager.authenticate(authentication);
//
//	    // Lưu thông tin người dùng vào SecurityContext
//	    SecurityContextHolder.getContext().setAuthentication(auth);
		
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		session.setAttribute("user", user);

		
		return "redirect:/index.htm";
	}
	
	@RequestMapping("/login-github")
	public String loginGithub(HttpSession session, HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		
		System.out.println(code);
		
		if (code == null || code.isEmpty()) {
			return "redirect:/index.htm?error=true";
		}
		
		String accessToken = githubUtils.getToken(code);
		System.out.println(accessToken);
		
		GithubPojo githubPojo = githubUtils.getUserInfo(accessToken);
		
		UsersEntity user = userDAO.getUserByEmai(githubPojo.getEmail());
		
		if (user == null) {
			user = new UsersEntity();
			user.setAvatar(githubPojo.getAvatar_url());
			user.setEmail(githubPojo.getEmail());
			user.setPassword("");
			user.setFullname(githubPojo.getName() != null ? githubPojo.getName() : githubPojo.getLogin());
			user.setGender(1);
			user.setPhone("");
			user.setCreated_at(new Date());
			user.setUpdated_at(new Date());
			user.setEnabled(1);
			
			RolesEntity role = rolesDAO.getRoleByName("ROLE_USER");
			if (role != null) {
	            Set<RolesEntity> roles = new HashSet<>();
	            roles.add(role);
	            user.setRoles(roles);
	        }
			
			boolean isSavedUser = userDAO.saveNewUser(user);
			
			if (isSavedUser) {
				CartsEntity cart = new CartsEntity();
		        cart.setUser(user);
		        cart.setStatus(1); 
		        cart.setCreatedAt(new Date());
		        cart.setUpdatedAt(new Date());

		        
		        boolean isSavedCart = cartDAO.saveNewCart(cart);
		        if (!isSavedCart) {
		        	System.out.println("CO LOI ROI!!!!!");
		        	return "redirect:/signin.htm?error=true";
		        } else {
		        	user.setCart(cart);
		        }
			}
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		session.setAttribute("user", user);
		
		
		return "redirect:/index.htm";
	}

	@Transactional
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String handle_login(@RequestParam("email") String email, @RequestParam("password") String password,
	                           @RequestParam("g-recaptcha-response") String recaptchaResponse, HttpSession session) {

	    // Kiểm tra reCAPTCHA
	    if (!verifyCaptcha(recaptchaResponse)) {
	        return "redirect:signin.htm?error=recaptcha";
	    }

	    if (email.isEmpty() || password.isEmpty()) {
	        return "redirect:signin.htm";
	    }

	    UsersEntity user = userDAO.getUserByEmailPass(email, password);

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
		
		System.out.println("Da logout!!!");
		
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
	@Transactional
	@RequestMapping(value = "/saveSignup", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UsersEntity user, Model model, RedirectAttributes redirectAttributes) {
		Session session = factory.getCurrentSession();

	    try {
	        if (getUserByEmail(user.getEmail()) != null) {
	            model.addAttribute("alertMessage", "Email đã tồn tại vui lòng nhập email khác!");
	            model.addAttribute("alertType", "error");
	            return "redirect:signup.htm";
	        }

	        user.setAvatar("https://res.cloudinary.com/dsqhfz3xt/image/upload/v1733041850/images/avatars/vo-anh-phungg/drmxjyaok8d8b8ofgiwl.png");

	        LocalDateTime now = LocalDateTime.now();
	        Timestamp currentDate = Timestamp.valueOf(now);
	        user.setCreated_at(currentDate);
	        user.setUpdated_at(currentDate);

	        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
	        user.setPassword(hashedPassword);
	        user.setGender(1);

//	        RolesEntity role = (RolesEntity) session.get(RolesEntity.class, 3L);
	        RolesEntity role = rolesDAO.getRoleByName("ROLE_USER");
	        
	        if (role != null) {
	            Set<RolesEntity> roles = new HashSet<>();
	            roles.add(role);
	            user.setRoles(roles);
	        }
	        
	        String otpCode = otpService.storeOtp(user.getEmail());
			
			String emailContent = "<html><body>"
	                + "<h5>Hello " + user.getEmail() + ",</h5>"
	                + "<p>Click the following link to confirm and activate your account:</p>"
	                + "<h5 style=\"color: #4CAF50;\">" + "http://localhost:8080/bookstore/verify-email.htm?code="+ otpCode + "</h5>"
//	                + "<p>Bạn có 1 phút để xác thực tài khoản 😎</p>"
	                + "<p>Regards,<br>BookStore</p>"
	                + "<footer style=\"font-size: 0.8em; color: #777;\">This is an automated email. Please do not reply.</footer>"
	                + "</body></html>";
			
			mailService.sendMail(emailContent, user.getEmail(), "Complete Your Registration with This Link");
			
			user.setEnabled(0);

	        session.saveOrUpdate(user);

	        CartsEntity cart = new CartsEntity();
	        cart.setUser(user);
	        cart.setStatus(1); 
	        cart.setCreatedAt(new Date());
	        cart.setUpdatedAt(new Date());

	        session.save(cart);

	        model.addAttribute("alertMessage", "The account activation link has been sent to your email, please check it!");
	        model.addAttribute("alertType", "warning");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("alertType", "error");
	        return "redirect:signup.htm";
	    }
	    return "auth/signup";
	}
	
	@RequestMapping("/verify-email")
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
		        
		        UsersEntity user = userDAO.getUserByEmai(otp.getEmail());
		        
		        user.setEnabled(1);
		        
		        userDAO.updateUser(user);
		        
		        alertMessage = "Congratulations, you have successfully registered!";
		        alertType = "success";
		    } else {
		        alertMessage = "The link is incorrect or does not exist!";
		        alertType = "error";
		    }
	    
	    }
		
	    model.addAttribute("alertMessage", alertMessage);
	    model.addAttribute("alertType", alertType);
	    
		return "auth/confirmOTP";
		
	}
	
	@RequestMapping(value = "/resend-link", method = RequestMethod.POST)
	public String resend(@RequestParam("email") String email) {
		
		String otpCode = otpService.storeOtp(email);
		long start_time = System.currentTimeMillis();
		
		String emailContent = "<html><body>"
                + "<h5>Hello " + email + ",</h5>"
                + "<p>Click the following link to confirm and activate your account:</p>"
                + "<h5 style=\"color: #4CAF50;\">" + "http://127.0.0.1:8080/bookstore/verify-email.htm?code="+ otpCode + "</h5>"
                + "<p>Bạn có 1 phút để xác thực tài khoản 😎</p>"
                + "<p>Regards,<br>BookStore</p>"
                + "<footer style=\"font-size: 0.8em; color: #777;\">This is an automated email. Please do not reply.</footer>"
                + "</body></html>";
		
		mailService.sendMail(emailContent, email,  "Complete Your Registration with This Re-Link");
		long end_time = System.currentTimeMillis();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (end_time - start_time));
		
		return "auth/signup";
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
	
	@Transactional
	public RolesEntity getRoleByName(String roleName) {
		String hql = "FROM RolesEntity r WHERE r.name = :roleName";
	    return (RolesEntity) factory.getCurrentSession()
	                                       .createQuery(hql)
	                                       .setParameter("roleName", roleName)
	                                       .uniqueResult();
	}
	
	//Recaptcha
	public boolean verifyCaptcha(String recaptchaResponse) {
        String secretKey = "6LfGAJYqAAAAAB2PBvsV_38QchRbZ5G_bW2SEwpu"; // Thay bằng secret key của bạn
        try {
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postData = "secret=" + secretKey + "&response=" + recaptchaResponse;

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(postData);
            writer.flush();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonObject responseJson = JsonParser.parseReader(reader).getAsJsonObject();

            return responseJson.get("success").getAsBoolean(); // Kiểm tra xem reCAPTCHA có thành công hay không
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
