package bookstore.Controller.client;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

import bookstore.DAO.BooksDAO;
import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.InventoryDAO;
import bookstore.DAO.OrderDAO;
import bookstore.DAO.RatingsDAO;
import bookstore.DAO.SubcategoriesDAO;
//import bookstore.DAO.OrderDAO;
import bookstore.DAO.UserDAO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.Order_DiscountsEntity;
import bookstore.Entity.OrdersDetailEntity;
import bookstore.Entity.OrdersEntity;
import bookstore.Entity.RatingsEntity;
import bookstore.Entity.SubcategoriesEntity;
//import bookstore.Entity.OrderDetailEntity;
//import bookstore.Entity.OrderEntity;
//import bookstore.Entity.Order_DiscountsEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Service.MailService;
import bookstore.Service.UploadService;
import bookstore.Utils.PasswordUtil;

@Transactional
@Controller
@RequestMapping("/account/")
public class AccountController {
	@Autowired
	private BooksDAO bookDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
    private OrderDAO orderDAO;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private RatingsDAO ratingsDAO;
	@Autowired
	private InventoryDAO inventoryDAO;
	@Autowired
    private CategoriesDAO categoriesDAO;
    @Autowired
    private SubcategoriesDAO subcategoriesDAO;
    @Autowired
    MailService mailService;
	
	@RequestMapping(value = "account_orders", method = RequestMethod.GET)
	public String account_order(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    if (currentUser == null) {
	    	return "redirect:/signin.htm";
	    }
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    model.addAttribute("user", user);
	    
	    List<OrdersEntity> orders = orderDAO.getOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);
        List<Long> order_reviewed = ratingsDAO.getRatedOrderIdsByUserId(user.getId());
        for (Long i: order_reviewed) {
        	System.out.println(i);
        }
        List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
        
        model.addAttribute("order_reviewed", order_reviewed);
		return "client/Account/account_orders";
	}
	
	@RequestMapping(value = "order_details/{orderId}", method = RequestMethod.GET) 
	public String order_details(ModelMap model, @PathVariable("orderId") Long id) {
		OrdersEntity order = orderDAO.getOrderByOrderId(id);
		model.addAttribute("order", order);
		List<OrdersDetailEntity> listOrderDetails = orderDAO.getOrderDetailsByOrderId(id);
	     // Lấy thông tin giảm giá cho đơn hàng (OrderDiscount)
		List<Order_DiscountsEntity> listOrderDiscounts = orderDAO.getOrderDiscountsByOrderId(id);
		  // Thêm dữ liệu vào model để gửi tới view
		model.addAttribute("orderDetails", listOrderDetails);
		if (listOrderDiscounts != null && !listOrderDiscounts.isEmpty()) {
			model.addAttribute("orderDiscounts", listOrderDiscounts);
		}
		
		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
		return "client/Account/order_detail";
	}
	
	@RequestMapping(value = "cancel_order", method = RequestMethod.POST)
	public String cancel_order(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
		try {
	        int isCancel = orderDAO.updateOrderStatusToCancel(orderId);
	        
	        OrdersEntity order = orderDAO.getOrderByOrderId(orderId);
	        for(OrdersDetailEntity orderDetail : order.getOrderDetails()) {
	        	InventoryEntity inventoryOfCurrentBook = inventoryDAO.getInventoryByBookId(orderDetail.getBook().getId());
                Integer currentStockQuantity = inventoryOfCurrentBook.getStock_quantity();
                inventoryOfCurrentBook.setStock_quantity(currentStockQuantity + orderDetail.getQuantity());
                boolean isUpdateStockQuantity = inventoryDAO.updateInventory(inventoryOfCurrentBook);
	        }
	        redirectAttributes.addFlashAttribute("successMessage", "Order has been successfully canceled.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel the order. Please try again.");
	    }
		return "redirect:/account/account_orders.htm";
	}
	
	@RequestMapping(value = "profile_settings", method = RequestMethod.GET)
	public String account_profile_get(ModelMap model, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    if (currentUser == null) {
	    	return "redirect:/signin.htm";
	    }
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    user.setEmail(user.getEmail().toLowerCase());
	    model.addAttribute("user", user);
	    
	    List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
		return "client/Account/profile_settings";
	}
	
	
	@RequestMapping(value = "update_profile", method = RequestMethod.POST)
	public String update_profile(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam("fullname") String fullname,
            @RequestParam("phone") String phone,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException
	{
		HttpSession session = request.getSession();
	   
	    UsersEntity user = (UsersEntity) session.getAttribute("user");
	    
		
	    int isError = 0;
	    if(fullname.trim().length() == 0){
	    	redirectAttributes.addFlashAttribute("errorfn", "Vui lòng nhập họ tên!");
			isError++;
		}
	    
	    String regex = "^0(3|5|7|8|9)[0-9]{8}$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone.trim());
        

		if(phone.trim().length() == 0){
			redirectAttributes.addFlashAttribute("errorPhone", "Vui lòng nhập số điện thoại!");
			isError++;
		}
		else {
			if (!matcher.matches()) {
				redirectAttributes.addFlashAttribute("errorPhone", "Số điện thoại không hợp lệ!");
	        	isError++;
			}
		}
		
		if(isError > 0){
			redirectAttributes.addFlashAttribute("errorUpdate", "Vui lòng sửa các lỗi sau đây !");
			return "redirect:/account/profile_settings.htm";
		}
		
	    user.setFullname(fullname.trim());
	    user.setPhone(phone.trim());
	    if (!avatar.isEmpty()) {
	    	String avatarPath = uploadService.uploadByCloudinary(avatar, "images/avatar/" + uploadService.toSlug(fullname));
	    	System.out.println(avatarPath);
	    	user.setAvatar(avatarPath);
		    if (userDAO.updateUserById(user.getId(), user) > 0) {
		    	redirectAttributes.addFlashAttribute("successUpdate", "Cập nhật thông tin thành công!");
		    	session.setAttribute("user", user);
		    }else {
		    	redirectAttributes.addFlashAttribute("errorUpdate", "Cập nhật thông tin không thành công!");
		    }
	    }else {
	    	if (userDAO.updateUserById(user.getId(), user) > 0) {
	    		redirectAttributes.addFlashAttribute("successUpdate", "Cập nhật thông tin thành công!");
		    	session.setAttribute("user", user);
		    }else {
		    	redirectAttributes.addFlashAttribute("errorUpdate", "Cập nhật thông tin không thành công!");
		    }
	    }
	    return "redirect:/account/profile_settings.htm";
	}
	
	@RequestMapping(value = "change_password", method = RequestMethod.POST)
	public String acoount_change_passwowrd(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
	    UsersEntity user = (UsersEntity) session.getAttribute("user");
	    
	    String oldPass = request.getParameter("oldPassword");
	    if (oldPass.trim().length() == 0) {
	    	redirectAttributes.addFlashAttribute("oldPassError", "Hãy nhập lại mật khẩu cũ!");
	    }else if (!userDAO.checkOldPassword(user.getId(), oldPass)) {
	    	redirectAttributes.addFlashAttribute("oldPassError", "Mật khẩu cũ không đúng");
	    }else {
	    	String newPass = request.getParameter("newPassword");
	    	String hashNewPass = PasswordUtil.hashPassword(newPass);
	    	if (userDAO.updatePasswordUserById(user.getId(), hashNewPass) > 0) {
	    		redirectAttributes.addFlashAttribute("messagePassword", "Đổi password thành công!");
	    		redirectAttributes.addFlashAttribute("alertType", "success");
	    		user.setPassword(hashNewPass);
	    		session.setAttribute("user",user);
	    		String emailContent = "<html><body>"
	    		        + "<h5>Hello " + user.getFullname() + ",</h5>"
	    		        + "<p>We are pleased to inform you that your password has been successfully changed.</p>"
	    		        + "<p>If you did not request this change, please contact our support team immediately to secure your account.</p>"
	    		        + "<p>Thank you for using our services.</p>"
	    		        + "<p>Best regards,</p>"
	    		        + "<p>Book Store ALDPT</p>"
	    		        + "</body></html>";
	    		mailService.sendMail(emailContent, user.getEmail(), "Password Changed Successfully");
	    	}else {
	    		redirectAttributes.addFlashAttribute("messagePassword", "Đổi password không thành công!");
	    		redirectAttributes.addFlashAttribute("alertType", "error");
	    	}
	    }
		return "redirect:/account/profile_settings.htm";
	}
	
	@RequestMapping(value = "my_ratings", method = RequestMethod.GET)
	public String my_ratings(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
	    if (currentUser == null) {
	    	return "redirect:/signin.htm";
	    }
	    UsersEntity user = userDAO.getUserById(currentUser.getId());
	    model.addAttribute("user", user);
	    List<RatingsEntity> reviews = ratingsDAO.getRatingsByUserId(user.getId());

        model.addAttribute("reviews", reviews);
        
        List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
		return "client/Account/my_ratings";
	}
	
	@RequestMapping(value = "ratings/{orderId}", method = RequestMethod.GET) 
	public String rating_order(ModelMap model, @PathVariable("orderId") Long id) {
		model.addAttribute("orderId", id);
		OrdersEntity order = orderDAO.getOrderByOrderId(id);
		model.addAttribute("order", order);
		List<OrdersDetailEntity> listOrderDetails = orderDAO.getOrderDetailsByOrderId(id);
	     // Lấy thông tin giảm giá cho đơn hàng (OrderDiscount)
		List<Order_DiscountsEntity> listOrderDiscounts = orderDAO.getOrderDiscountsByOrderId(id);
		  // Thêm dữ liệu vào model để gửi tới view
		model.addAttribute("orderDetails", listOrderDetails);
		
		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
		return "client/Account/ratings";
	}
	
	@RequestMapping(value = "submitRatings", method = RequestMethod.POST)
	//public String submit_ratings(Model model, )
	public String submitRatings(@RequestParam Map<String, String> allRequestParams, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    UsersEntity currentUser = (UsersEntity) session.getAttribute("user");
		Long userId = currentUser.getId(); // Lấy userId từ session hoặc security context
		UsersEntity user = userDAO.getUserById(currentUser.getId());
		int check = 0;

		for (String key : allRequestParams.keySet()) {
	        String orderId = "";
	        String ratingValue = "";
	        String reviewValue = null;
	        String bookId = "";
	        // Kiểm tra nếu key bắt đầu bằng "rating" thì đây là thông tin đánh giá
	        if (key.startsWith("rating")) {
	            // Lấy orderId và bookId từ tên trường (orderId_bookId)
	            String[] parts = key.substring(6).split("_");
	            orderId = parts[0];  // Lấy Order ID từ tên trường
	            bookId = parts[1];   // Lấy Book ID từ tên trường
	            
	            // Lấy giá trị đánh giá
	            if (allRequestParams.get(key) != null && allRequestParams.get(key).length() > 0) {
	                ratingValue = allRequestParams.get(key);  // Lấy giá trị đầu tiên từ mảng
	            }

	            // Kiểm tra nếu có giá trị review
	            String reviewKey = "review" + orderId + "_" + bookId;
	            if (allRequestParams.get(reviewKey) != null && allRequestParams.get(reviewKey).length() > 0) {
	                reviewValue = allRequestParams.get(reviewKey);  // Lấy giá trị review
	            }

	            // Kiểm tra giá trị null và xử lý
	            if (ratingValue != null) {
	            	OrdersEntity order = orderDAO.getOrderById(Long.parseLong(orderId));
	                BooksEntity book = bookDAO.getBookById(Long.parseLong(bookId));
	            	int rs = ratingsDAO.addRating(user, book , order, Integer.parseInt(ratingValue), reviewValue);
	                // Lưu dữ liệu vào cơ sở dữ liệu hoặc xử lý tiếp
	                System.out.println("Order ID: " + orderId);
	                System.out.println("Book ID: " + bookId);
	                System.out.println("Rating: " + ratingValue);
	                System.out.println("Review: " + reviewValue);
	            } else {
	                // Xử lý nếu không có giá trị rating
	            	check++;
	                System.out.println("Thiếu thông tin đánh giá cho sản phẩm ID: " + bookId);
	            }
	        }
	    }
		if (check != 0) {
			model.addAttribute("errorRating", "Thiếu thông tin đánh giá cho một vài sách!");
			return "client/Account/ratings";
		}
		return "redirect:/account/my_ratings.htm"; 
	}
	
}
