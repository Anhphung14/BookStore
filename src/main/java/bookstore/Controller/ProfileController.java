package bookstore.Controller;

import bookstore.Entity.UsersEntity;
import bookstore.Service.UploadService;
import bookstore.Utils.EscapeHtmlUtil;
import bookstore.Utils.PasswordUtil;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/admin1337")
public class ProfileController {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UploadService uploadService;

	@RequestMapping("/profile")
	public String showProfile(HttpSession session, Model model) {
		UsersEntity user = (UsersEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/signin.htm";
		}
		model.addAttribute("user", user);
		return "account/profile";
	}

	@RequestMapping(value = "/profile/save", method = RequestMethod.POST)
	public String saveProfile(@ModelAttribute("user") UsersEntity user,
			@RequestParam("avatarFile") MultipartFile avatarFile, HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			UsersEntity sessionUser = (UsersEntity) session.getAttribute("user");
			
			// Cập nhật các thông tin cá nhân
			if (user.getFullname() != null) {
				user.setFullname(EscapeHtmlUtil.encodeHtml(user.getFullname()));
				sessionUser.setFullname(user.getFullname());
			}
			if (user.getPhone() != null) {
				user.setPhone(EscapeHtmlUtil.encodeHtml(user.getPhone()));
				sessionUser.setPhone(user.getPhone());
			}
			if (user.getGender() != null) {
				sessionUser.setGender(user.getGender());
			}

			if (user.getPassword() != null && !user.getPassword().isEmpty()) {

				String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
				sessionUser.setPassword(hashedPassword);// Cập nhật mật khẩu đã mã hóa
			}

			if (avatarFile != null && !avatarFile.isEmpty()) {
				// Upload ảnh đại diện lên Cloudinary
				String avatarPath = uploadService.uploadByCloudinary(avatarFile,
						"images/avatars/" + uploadService.toSlug(user.getFullname()));
				sessionUser.setAvatar(avatarPath); // Lưu đường dẫn ảnh đại diện vào đối tượng người dùng
			}

			// Cập nhật thông tin người dùng trong session
			session.setAttribute("user", sessionUser);

			// Cập nhật người dùng trong cơ sở dữ liệu
			sessionFactory.getCurrentSession().merge(sessionUser);

	        redirectAttributes.addFlashAttribute("success");
		} catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error");
			e.printStackTrace();
		}

		// Điều hướng về trang profile sau khi lưu thành công
		return "redirect:/admin1337/profile.htm";
	}

}
