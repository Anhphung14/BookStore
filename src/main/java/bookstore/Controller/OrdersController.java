package bookstore.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bookstore.Entity.*;
import bookstore.Service.MailService;
import bookstore.Utils.EscapeHtmlUtil;
import bookstore.DAO.DiscountsDAO;
import bookstore.DAO.OrderDAO;
import bookstore.DAO.ShippingAddressDAO;
@Transactional
@Controller
@RequestMapping("/admin1337")
public class OrdersController {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	ShippingAddressDAO shippingAddressDAO;
	@Autowired
	DiscountsDAO discountsDAO;
	@Autowired
	MailService mailService;
	
	
	@RequestMapping("/orders")
	public String index(ModelMap modelMap, @RequestParam(value = "customerName", required = false) String customerName, @RequestParam(value = "fromDate", required = false) String fromDate, @RequestParam(value = "toDate", required = false) String toDate, 
			@RequestParam(value = "minPrice", required = false) Double minPrice, @RequestParam(value = "maxPrice", required = false) Double maxPrice, @RequestParam(value = "paymentStatus", required = false) String paymentStatus,
			@RequestParam(value = "orderStatus", required = false) String orderStatus) throws ParseException {

		List<OrdersEntity> listOrders = new ArrayList<OrdersEntity>();
		if(customerName != null || fromDate != null || toDate != null || minPrice != null || maxPrice != null || paymentStatus != null || orderStatus != null) {
			listOrders = orderDAO.searchOrders(customerName, fromDate, toDate, minPrice, maxPrice, paymentStatus, orderStatus);
		}else{
			listOrders = orderDAO.listOrders();
		}
		//System.out.println("listOrders: " + listOrders);
		for(OrdersEntity order: listOrders) {
			Hibernate.initialize(order.getOrderDetails());
			DiscountsEntity discount = orderDAO.getDiscountUsedByOrder(order.getId());
			Double discountValue = 0.0;
			if(discount != null) {
				if (discount != null) {
		        	if(discount.getDiscountType().equals("percentage")) {
		        		discountValue = (order.getTotalPrice() / (1 - (double) discount.getDiscountValue() / 100)) - order.getTotalPrice();
		        	}else {
		        		discountValue = discount.getDiscountValue().doubleValue();
		        	}
		        }
			}
			order.setDiscountValue(discountValue);
		}
		List<OrdersEntity> listOrderAutoCancelEntities =  orderDAO.autoCancelUnconfirmedOrders();
		if(listOrderAutoCancelEntities.size() != 0) {
			String notify = "Đơn hàng ";
			for(OrdersEntity order: listOrderAutoCancelEntities) {
				notify += order.getId() + " ,";
				
				
				String emailContent = "<html><body>"
		                + "<h5>Hello " + order.getUser().getFullname() + ",</h5>"
		                + "<p>We regret to inform you that your order with Order ID: " + order.getId() + " has been cancelled due to some unforeseen circumstances from the seller's side.</p>"
		                + "<p>We apologize for any inconvenience this may have caused. If you have any questions or concerns, please feel free to contact our support team.</p>"
		                + "<p>Thank you for your understanding.</p>"
		                + "<p>Best regards,</p>"
		                + "<p>Book Store ALDPT</p>"
		                + "</body></html>";
					
				 mailService.sendMail(emailContent, order.getUser().getEmail(), "Notice of order cancellation due to special circumstances from our side.");
			}
			if(notify.endsWith(",")) {
				 notify = notify.substring(0, notify.length() - 1);
			}
			notify += " đã được huỷ tự động!";
			modelMap.addAttribute("alertMessage", notify);
			modelMap.addAttribute("alertType", "error");
	        
		}
		
		
		modelMap.addAttribute("customerName", customerName);
		modelMap.addAttribute("fromDate", fromDate);
		modelMap.addAttribute("toDate", toDate);
		modelMap.addAttribute("minPrice", minPrice);
		modelMap.addAttribute("maxPrice", maxPrice);
		modelMap.addAttribute("paymentStatus", paymentStatus);
		modelMap.addAttribute("orderStatus", orderStatus);
		modelMap.addAttribute("listOrders", listOrders);
		
		return "orders/index";
	}
	
	@RequestMapping(value = "/orders/updateOrderStatus", method = RequestMethod.POST)
	public String updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("orderStatus") String orderStatus, RedirectAttributes redirectAttributes) {
		orderStatus = EscapeHtmlUtil.encodeHtml(orderStatus);
		if(orderDAO.updateOrderStatus(orderId, orderStatus)) {
			redirectAttributes.addFlashAttribute("alertMessage", "Order status updated successfully!");
	        redirectAttributes.addFlashAttribute("alertType", "success");
		}else {
			redirectAttributes.addFlashAttribute("alertMessage", "Error updating order status!");
			redirectAttributes.addFlashAttribute("alertType", "error");
		}
		
		return "redirect:/admin1337/orders.htm";
	}
	
	@RequestMapping("/order/edit/{orderId}")
	public String editOrder(@PathVariable("orderId") Long orderId, ModelMap modelMap) {
		OrdersEntity order = orderDAO.getOrderWithDetails(orderId);
		Map<String, Map<String, List<String>>> locationData = shippingAddressDAO.getProvincesWithDistrictsAndWards();
		ObjectMapper objectMapper = new ObjectMapper();
	    String locationDataJson = "";
	    try {
	        locationDataJson = objectMapper.writeValueAsString(locationData);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace(); // Log lỗi nếu có
	    }
		//System.out.println(locationDataJson);
	    Hibernate.initialize(order.getOrderDiscounts());
	    
		modelMap.addAttribute("locationData", locationDataJson);
		modelMap.addAttribute("order", order);
		//System.out.println("order: " + order.getId());
		return "orders/edit";
	}
	
	@RequestMapping(value = "/order/edit", method = RequestMethod.POST)
	public String updateOrder(@RequestParam("orderId") Long orderId ,@RequestParam("totalPrice") Double totalPrice, @RequestParam("paymentMethod") String paymentMethod,
			@RequestParam("paymentStatus") String paymentStatus, @RequestParam("orderStatus") String orderStatus,
			@RequestParam("province") String province, @RequestParam("district") String district,
			@RequestParam("ward") String ward, @RequestParam("street") String street,
			RedirectAttributes redirectAttributes) {
		//System.out.println("total: " + totalPrice);
		street = EscapeHtmlUtil.encodeHtml(street);
		StringBuilder shippingAddressBuilder = new StringBuilder();
		shippingAddressBuilder.append(street).append(", ")
		                      .append(ward).append(", ")
		                      .append(district).append(", ")
		                      .append(province);

		String shippingAddress = shippingAddressBuilder.toString();
		//System.out.println(shippingAddress);
		
		OrdersEntity order = orderDAO.getOrderById(orderId);
		if(order != null) {
			order.setTotalPrice(totalPrice);
			order.setPaymentMethod(paymentMethod);
			order.setPaymentStatus(paymentStatus);
			order.setOrderStatus(orderStatus);
			order.setShippingAddress(shippingAddress);
			boolean isUpdated = orderDAO.updateOrder(order);
			 if (isUpdated) {
			        redirectAttributes.addFlashAttribute("alertMessage", "Order updated successfully!");
			        redirectAttributes.addFlashAttribute("alertType", "success");
			    } else {
			        redirectAttributes.addFlashAttribute("alertMessage", "Failed to update order.");
			        redirectAttributes.addFlashAttribute("alertType", "danger");
			    }
		}else {
			redirectAttributes.addFlashAttribute("alertMessage", "Order not found.");
		    redirectAttributes.addFlashAttribute("alertType", "danger");
		}
		
		
		return "redirect:/admin1337/orders.htm";
	}
	
	@RequestMapping(value = "/order/updateOrderItems", method = RequestMethod.POST)
	public String updateOrderItems(@RequestParam("orderId") Long orderId,
	                                @RequestParam Map<String, String> formParams, 
	                                RedirectAttributes redirectAttributes) {
	   
		
	    OrdersEntity order = orderDAO.getOrderById(orderId);
		//System.out.println("orderId: " + orderId);
	    
	    //System.out.println("formParams" + formParams);
	    
        Map<Integer, Integer> quantities = new HashMap<>();

        // Duyệt qua tất cả các tham số trong formParams
        for (String key : formParams.keySet()) { 
        	if (key.startsWith("orderDetailsQuantity")) {
                // Lấy index của orderDetailsQuantity
                String indexStr = key.split("\\[|\\]")[1];  // Lấy index (ví dụ: [4], [5])
                int index = Integer.parseInt(indexStr);
                String quantityStr = formParams.get(key);  // Lấy giá trị quantity
                quantities.put(index, Integer.parseInt(quantityStr));
            }
        }
        
        
        
        double totalPrice = 0.0;
        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            Long key = entry.getKey().longValue();
            Integer value = entry.getValue();
            
            OrdersDetailEntity orderDetail = orderDAO.getOrderDetailById(key);
            orderDetail.setQuantity(value);
            orderDAO.updateOrderItems(orderDetail);
            totalPrice += orderDetail.getPrice() * value;
        }

		System.out.println("totalPriceBefore: " + totalPrice);
        
        DiscountsEntity discountEntity = orderDAO.getDiscountUsedByOrder(orderId);
        if (discountEntity != null) {
        	if(discountEntity.getDiscountType().equals("percentage")) {
        		totalPrice = totalPrice * (1 - (double) discountEntity.getDiscountValue() / 100);
        	}else {
        		totalPrice = totalPrice - discountEntity.getDiscountValue();
        	}
        }
       
        
        order.setTotalPrice(totalPrice);
        orderDAO.updateOrder(order);

	    redirectAttributes.addFlashAttribute("alertMessage", "Order items updated successfully.");
        redirectAttributes.addFlashAttribute("alertType", "success");
	    return "redirect:/admin1337/orders.htm";
	}




}
