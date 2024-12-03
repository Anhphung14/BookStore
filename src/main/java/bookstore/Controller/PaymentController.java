package bookstore.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import bookstore.DAO.CartDAO;
import bookstore.DAO.OrderDAO;
import bookstore.DAO.OrderDetailDAO;
import bookstore.DAO.UserDAO;
import bookstore.Entity.*;

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

    // Hiển thị trang thanh toán
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkoutPage(/* @RequestParam("userId") Long userId, */
			 @RequestParam(value = "selectedItems", required = false) List<Long> selectedItemIds,
		        Model model) {

    	Long userId = 1L; // Giả định userId = 1, bạn có thể lấy từ session
        System.out.println("List selected: " + selectedItemIds);

        if (selectedItemIds == null || selectedItemIds.isEmpty()) {
            model.addAttribute("errorMessage", "Bạn chưa chọn sản phẩm nào để thanh toán.");
            return "redirect:/cart/view.htm";
        }

        // Lấy thông tin người dùng
        UsersEntity user = userDAO.getUserById(userId);
        model.addAttribute("user", user);

        // Lấy các sản phẩm đã chọn từ selectedItemIds
        List<CartItemsEntity> selectedItems = new ArrayList<>();
        for (Long selectedId : selectedItemIds) {
            System.out.println("Selected_id: " + selectedId);
            // Gọi phương thức getSelectedItemByBookId từ CartDAO để lấy CartItemsEntity
            CartItemsEntity item = cartDAO.getSelectedItemById(selectedId);
            selectedItems.add(item);
        }

        System.out.println("Selected cart items: " + selectedItems);
        model.addAttribute("cartItems", selectedItems);

        // Tính tổng giá trị giỏ hàng (nếu cần)
        double totalPrice = 0;
        for (CartItemsEntity item : selectedItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        // Chuyển đến trang thanh toán
        return "cart/checkout";
    }

 // Xử lý thanh toán
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String processCheckout(@RequestParam("address") String address,
                                  @RequestParam("payment") String paymentMethod,
                                  @RequestParam("selectedItems") List<Long> selectedItemIds, // Lấy danh sách ID các sản phẩm được chọn
                                  RedirectAttributes redirectAttributes) {
        try {
            // Set cứng userId là 1
            Long userId = 1L;

            // Kiểm tra phương thức thanh toán
            if (paymentMethod == null || paymentMethod.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn phương thức thanh toán.");
                return "redirect:/payment/checkout.htm";
            }

            // Kiểm tra xem có sản phẩm nào được chọn hay không
            if (selectedItemIds == null || selectedItemIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn sản phẩm để thanh toán.");
                return "redirect:/payment/checkout.htm";
            }

            // Lấy thông tin người dùng
            UsersEntity user = userDAO.getUserById(userId); // Giả sử bạn đã có UserDAO để tìm người dùng

            // Tính tổng giá trị đơn hàng (ví dụ, nếu bạn đã có cách tính giá từ selectedItemIds)
            double totalPrice = 0;
            List<CartItemsEntity> selectedItems = new ArrayList<>();

            // Lấy thông tin các sản phẩm đã chọn từ cơ sở dữ liệu
            for (Long itemId : selectedItemIds) {
                CartItemsEntity item = cartDAO.getSelectedItemById(itemId);
                if (item != null) {
                    selectedItems.add(item);
                    totalPrice += item.getPrice() * item.getQuantity(); // Tính tổng giá trị
                }
            }

            if (selectedItems.isEmpty()) {
                throw new Exception("Không có sản phẩm hợp lệ để thanh toán.");
            }

            // Tạo một đơn hàng mới
            OrdersEntity newOrder = new OrdersEntity();
            newOrder.setUser(user); // Gán đối tượng UsersEntity vào OrdersEntity
            newOrder.setShippingAddress(address);
            newOrder.setTotalPrice(totalPrice);
            newOrder.setOrderStatus("Chờ xử lý"); // 1: Chờ xử lý
            newOrder.setCreatedAt(new Date());
            newOrder.setUpdatedAt(new Date());

            // Lưu đơn hàng và lấy orderId
            Long orderId = orderDAO.createOrder(newOrder);
            if (orderId == null) {
                throw new Exception("Không thể tạo đơn hàng.");
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
            }

            // Xóa giỏ hàng sau khi thanh toán thành công
            cartDAO.clearCart(userId, selectedItemIds);

            // Redirect với thông báo thành công
            redirectAttributes.addFlashAttribute("successMessage", "Thanh toán thành công!");
            return "redirect:/index.htm";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Thanh toán thất bại! Vui lòng thử lại.");
            return "redirect:/index.htm";
        }
    }


    // Trang hiển thị thành công
    @RequestMapping("/success")
    public String paymentSuccess() {
        return "cart/success";
    }
}

