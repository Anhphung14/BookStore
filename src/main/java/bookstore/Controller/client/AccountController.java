package bookstore.Controller.client;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import bookstore.DAO.OrderDAO;
import bookstore.DAO.UserDAO;
//import bookstore.Entity.OrderDetailEntity;
//import bookstore.Entity.OrderEntity;
//import bookstore.Entity.Order_DiscountsEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Service.UploadService;

@Transactional
@Controller
@RequestMapping("/account/")
public class AccountController {
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired
//    private OrderDAO orderDAO;
	
	@Autowired
	private UploadService uploadService;
	/*
	@RequestMapping(value = "account_orders", method = RequestMethod.GET)
	public String account_order(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    model.addAttribute("user", user);
	    
	    List<OrderEntity> orders = orderDAO.getOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);
	  
		return "client/Account/account_orders";
	}
	
	
	  @RequestMapping(value = "order_details/{orderId}", method = RequestMethod.GET) 
	  public String order_details(Model model, @PathVariable("orderId") Long id) {
		  OrderEntity order = orderDAO.getOrderByOrderId(id);
		  model.addAttribute("order", order);
		  List<OrderDetailEntity> listOrderDetails = orderDAO.getOrderDetailsByOrderId(id);
	     // Lấy thông tin giảm giá cho đơn hàng (OrderDiscount)
		  List<Order_DiscountsEntity> listOrderDiscounts = orderDAO.getOrderDiscountsByOrderId(id);
		  // Thêm dữ liệu vào model để gửi tới view
		  model.addAttribute("orderDetails", listOrderDetails);
		  if (listOrderDiscounts != null && !listOrderDiscounts.isEmpty()) {
			  model.addAttribute("orderDiscounts", listOrderDiscounts);
	      }
		  return "client/Account/order_detail";
	  }
	 
	
	@RequestMapping(value = "cancel_order", method = RequestMethod.POST)
	public String cancel_order(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
		try {
	        orderDAO.updateOrderStatusToCancel(orderId);
	        redirectAttributes.addFlashAttribute("successMessage", "Order has been successfully canceled.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel the order. Please try again.");
	    }
		return "redirect:/client/account/account_orders.htm";
	}
	*/
	@RequestMapping(value = "profile_settings", method = RequestMethod.GET)
	public String account_profile_get(ModelMap model, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    user.setEmail(user.getEmail().toLowerCase());
	    model.addAttribute("user", user);
		return "client/Account/profile_settings";
	}
	
	
	@RequestMapping(value = "update_profile", method = RequestMethod.POST)
	public String update_profile(ModelMap model, HttpServletRequest request, 
			@RequestParam("fullname") String fullname,
            @RequestParam("phone") String phone,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException
	{
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    
	    UsersEntity userUpdate = user;
	    userUpdate.setFullname(fullname.trim());
		userUpdate.setPhone(phone.trim());
		
	    int isError = 0;
	    if(userUpdate.getFullname().trim().length() == 0){
			model.addAttribute("errorfn", "Vui lòng nhập họ tên!");
			isError++;
		}
	    
	    String regex = "^(\\+84|0)(9[0-9]{8}|1[2-9][0-9]{7})$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userUpdate.getPhone());
        

		if(userUpdate.getPhone().trim().length() == 0){
			model.addAttribute("errorPhone", "Vui lòng nhập số điện thoại!");
			isError++;
		}
		else {
			if (!matcher.matches()) {
	        	model.addAttribute("errorPhone", "Số điện thoại không hợp lệ!");
	        	isError++;
			}
		}
		
		if(isError > 0){
			model.addAttribute("errorUpdate", "Vui lòng sửa các lỗi sau đây !");
			return "client/Account/profile_settings";
		}
		
	    user.setFullname(userUpdate.getFullname());
	    user.setPhone(userUpdate.getPhone());
	    if (!avatar.isEmpty()) {
	    	String avatarPath = uploadService.uploadByCloudinary(avatar, "images/avatar/" + uploadService.toSlug(fullname));
	    	System.out.println(avatarPath);
	    	userUpdate.setAvatar(avatarPath);
	    	user.setAvatar(avatarPath);
		    if (userDAO.updateUserById(user.getId(), userUpdate) > 0) {
		    	model.addAttribute("successUpdate", "Cập nhật thông tin thành công!");
		    	session.setAttribute("user", user);
		    }else {
		    	model.addAttribute("errorUpdate", "Cập nhật thông tin không thành công!");
		    }
	    }else {
	    	if (userDAO.updateUserById(user.getId(), userUpdate) > 0) {
		    	model.addAttribute("successUpdate", "Cập nhật thông tin thành công!");
		    	session.setAttribute("user", user);
		    }else {
		    	model.addAttribute("errorUpdate", "Cập nhật thông tin không thành công!");
		    }
	    }
	    return "client/Account/profile_settings";
	}
	
	@RequestMapping(value = "change_password", method = RequestMethod.POST)
	public String acoount_change_passwowrd(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    
	    String oldPass = request.getParameter("oldPassword");
	    if (oldPass.trim().length() == 0) {
	    	redirectAttributes.addFlashAttribute("oldPassError", "Hãy nhập lại mật khẩu cũ!");
	    }else if (!oldPass.equals(currentUser.getPassword())) {
	    	redirectAttributes.addFlashAttribute("oldPassError", "Mật khẩu cũ không đúng");
	    }else {
	    	String newPass = request.getParameter("newPassword");
	    	if (userDAO.updatePasswordUserById(user.getId(), newPass) > 0) {
	    		redirectAttributes.addFlashAttribute("messagePassword", "Đổi password thành công!");
	    		redirectAttributes.addFlashAttribute("alertType", "success");
	    		user.setPassword(newPass);
	    		session.setAttribute("user",user);
	    	}else {
	    		redirectAttributes.addFlashAttribute("messagePassword", "Đổi password không thành công!");
	    		redirectAttributes.addFlashAttribute("alertType", "error");
	    	}
	    }
		return "redirect:/account/profile_settings.htm";
	}
	
	
	private String[] splitFullName(String fullname) {
        if (fullname == null || fullname.isEmpty()) {
            return new String[]{"", ""};
        }
        String[] parts = fullname.split(" ", 2);
        if (parts.length == 1) {
            return new String[]{parts[0], ""};
        }
        return parts;
    }
}
