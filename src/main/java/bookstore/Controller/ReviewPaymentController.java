package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import bookstore.Service.*;

@Controller
public class ReviewPaymentController {
	
	@RequestMapping(value = "/review_payment", method = RequestMethod.GET)
	public String printHello(@RequestParam(value = "paymentId") String paymentId,
	                         @RequestParam(value = "PayerID") String payerId,
	                         ModelMap model) {
	    try {
	        // Khởi tạo service và thực hiện giao dịch
	        PaymentService paymentService = new PaymentService();
	        Payment payment = paymentService.executePayment(paymentId, payerId);
	        
	        // Lấy thông tin người thanh toán và giao dịch
	        PayerInfo payerInfo = payment.getPayer().getPayerInfo();
	        Transaction transaction = payment.getTransactions().get(0);
	        ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
	        
	        // Thêm các đối tượng vào model để truyền đến view
	        model.addAttribute("payer", payerInfo);
	        model.addAttribute("transaction", transaction);
	        model.addAttribute("shippingAddress", shippingAddress);
	      //  System.out.println("review_payer: " + payerInfo);
	     //   System.out.println("review_transaction: " + transaction);
	    //    System.out.println("review_shippingAddress: " + shippingAddress);
	        // Trả về tên view, không cần ModelAndView nữa
	        return "cart/review_payment";  // Trả về tên view JSP
	    } catch (PayPalRESTException ex) {
	        // Xử lý lỗi nếu có
	        ex.printStackTrace();
	        model.addAttribute("error", "An error occurred during payment processing.");
	        return "error";  // Trả về view error nếu có lỗi
	    }
	}

}
