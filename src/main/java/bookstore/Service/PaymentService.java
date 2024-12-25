package bookstore.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import bookstore.DAO.CartDAO;
import bookstore.Entity.CartItemsEntity;

@Service
public class PaymentService {
	
	@Autowired
	private CartDAO cartDAO;
	
	private static final String CLIENT_ID = "AZzuNyaP3AzHAUUzLAOkhRz0imoN1dmXQHN0FEczevPzFJK-c6tQwI-VD79x6dZ7eOuMR2kn4eBjPUnB";
	private static final String CLIENT_SECRET = "EG9F8oMUxPBioZ7sutysij-oDtME9CJVfHu5w6hw9OsuehEwniaN5ZXgltEP4lY13gZ4KYjj9Ef7y7yX";
	private static final String MODE = "sandbox";
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		Payment payment = new Payment().setId(paymentId);
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return payment.execute(apiContext, paymentExecution);
	}	
	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException{
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}
	
	public String authorizePayment(String name, String phone, List<Long> selectedItemIds, String discountCode, double totalPrice, String address) throws Exception{
		Payer payer = getPayerInformation();
		RedirectUrls redirectUrls = getRedirectUrls();
		
		List<Transaction> transactionList = getTransactionInformation(name, phone, selectedItemIds, discountCode, totalPrice, address);
		 
		
		Payment requestPayment = new Payment();
		requestPayment.setTransactions(transactionList); 
		requestPayment.setRedirectUrls(redirectUrls);
		requestPayment.setPayer(payer);
		requestPayment.setIntent("authorize");
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		Payment approvedPayment = requestPayment.create(apiContext);
		System.out.println("1111111111"+approvedPayment);
		
		return getApprovalLink(approvedPayment);
	}
	
	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvedLink = null;
		for(Links link: links) {
			if(link.getRel().equalsIgnoreCase("approval_url")) {
				approvedLink = link.getHref();
			}
		}
		return approvedLink;
	}
	
	private String convertNumberFromGermanToUsFormat(String germanNumber) {
		String usNumber = germanNumber.replace(",",".");
		return usNumber;
	}
	
	
	public static double getExchangeRate() throws Exception {
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/VND"; // API miễn phí
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONObject("rates").getDouble("USD"); // Lấy tỉ giá USD
    }
	
	private List<Transaction> getTransactionInformation(String name, String phone, List<Long> selectedItemIds, String discountCode, double totalPrice, String address) throws Exception {
	    // Tính tổng tiền và đảm bảo khớp với subtotal và total
	    double calculatedSubtotal = 0;
	  //  System.out.println("Payment_Service selectedItemIds: " + selectedItemIds);
	    double exchangeRate = getExchangeRate();
	    List<Item> items = new ArrayList<Item>();
	    for (Long itemId : selectedItemIds) {
            CartItemsEntity cartItem = cartDAO.getSelectedItemById(itemId);
            if (cartItem != null) {
            	Item item = new Item();
            	item.setSku(String.valueOf(cartItem.getId()));
            	item.setCurrency("USD");
            	item.setName(cartItem.getBook().getTitle()); // Tên sản phẩm
            	item.setPrice(String.format(Locale.US, "%.2f",(cartItem.getPrice() / cartItem.getQuantity()) * exchangeRate));          // Số lượng
            	item.setQuantity(String.valueOf(cartItem.getQuantity()));   
            	items.add(item);// Giá tiền
            	calculatedSubtotal += (cartItem.getPrice() * exchangeRate);
            }
        }
	    // Kiểm tra nếu tổng tiền không khớp với giá trị truyền vào
	    if (Math.abs(calculatedSubtotal - (totalPrice * exchangeRate)) > 0.01) {
	        throw new IllegalArgumentException("Subtotal does not match total price");
	    }

	    // Thiết lập chi tiết giao dịch
	    Details details = new Details();
	    details.setSubtotal(String.format(Locale.US, "%.2f", calculatedSubtotal));
	    details.setTax("0.00");
	    details.setShipping("0.00");

	    Amount amount = new Amount();
	    amount.setCurrency("USD");
	    amount.setTotal(String.format(Locale.US, "%.2f", calculatedSubtotal));

	    amount.setDetails(details);
	    
	 // Tách chuỗi address thành mảng
	    String[] addressParts = address.split(",\\s*"); // Tách các phần tử dựa trên dấu phẩy và khoảng trắng

	    // Kiểm tra và gán giá trị từng phần (nếu có)
	    String line1 = addressParts.length > 0 ? addressParts[0] : ""; // Phần đầu tiên
	    String line2 = addressParts.length > 1 ? addressParts[1] : ""; // Phần thứ hai (nếu có)
	    String city = addressParts.length > 2 ? addressParts[2] : ""; // Thành phố/quận/huyện
	    String state = addressParts.length > 3 ? addressParts[3] : ""; // Tỉnh/bang
	    String postalCode = ""; // Bạn cần cập nhật giá trị phù hợp nếu có

	    ItemList itemList = new ItemList();
	    itemList.setItems(items);
		/* Set thông tin giao hàng mới */
	    itemList.setShippingAddress((ShippingAddress) new ShippingAddress()
	    		.setRecipientName(name)  
	            .setPhone(phone)
	            .setLine1(line1)         
	            .setLine2(line2)        
	            .setCity(city)           
	            .setState(state)         
	            .setPostalCode(postalCode) // Mã bưu chính (thay giá trị thực tế)
	            .setCountryCode("VN"));       
	    
	    
	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    List<String> productNames = items.stream()
                .map(Item::getName) // Lấy tên sản phẩm từ mỗi item
                .collect(Collectors.toList());
	    transaction.setDescription("Order for products: " + String.join(", ", productNames));
	    transaction.setItemList(itemList);

	    List<Transaction> transactionList = new ArrayList<>();
	    transactionList.add(transaction);
	    return transactionList;
	}
	
	private RedirectUrls getRedirectUrls() { 
		RedirectUrls redirectUrls = new RedirectUrls(); 
		redirectUrls.setCancelUrl("http://localhost:8080/bookstore/cancel_payment"); 
		redirectUrls.setReturnUrl("http://localhost:8080/bookstore/review_payment"); 
		return redirectUrls; 
		}
	
	private Payer getPayerInformation() {
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName("Mai_TEST");
		payerInfo.setLastName("Anh_TEST");
		payerInfo.setEmail("maianha52021TESTTTT@gmail.com");
		Payer payer = new Payer();
		payer.setPaymentMethod("PayPal");
		payer.setPayerInfo(payerInfo);
		return payer;
	}
}
