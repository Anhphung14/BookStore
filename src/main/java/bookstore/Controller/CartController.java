package bookstore.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/view")
    public String viewCart(Model model, HttpSession session) {
        // Giả sử cartId là cố định, bạn có thể điều chỉnh cho phù hợp với hệ thống của bạn
    	UsersEntity user = (UsersEntity) session.getAttribute("user");
        Long cartId = user.getCart().getId();
        Long countBooksInCart = cartDAO.countItemsInCart(cartId);
        session.setAttribute("countBooksInCart", countBooksInCart);
        List<CartItemsEntity> cartItems = cartDAO.getAllBooksInCart(cartId);
     // Tính tổng tiền giỏ hàng
        double totalPrice = 0;
        for (CartItemsEntity item : cartItems) {
            totalPrice += item.getBook().getPrice() * item.getQuantity(); // Giá sản phẩm * số lượng
        }
        //System.out.println("cartItems: " + cartItems);
        
        // Đưa danh sách này vào Model để hiển thị
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice); // Tổng tiền giỏ hàng
        
        return "cart/view"; // Trả về trang giỏ hàng
    }

    // Thêm sản phẩm vào giỏ hàng
    /*@RequestMapping("/add")
    public String addToCart(@RequestParam("bookId") Long bookId,
                            @RequestParam("quantity") int quantity,
                            Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	UsersEntity user = (UsersEntity) session.getAttribute("user");
    	Long userId = user.getId();  
        //System.out.println("bookid: " + bookId);
    	
        try {
            cartDAO.addToCart(userId, bookId, quantity);
            redirectAttributes.addFlashAttribute("alertMessage", "Sản phẩm đã được thêm vào giỏ hàng!");
    		redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/index.htm"; // Quay lại trang chủ
        } catch (Exception e) {
        	redirectAttributes.addFlashAttribute("alertMessage", "Không thể thêm sản phẩm vào giỏ hàng. Có lỗi xảy ra!");
    		redirectAttributes.addFlashAttribute("alertType", "error");
            return "redirect:/index.htm"; // Quay lại trang chủ
        }
    }*/
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
	/* ResponseEntity<Map<String, Object>> */
    public String addToCart(@RequestParam("bookId") Long bookId,
                                                          @RequestParam("quantity") int quantity,
                                                          HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("Vô đây");
        try {
            // Lấy người dùng từ session
            UsersEntity user = (UsersEntity) session.getAttribute("user");
            System.out.println("Vô đâyyyy");
            Long userId = user.getId();  
            cartDAO.addToCart(userId, bookId, quantity);
            Long countBooksInCart = cartDAO.countItemsInCart(user.getCart().getId());
	            
            return String.valueOf(countBooksInCart);
        } catch (Exception e) {
            return "error";
        }
    }


    // Xóa sản phẩm khỏi giỏ hàng
    @RequestMapping("/remove")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId, Model model, RedirectAttributes redirectAttributes) {
        try {
            String isDeleted = cartDAO.removeCartItem(cartItemId);
            if(isDeleted.equals("Xóa thành công!")) {
            	redirectAttributes.addFlashAttribute("alertMessage", "Sản phẩm đã được xóa khỏi giỏ hàng!");
        		redirectAttributes.addFlashAttribute("alertType", "success");
                return "redirect:/cart/view.htm";
            }else if(isDeleted.equals("Sản phẩm không tồn tại!")) {
            	redirectAttributes.addFlashAttribute("alertMessage", "Sản phẩm không tồn tại!");
        		redirectAttributes.addFlashAttribute("alertType", "error");
                return "redirect:/cart/view.htm";
            }else {
            	redirectAttributes.addFlashAttribute("alertMessage", "Có lỗi xảy ra!");
        		redirectAttributes.addFlashAttribute("alertType", "error");
                return "redirect:/cart/view.htm";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sản phẩm khỏi giỏ hàng. Vui lòng thử lại!");
            return "redirect:/cart/view.htm";
        }
        
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @RequestMapping(value = "/update_quantity", method = RequestMethod.GET)
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId,
            @RequestParam("quantity") int quantity, Model model, RedirectAttributes redirectAttributes) {
    	//System.out.println("cartItemId: " + cartItemId);
    	//System.out.println("quantity: " + quantity);
		try {
		// Cập nhật số lượng trong giỏ hàng
		cartDAO.updateQuantity(cartItemId, quantity);  // Giả sử bạn có phương thức updateQuantity trong CartDAO
		
		// Sau khi cập nhật, bạn có thể quay lại trang giỏ hàng hoặc trang khác
		redirectAttributes.addFlashAttribute("alertMessage", "Số lượng sản phẩm đã được cập nhật thành công!");
		redirectAttributes.addFlashAttribute("alertType", "success");
		return "redirect:/cart/view.htm";  // Điều hướng đến trang giỏ hàng sau khi cập nhật
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertMessage", "Lỗi khi cập nhật số lượng sản phẩm!");
			redirectAttributes.addFlashAttribute("alertType", "error");
			return "redirect:/cart/view.htm";  // Điều hướng đến trang giỏ hàng nếu có lỗi
		}
	}
}