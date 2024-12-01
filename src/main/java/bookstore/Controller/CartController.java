package bookstore.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import bookstore.DAO.BooksDAO;
import bookstore.DAO.CartDAO;
import bookstore.Entity.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private BooksDAO bookDAO;
    @Autowired
    private CartDAO cartDAO;

    // Hiển thị giỏ hàng
    @RequestMapping("/view")
    public String viewCart(Model model) {
        // Giả sử cartId là cố định, bạn có thể điều chỉnh cho phù hợp với hệ thống của bạn
        Long cartId = (long) 1;
        List<CartItemsEntity> cartItems = cartDAO.getAllBooksInCart(cartId);
        
        // Đưa danh sách này vào Model để hiển thị
        model.addAttribute("cartItems", cartItems);
        
        return "cart/view"; // Trả về trang giỏ hàng
    }

    // Thêm sản phẩm vào giỏ hàng
    @RequestMapping("/add")
    public String addToCart(@RequestParam("bookId") Long bookId,
                            @RequestParam("quantity") int quantity,
                            Model model, RedirectAttributes redirectAttributes) {
        Long userId = (long) 1;  // Giả sử userId là 1, có thể lấy từ session
        System.out.println("bookid: " + bookId);

        try {
            cartDAO.addToCart(userId, bookId, quantity);
            String successMessage = "Sản phẩm đã được thêm vào giỏ hàng!";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/index.htm"; // Quay lại trang chủ
        } catch (Exception e) {
            String errorMessage = "Không thể thêm sản phẩm vào giỏ hàng. Vui lòng thử lại!";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/index.htm"; // Quay lại trang chủ
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @RequestMapping("/remove")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId, Model model, RedirectAttributes redirectAttributes) {
        try {
            cartDAO.removeCartItem(cartItemId, model);
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng!");
            return "redirect:/cart/view.htm";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sản phẩm khỏi giỏ hàng. Vui lòng thử lại!");
            return "redirect:/cart/view.htm";
        }
        
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @RequestMapping(value = "/update_quantity", method = RequestMethod.GET)
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId,
            @RequestParam("quantity") int quantity, Model model) {
    	System.out.println("cartItemId: " + cartItemId);
    	System.out.println("quantity: " + quantity);
		try {
		// Cập nhật số lượng trong giỏ hàng
		cartDAO.updateQuantity(cartItemId, quantity);  // Giả sử bạn có phương thức updateQuantity trong CartDAO
		
		// Sau khi cập nhật, bạn có thể quay lại trang giỏ hàng hoặc trang khác
		model.addAttribute("message", "Số lượng sản phẩm đã được cập nhật thành công!");
		return "redirect:/cart/view.htm";  // Điều hướng đến trang giỏ hàng sau khi cập nhật
		} catch (Exception e) {
		model.addAttribute("errorMessage", "Lỗi khi cập nhật số lượng sản phẩm!");
		return "redirect:/cart/view.htm";  // Điều hướng đến trang giỏ hàng nếu có lỗi
		}
	}
}