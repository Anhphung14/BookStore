package bookstore.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import bookstore.DAO.CartDAO;
import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.DiscountsDAO;
import bookstore.DAO.InventoryDAO;
import bookstore.DAO.OrderDAO;
import bookstore.DAO.OrderDetailDAO;
import bookstore.DAO.ShippingAddressDAO;
import bookstore.DAO.SubcategoriesDAO;
import bookstore.DAO.UserDAO;
import bookstore.Entity.*;
import bookstore.Service.MailService;
import bookstore.Service.VNPAYService;
import bookstore.Utils.EscapeHtmlUtil;
import bookstore.Utils.UUIDUtil;
import bookstore.config.VNPAYConfig;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderDetailDAO orderDetailDAO; 
    
    @Autowired
    private ShippingAddressDAO shippingAddressDAO;
    
    @Autowired
    private InventoryDAO inventoryDAO;
    
    @Autowired
    private CategoriesDAO categoriesDAO;
    
    @Autowired
    private SubcategoriesDAO subcategoriesDAO;
    
    @Autowired
    VNPAYService vnpayService;
    
    @Autowired
    private DiscountsDAO discountsDAO;
    
    @Autowired
    MailService mailService;
    
    
    // Hiển thị trang thanh toán
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkoutPage(/* @RequestParam("userId") Long userId, */
			 @RequestParam(value = "selectedItems", required = false) List<Long> selectedItemIds,
		        Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	System.out.println("selectedItemsCheckOut: " + selectedItemIds);
    	if (selectedItemIds == null || selectedItemIds.isEmpty()) {
    		redirectAttributes.addFlashAttribute("alertMessage", "Bạn chưa chọn sản phẩm nào để thanh toán.");
    		redirectAttributes.addFlashAttribute("alertType", "error");
            return "redirect:/cart/view";
        }
    	
    	
        //System.out.println("List selected: " + selectedItemIds);
    	Map<String, Map<String, List<String>>> locationData = shippingAddressDAO.getProvincesWithDistrictsAndWards();
		ObjectMapper objectMapper = new ObjectMapper();
	    String locationDataJson = "";
	    try {
	        locationDataJson = objectMapper.writeValueAsString(locationData);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace(); // Log lỗi nếu có
	    }
	    model.addAttribute("locationData", locationDataJson);
        

	    UsersEntity user = (UsersEntity) session.getAttribute("user");
        model.addAttribute("user", user);

        // Lấy các sản phẩm đã chọn từ selectedItemIds
        List<CartItemsEntity> selectedItems = new ArrayList<>();
        for (Long selectedId : selectedItemIds) {
            //System.out.println("Selected_id: " + selectedId);
            // Gọi phương thức getSelectedItemByBookId từ CartDAO để lấy CartItemsEntity
            CartItemsEntity item = cartDAO.getSelectedItemById(selectedId);
            selectedItems.add(item);
        }

        //System.out.println("Selected cart items: " + selectedItems);
        model.addAttribute("cartItems", selectedItems);

        // Tính tổng giá trị giỏ hàng (nếu cần)
        double totalPrice = 0;
        for (CartItemsEntity item : selectedItems) {
            //totalPrice += (item.getPrice() * item.getQuantity());
            totalPrice += (item.getPrice());
        }
        //System.out.println("totalPrice: " + totalPrice);
        model.addAttribute("totalPrice", totalPrice);
        
        List<DiscountsEntity> listDiscountsAvailable = new ArrayList<DiscountsEntity>();
        List<DiscountsEntity> listDiscounts = discountsDAO.getAllDiscounts();
        for(DiscountsEntity discount : listDiscounts) {
        	if(discount.getApplyTo().equals("user")) {
        		if(discount.getMinOrderValue() <= totalPrice && discount.getStatus().equals("active")) {
        			listDiscountsAvailable.add(discount);
        		}
        	}
        }
        System.out.println("listDiscountsAvailable: " + listDiscountsAvailable);
        model.addAttribute("listDiscountsAvailable", listDiscountsAvailable);
        List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
        return "cart/checkout";
    }

 // Xử lý thanh toán
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String processCheckout(@RequestParam("name") String name, @RequestParam("phone") String phone,
    		@RequestParam("province") String province, @RequestParam("district") String district, @RequestParam("ward") String ward,
    		@RequestParam("street") String street,
	          @RequestParam("paymentMethod") String paymentMethod,
	          @RequestParam("selectedItems") List<Long> selectedItemIds, 
	          @RequestParam(value = "discountCode", defaultValue = "") String discountCoded,  HttpSession session,  RedirectAttributes redirectAttributes) {  
    	name = EscapeHtmlUtil.encodeHtml(name);
    	phone = EscapeHtmlUtil.encodeHtml(phone);
    	street = EscapeHtmlUtil.encodeHtml(street);
    	String discountCode = EscapeHtmlUtil.encodeHtml(discountCoded);
    	
    	// Kiểm tra độ dài của các trường 
    	if (name.length() > 50 || province.length() > 50 || district.length() > 50 || ward.length() > 50 || 
    			street.length() > 255 || paymentMethod.length() > 255 || discountCode.length() > 50) { 
    		redirectAttributes.addFlashAttribute("alertMessage", "One or more fields exceed the allowed length."); 
    		redirectAttributes.addFlashAttribute("alertType", "error"); 
    		return "redirect:/cart/view"; 
    	} 
    	
    	
    	
    	StringBuilder shippingAddressBuilder = new StringBuilder();
		shippingAddressBuilder.append(street).append(", ")
		                      .append(ward).append(", ")
		                      .append(district).append(", ")
		                      .append(province);
		System.out.println("selectedItemIdsPAY: " + selectedItemIds);
		String address = shippingAddressBuilder.toString();
        try {
        	UsersEntity userSession = (UsersEntity) session.getAttribute("user");
            Long userId = userSession.getId();

            // Kiểm tra phương thức thanh toán
            if (paymentMethod == null || paymentMethod.isEmpty()) {
            	redirectAttributes.addFlashAttribute("alertMessage", "Vui lòng chọn phương thức thanh toán.");
        		redirectAttributes.addFlashAttribute("alertType", "error");
        		return "redirect:/cart/view";
            }

            // Kiểm tra xem có sản phẩm nào được chọn hay không
            if (selectedItemIds == null || selectedItemIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("alertMessage", "Vui lòng chọn sản phẩm để thanh toán.");
        		redirectAttributes.addFlashAttribute("alertType", "error");
        		return "redirect:/cart/view";
            }

            // Lấy thông tin người dùng
            UsersEntity user = userDAO.getUserById(userId);

            
            
            // Tính tổng giá trị đơn hàng (ví dụ, nếu bạn đã có cách tính giá từ selectedItemIds)
            double totalPrice = 0;
            List<CartItemsEntity> selectedItems = new ArrayList<>();

            // Lấy thông tin các sản phẩm đã chọn từ cơ sở dữ liệu
            for (Long itemId : selectedItemIds) {
                CartItemsEntity item = cartDAO.getSelectedItemById(itemId);
                if (item != null) {
                    selectedItems.add(item);
                    //totalPrice += item.getPrice() * item.getQuantity(); // Tính tổng giá trị
                    totalPrice += item.getPrice(); // Tính tổng giá trị
                }
            }
            
            System.out.println(">>>>>>>>>>>>>" + totalPrice);
            
            DiscountsEntity discount = discountsDAO.getDiscountByCode(discountCode);
            if(discount == null && !discountCode.isEmpty()) {
            	redirectAttributes.addFlashAttribute("alertMessage", "Mã giảm giá không hợp lệ!");
        		redirectAttributes.addFlashAttribute("alertType", "error");
                return "redirect:/cart/view";
            }
            
            if(discount != null) {
            	if(discount.getApplyTo().equals("user")) {
            		System.out.println("Vô điều kiện nè");
                	if(discount.getDiscountType().equals("percentage")) {
                		totalPrice = totalPrice * (1- (double) discount.getDiscountValue() / 100);
                	}else {
                		totalPrice = totalPrice - discount.getDiscountValue();
                	}
                }
            }
            
            List<DiscountsEntity> listDiscountsAvailable = new ArrayList<DiscountsEntity>();
            List<DiscountsEntity> listDiscounts = discountsDAO.getAllDiscounts();
            for(DiscountsEntity discountCheck : listDiscounts) {
            	if(discountCheck.getApplyTo().equals("user")) {
            		if(discountCheck.getMinOrderValue() <= totalPrice && discountCheck.getStatus().equals("active")) {
            			listDiscountsAvailable.add(discountCheck);
            		}
            	}
            }
            
            if (!discountCode.isEmpty()) {
                boolean isDiscountValid = listDiscountsAvailable.stream()
                        .anyMatch(d -> d.getCode().equals(discountCode));
                if (!isDiscountValid) {
                    redirectAttributes.addFlashAttribute("alertMessage", "Mã giảm giá không hợp lệ!");
                    redirectAttributes.addFlashAttribute("alertType", "error");
                    return "redirect:/cart/view";
                }
            }
            
            
            if (selectedItems.isEmpty()) {
                throw new Exception("Không có sản phẩm hợp lệ để thanh toán.");
            }

            // Tạo một đơn hàng mới
            OrdersEntity newOrder = new OrdersEntity();
            
            newOrder.setUser(user);
            newOrder.setCustomerName(name);
            newOrder.setCustomerPhone(phone);
            newOrder.setShippingAddress(address);
            newOrder.setTotalPrice(totalPrice);
            
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 20);
            newOrder.setUuid(uuid);
            
            newOrder.setPaymentMethod(paymentMethod);
            if(paymentMethod.equals("PayPal") || paymentMethod.equals("VnPay")) {
            	newOrder.setPaymentStatus("Đã thanh toán");
            }else {
            	newOrder.setPaymentStatus("Chưa thanh toán");
            }
            newOrder.setOrderStatus("Chờ xác nhận");
            newOrder.setCreatedAt(new Date());
            newOrder.setUpdatedAt(new Date());

            // Lưu đơn hàng và lấy orderId
            Long orderId = orderDAO.createOrder(newOrder);
            if (orderId == null) {
                throw new Exception("Không thể tạo đơn hàng.");
            }
            if(discount != null) {
            	Order_DiscountsEntity orderDiscountEntity = new Order_DiscountsEntity();
                orderDiscountEntity.setDiscount_id(discount);
                orderDiscountEntity.setOrder_id(newOrder);
                Boolean isCreateOrderDiscount = discountsDAO.createOrderDiscount(orderDiscountEntity);
                if(!isCreateOrderDiscount) {
                	redirectAttributes.addFlashAttribute("alertMessage", "Có lỗi với mã giảm giá");
            		redirectAttributes.addFlashAttribute("alertType", "error");
            		return "redirect:/cart/view";
                }
            }
            
            System.out.println("Tới dòng 231");
            
            // Lấy đối tượng OrdersEntity từ orderId
            OrdersEntity order = orderDAO.getOrderById(orderId);
            
            // Lưu chi tiết đơn hàng từ các sản phẩm đã chọn
            for (CartItemsEntity cartItem : selectedItems) {
                OrdersDetailEntity orderDetail = new OrdersDetailEntity();
                orderDetail.setOrder(order); // Set đơn hàng hiện tại
                orderDetail.setBook(cartItem.getBook()); // Set sản phẩm
                orderDetail.setQuantity(cartItem.getQuantity()); // Set số lượng
                orderDetail.setPrice(cartItem.getPrice()); // Set giá
                orderDetail.setCreatedAt(new Date());
                orderDetail.setUpdatedAt(new Date());
                // Lưu chi tiết đơn hàng
                orderDetailDAO.saveOrderDetail(orderDetail);
                //System.out.println("!!!!!!!!!!!!!!!!!!" + cartItem.getBook().getId());
                InventoryEntity inventoryOfCurrentBook = inventoryDAO.getInventoryByBookId(cartItem.getBook().getId());
               
                Integer currentStockQuantity = inventoryOfCurrentBook.getStock_quantity();
                inventoryOfCurrentBook.setStock_quantity(currentStockQuantity - cartItem.getQuantity());
               // System.out.println("currentStockQuantity: " + (currentStockQuantity - cartItem.getQuantity() ));
                boolean isUpdateStockQuantity = inventoryDAO.updateInventoryStock(inventoryOfCurrentBook);
                //System.out.println("isUpdateStockQuantity: " + isUpdateStockQuantity);
            }

            // Xóa giỏ hàng sau khi thanh toán thành công
            cartDAO.clearCart(userId, selectedItemIds);

            // Redirect với thông báo thành công
            redirectAttributes.addFlashAttribute("alertMessage", "Thanh toán thành công!");
    		redirectAttributes.addFlashAttribute("alertType", "success");
    		sendMailOrderSuccess(order);
            return "redirect:/index";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("alertMessage", "Thanh toán thất bại! Vui lòng thử lại.");
    		redirectAttributes.addFlashAttribute("alertType", "error");
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/createMethodVnPay", method = RequestMethod.POST)
    public String submidOrder(@RequestParam("name") String name, @RequestParam("phone") String phone,
    		@RequestParam("province") String province, @RequestParam("district") String district, @RequestParam("ward") String ward,
    		@RequestParam("street") String street,
	          //@RequestParam("paymentMethod") String paymentMethod,
	          @RequestParam("selectedItems") List<Long> selectedItemIds, 
	          @RequestParam(value = "discountCode", defaultValue = "") String discountCoded, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes){
    		
    	name = EscapeHtmlUtil.encodeHtml(name);
    	phone = EscapeHtmlUtil.encodeHtml(phone);
    	street = EscapeHtmlUtil.encodeHtml(street);
    	String discountCode = EscapeHtmlUtil.encodeHtml(discountCoded);
    	StringBuilder shippingAddressBuilder = new StringBuilder();
		shippingAddressBuilder.append(street).append(", ")
		                      .append(ward).append(", ")
		                      .append(district).append(", ")
		                      .append(province);
		String address = shippingAddressBuilder.toString();
		
		try {
        	UsersEntity userSession = (UsersEntity) session.getAttribute("user");
            Long userId = userSession.getId();


            // Kiểm tra xem có sản phẩm nào được chọn hay không
            if (selectedItemIds == null || selectedItemIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("alertMessage", "Vui lòng chọn sản phẩm để thanh toán.");
        		redirectAttributes.addFlashAttribute("alertType", "error");
        		return "redirect:/cart/view";
            }

            // Lấy thông tin người dùng
            UsersEntity user = userDAO.getUserById(userId);

            
            
            // Tính tổng giá trị đơn hàng (ví dụ, nếu bạn đã có cách tính giá từ selectedItemIds)
            Double totalPrice = 0.0;
            List<CartItemsEntity> selectedItems = new ArrayList<>();

            // Lấy thông tin các sản phẩm đã chọn từ cơ sở dữ liệu
            for (Long itemId : selectedItemIds) {
                CartItemsEntity item = cartDAO.getSelectedItemById(itemId);
                if (item != null) {
                    selectedItems.add(item);
                    //totalPrice += item.getPrice() * item.getQuantity(); // Tính tổng giá trị
                    totalPrice += item.getPrice(); // Tính tổng giá trị
                }
            }
            
            
            DiscountsEntity discount = discountsDAO.getDiscountByCode(discountCode);
            if(discount == null && !discountCode.isEmpty()) {
            	redirectAttributes.addFlashAttribute("alertMessage", "Mã giảm giá không hợp lệ!");
        		redirectAttributes.addFlashAttribute("alertType", "error");
                return "redirect:/cart/view";
            }
            
            List<DiscountsEntity> listDiscountsAvailable = new ArrayList<DiscountsEntity>();
            List<DiscountsEntity> listDiscounts = discountsDAO.getAllDiscounts();
            for(DiscountsEntity discountCheck : listDiscounts) {
            	if(discountCheck.getApplyTo().equals("user")) {
            		if(discountCheck.getMinOrderValue() <= totalPrice && discountCheck.getStatus().equals("active")) {
            			listDiscountsAvailable.add(discountCheck);
            		}
            	}
            }
            
            if (!discountCode.isEmpty()) {
                boolean isDiscountValid = listDiscountsAvailable.stream()
                        .anyMatch(d -> d.getCode().equals(discountCode));
                if (!isDiscountValid) {
                    redirectAttributes.addFlashAttribute("alertMessage", "Mã giảm giá không hợp lệ!");
                    redirectAttributes.addFlashAttribute("alertType", "error");
                    return "redirect:/cart/view";
                }
            }
            
            if(discount != null) {
            	if(discount.getApplyTo().equals("user")) {
                	if(discount.getDiscountType().equals("percentage")) {
                		totalPrice = totalPrice * (1- (double) discount.getDiscountValue() / 100);
                	}else {
                		totalPrice = totalPrice - discount.getDiscountValue();
                	}
                }
            }
            if (selectedItems.isEmpty()) {
                throw new Exception("Không có sản phẩm hợp lệ để thanh toán.");
            }

            // Tạo một đơn hàng mới
            OrdersEntity newOrder = new OrdersEntity();
            newOrder.setUser(user); // Gán đối tượng UsersEntity vào OrdersEntity
            newOrder.setCustomerName(name);
            newOrder.setCustomerPhone(phone);
            newOrder.setShippingAddress(address);
            newOrder.setTotalPrice(totalPrice);
            newOrder.setPaymentMethod("VnPay");
            newOrder.setPaymentStatus("Chưa thanh toán");
            newOrder.setOrderStatus("Chờ xác nhận");
            newOrder.setCreatedAt(new Date());
            newOrder.setUpdatedAt(new Date());

            // Lưu đơn hàng và lấy orderId
            Long orderId = orderDAO.createOrder(newOrder);
            if (orderId == null) {
                throw new Exception("Không thể tạo đơn hàng.");
            }
            if(discount != null) {
            	Order_DiscountsEntity orderDiscountEntity = new Order_DiscountsEntity();
                orderDiscountEntity.setDiscount_id(discount);
                orderDiscountEntity.setOrder_id(newOrder);
                Boolean isCreateOrderDiscount = discountsDAO.createOrderDiscount(orderDiscountEntity);
                if(!isCreateOrderDiscount) {
                	redirectAttributes.addFlashAttribute("alertMessage", "Có lỗi với mã giảm giá");
            		redirectAttributes.addFlashAttribute("alertType", "error");
            		return "redirect:/cart/view";
                }
            }
            
            
            // Lấy đối tượng OrdersEntity từ orderId
            OrdersEntity order = orderDAO.getOrderById(orderId);
            
            // Lưu chi tiết đơn hàng từ các sản phẩm đã chọn
            for (CartItemsEntity cartItem : selectedItems) {
                OrdersDetailEntity orderDetail = new OrdersDetailEntity();
                orderDetail.setOrder(order); // Set đơn hàng hiện tại
                orderDetail.setBook(cartItem.getBook()); // Set sản phẩm
                orderDetail.setQuantity(cartItem.getQuantity()); // Set số lượng
                orderDetail.setPrice(cartItem.getPrice()); // Set giá
                orderDetail.setCreatedAt(new Date());
                orderDetail.setUpdatedAt(new Date());
                // Lưu chi tiết đơn hàng
                orderDetailDAO.saveOrderDetail(orderDetail);
                InventoryEntity inventoryOfCurrentBook = inventoryDAO.getInventoryByBookId(cartItem.getBook().getId());
               
                Integer currentStockQuantity = inventoryOfCurrentBook.getStock_quantity();
                inventoryOfCurrentBook.setStock_quantity(currentStockQuantity - cartItem.getQuantity());
               // System.out.println("currentStockQuantity: " + (currentStockQuantity - cartItem.getQuantity() ));
                boolean isUpdateStockQuantity = inventoryDAO.updateInventoryStock(inventoryOfCurrentBook);
                //System.out.println("isUpdateStockQuantity: " + isUpdateStockQuantity);
            }

            // Xóa giỏ hàng sau khi thanh toán thành công
            cartDAO.clearCart(userId, selectedItemIds);
            
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    		String vnpayUrl = vnpayService.createOrder(request, totalPrice.longValue(), "Thanh toan don hang:" + orderId, baseUrl);
    		//System.out.println("vnpayUrl: " + vnpayUrl);
    		return "redirect:" + vnpayUrl;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("alertMessage", "Thanh toán thất bại! Vui lòng thử lại.");
    		redirectAttributes.addFlashAttribute("alertType", "error");
            return "redirect:/index";
        }
	
		
    }
    
    @RequestMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception{
    	int paymentStatus = vnpayService.orderReturn(request);
    	//System.out.println("vnp_TransactionStatus: " + request.getParameter("vnp_TransactionStatus"));
    	try {
    		// Lấy mã đơn hàng từ VNPay
            String orderInfo = request.getParameter("vnp_OrderInfo");
            String orderIdStr = orderInfo.split(":")[1].trim();
            Long orderId = Long.parseLong(orderIdStr);
            
            OrdersEntity order = orderDAO.getOrderById(orderId);
            if (order == null) {
                throw new Exception("Không tìm thấy đơn hàng.");
            }
            
            if (paymentStatus == 1) {
                // Cập nhật trạng thái đơn hàng
                order.setPaymentStatus("Đã thanh toán");
                order.setUpdatedAt(new Date());
                orderDAO.updateOrder(order);
                redirectAttributes.addFlashAttribute("alertMessage", "Thanh toán thành công!");
                redirectAttributes.addFlashAttribute("alertType", "success");
                sendMailOrderSuccess(order);
                return "redirect:/index";
            } else{
                // Xóa đơn hàng nếu thanh toán thất bại
                orderDAO.deleteOrder(orderId);
                redirectAttributes.addFlashAttribute("alertMessage", "Đã huỷ thanh toán!");
                redirectAttributes.addFlashAttribute("alertType", "error");
                return "redirect:/cart/view";
            }
    	} catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("alertMessage", "Đã xảy ra lỗi trong quá trình xử lý đơn hàng.");
            redirectAttributes.addFlashAttribute("alertType", "error");
            return "redirect:/cart/view";
        }
    }
    
    
    // Trang hiển thị thành công
    @RequestMapping("/success")
    public String paymentSuccess() {
        return "cart/success";
    }
    
    public void sendMailOrderSuccess(OrdersEntity order) {
        String emailContent = "<html><body>"
                + "<h5>Hello " + order.getUser().getFullname() + ",</h5>"
                + "<p>Congratulations! Your order with Order ID: <strong>" + order.getUuid() + "</strong> has been placed successfully.</p>"
                + "<p>Order Details:</p>"
                + "<ul>"
                + "<li><strong>Customer Name:</strong> " + order.getCustomerName() + "</li>"
                + "<li><strong>Shipping Address:</strong> " + order.getShippingAddress() + "</li>"
                + "<li><strong>Total Price:</strong> " + String.format("%,.2f", order.getTotalPrice()) + " VND</li>"
                + "</ul>"
                + "<p>Your order is currently under processing. We will notify you once it is shipped.</p>"
                + "<p>Thank you for shopping with us!</p>"
                + "<p>Best regards,</p>"
                + "<p>Book Store ALDPT</p>"
                + "</body></html>";

        mailService.sendMail(emailContent, order.getUser().getEmail(), "Order Confirmation - Order ID: " + order.getUuid());
    }

}

