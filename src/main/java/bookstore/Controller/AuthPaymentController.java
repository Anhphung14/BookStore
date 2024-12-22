package bookstore.Controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.Service.PaymentService;

@Controller
public class AuthPaymentController {
	@Autowired
	PaymentService paymentService;

	
	
	@RequestMapping(value = "payment/authorize_paypal", method = RequestMethod.POST)
	public String authorizePayment(@RequestParam("name") String name, 
	                               @RequestParam("phone") String phone,
	                               @RequestParam("province") String province, @RequestParam("district") String district, @RequestParam("ward") String ward,
	                       		@RequestParam("street") String street,
	                               @RequestParam(value = "discountCode", defaultValue = "") String discountCode,
	                               @RequestParam("totalPrice") double totalPrice,
	                               @RequestParam("selectedItems") List<Long> selectedItemIds,
	                               //@RequestParam("address") String address,
	                               Model model, RedirectAttributes redirectAttributes) {

	    try {
	    	StringBuilder shippingAddressBuilder = new StringBuilder();
			shippingAddressBuilder.append(street).append(", ")
			                      .append(ward).append(", ")
			                      .append(district).append(", ")
			                      .append(province);
			String address = shippingAddressBuilder.toString();
	        String approvalLink = paymentService.authorizePayment(name, phone, selectedItemIds, discountCode, totalPrice, address);
	        // Chuyển hướng người dùng đến URL thanh toán
	        return "redirect:" + approvalLink;

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("alertMessage", "Có lỗi xảy ra trong quá trình thanh toán!");
	        redirectAttributes.addFlashAttribute("alertType", "error");
	        return "redirect:/errorPage";
	    }
	}
} 
