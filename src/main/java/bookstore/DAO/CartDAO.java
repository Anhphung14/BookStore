package bookstore.DAO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import bookstore.Entity.*;



@Repository
@Transactional
public class CartDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Lấy tất cả các CartItem thuộc một Cart
    public List<CartItemsEntity> getAllBooksInCart(Long cartId) {
        Session session = sessionFactory.getCurrentSession();

        // Truy vấn để lấy tất cả CartItem thuộc Cart có id = cartId
        String hql = "FROM CartItemsEntity ci WHERE ci.cart.id = :cartId";
        Query query = session.createQuery(hql);
        query.setParameter("cartId", cartId);

        // Trả về danh sách CartItem
        return query.list();
    }
    
    public long countItemsInCart(Long cartId) {
        Session session = sessionFactory.getCurrentSession();

        // HQL query để đếm số lượng sách trong giỏ hàng
        String hql = "SELECT COUNT(ci) FROM CartItemsEntity ci WHERE ci.cart.id = :cartId";
        Query query = session.createQuery(hql);
        query.setParameter("cartId", cartId);

        // Trả về số lượng sách trong giỏ hàng
        return (Long) query.uniqueResult(); // Kết quả trả về là kiểu Long
    }


    // Thêm một Book vào Cart
    public void addToCart(Long userId, Long bookId, int quantity) {
        Session session = sessionFactory.getCurrentSession();

        try {
            // Truy vấn để kiểm tra xem giỏ hàng của người dùng đã tồn tại hay chưa
            String hqlCart = "FROM CartsEntity c WHERE c.user.id = :userId";
            Query queryCart = session.createQuery(hqlCart);
            queryCart.setParameter("userId", userId);
            CartsEntity cart = (CartsEntity) queryCart.uniqueResult();
            //System.out.println("giỏ hàng: " + cart);

            if (cart == null) {
                try {
                    // Gọi stored procedure để tạo mới giỏ hàng
                    String storedProcedure = "EXEC CreateNewCart :userId, :status";
                    Query query = session.createSQLQuery(storedProcedure)
                                         .setParameter("userId", userId)
                                         .setParameter("status", 0); // Trạng thái giỏ hàng: 0 = đang mở

                    // Lấy ID của giỏ hàng vừa được tạo
                    List<Object> result = query.list();

                    if (!result.isEmpty()) {
                        Long cartId = Long.valueOf(result.get(0).toString());
                        cart = (CartsEntity) session.get(CartsEntity.class, cartId); // Lấy lại đối tượng Cart từ ID
                        System.out.println("Tạo giỏ mới thành công với ID: " + cartId);
                    } else {
                        throw new RuntimeException("Không thể tạo giỏ hàng mới cho userId: " + userId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Lỗi khi tạo giỏ hàng mới: " + e.getMessage(), e);
                }
            }


            // Truy vấn để kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
            String hqlCartItem = "FROM CartItemsEntity ci WHERE ci.cart.id = :cartId AND ci.book.id = :bookId";
            Query queryCartItem = session.createQuery(hqlCartItem);
            queryCartItem.setParameter("cartId", cart.getId());
            queryCartItem.setParameter("bookId", bookId);
            CartItemsEntity cartItem = (CartItemsEntity) queryCartItem.uniqueResult();
            //System.out.println("cartItem: " + cartItem);

            if (cartItem != null) {
                try {
                    // Gọi stored procedure để cập nhật thông tin sản phẩm trong giỏ hàng
                    String storedProcedure = "EXEC UpdateCartItemQuantity :cartItemId, :quantity";
                    Query query = session.createSQLQuery(storedProcedure)
                                         .setParameter("cartItemId", cartItem.getId())
                                         .setParameter("quantity", cartItem.getQuantity() + quantity);

                    // Thực thi stored procedure
                    List<Object> result = query.list();

                    // Kiểm tra kết quả trả về
                    if (!result.isEmpty()) {
                        String message = result.get(0).toString();
                        System.out.println(message); // Log kết quả trả về
                    } else {
                        throw new RuntimeException("Không thể cập nhật sản phẩm trong giỏ hàng.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Lỗi khi cập nhật sản phẩm trong giỏ hàng: " + e.getMessage(), e);
                }
            } else {
                // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
                String hqlBook = "FROM BooksEntity b WHERE b.id = :bookId";
                Query queryBook = session.createQuery(hqlBook);
                queryBook.setParameter("bookId", bookId);
                BooksEntity book = (BooksEntity) queryBook.uniqueResult();
                //System.out.println("book: " + book);

                if (book == null) {
                    throw new IllegalArgumentException("Book with ID " + bookId + " not found!");
                }

             // Thay thế đoạn code bằng việc gọi Stored Procedure
//                cartItem = new CartItemsEntity();
//                cartItem.setCart(cart);
//                cartItem.setBook(book);
//                cartItem.setQuantity(quantity);
//                cartItem.setPrice(book.getPrice() * quantity);
//                cartItem.setCreatedAt(new Date());
//                cartItem.setUpdatedAt(new Date());
//                session.persist(cartItem); // Sử dụng persist để lưu sản phẩm mới vào giỏ hàng
                
                String storedProcedure = "EXEC AddCartItem :cart_id, :book_id, :quantity, :price";
                Query query = session.createSQLQuery(storedProcedure);
                query.setParameter("cart_id", cart.getId());
                query.setParameter("book_id", book.getId());
                query.setParameter("quantity", quantity);
                query.setParameter("price", book.getPrice() * quantity);

                // Thực thi Stored Procedure
                query.executeUpdate();

                //System.out.println("Thêm sản phẩm vào giỏ hàng thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage(), e);
        }
    }

  //xóa sp trong giỏ hàng sau khi thanh toán
    public void clearCart(Long userId, List<Long> selectedItemIds) {
        if (selectedItemIds == null || selectedItemIds.isEmpty()) {
            return;  // Không làm gì nếu không có sản phẩm nào được chọn
        }

        Session session = sessionFactory.getCurrentSession();
        try {
            // Chuỗi ID, ví dụ: "1,2,3"
            String selectedIds = selectedItemIds.stream()
                                                 .map(String::valueOf)
                                                 .collect(Collectors.joining(","));

            // Gọi stored procedure
            String storedProcedure = "EXEC ClearSelectedCartItems :userId, :selectedItemIds";
            Query query = session.createSQLQuery(storedProcedure)
                                 .setParameter("userId", userId)
                                 .setParameter("selectedItemIds", selectedIds);

            // Thực thi stored procedure
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Spring @Transactional sẽ tự động rollback nếu có lỗi
        }
    }

    public String removeCartItem(Long cartItemId) {
        try {
            Session session = sessionFactory.getCurrentSession();

            // Use a native query to call the stored procedure
            String storedProcedure = "EXEC DeleteCartItem :cart_item_id";
            Query query = session.createSQLQuery(storedProcedure);
            query.setParameter("cart_item_id", cartItemId);

            
            List<String> result = query.list();

           
            return result.isEmpty() ? "Có lỗi khi xoá!" : result.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi khi xoá!";
        }
    }

    
    //đếm số lượng sản phẩm trong giỏ hàng
    public int getCartItemCount(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT SUM(ci.quantity) FROM CartItemsEntity ci WHERE ci.cart.user.id = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Long count = (Long) query.uniqueResult();
        return count != null ? count.intValue() : 0;
    }
    
    public CartItemsEntity getSelectedItemById(Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        
        // HQL để thực hiện JOIN giữa CartItemsEntity và BooksEntity
        String hql = "FROM CartItemsEntity ct WHERE id = :bookId";
        Query query = session.createQuery(hql);
        query.setParameter("bookId", bookId); // Thiết lập tham số bookId
        
        return (CartItemsEntity) query.uniqueResult(); // Trả về CartItemsEntity đã kết hợp thông tin sách
    }

	/*
	 * public void updateQuantity(Long cartItemId, int quantity) { Session session =
	 * sessionFactory.getCurrentSession();
	 * 
	 * try { // Truy vấn để lấy CartItem theo cartItemId CartItemsEntity cartItem =
	 * (CartItemsEntity) session.get(CartItemsEntity.class, cartItemId);
	 * 
	 * if (cartItem != null) { // Cập nhật số lượng mới
	 * cartItem.setQuantity(quantity); // Cập nhật giá lại dựa trên số lượng mới
	 * cartItem.setPrice(cartItem.getBook().getPrice() * quantity); // Cập nhật thời
	 * gian chỉnh sửa cartItem.setUpdatedAt(new Date());
	 * 
	 * // Cập nhật CartItem vào cơ sở dữ liệu session.update(cartItem);
	 * System.out.println("Cập nhật số lượng thành công!"); } else { throw new
	 * IllegalArgumentException("CartItem không tồn tại với ID: " + cartItemId); } }
	 * catch (Exception e) { e.printStackTrace(); throw new
	 * RuntimeException("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage(), e);
	 * } }
	 */
   
    public void updateQuantity(Long cartItemId, int quantity) {
        Session session = sessionFactory.getCurrentSession();
        try {
            // Gọi stored procedure
            String storedProcedure = "EXEC UpdateCartItemQuantity :cartItemId, :quantity";
            Query query = session.createSQLQuery(storedProcedure)
                                 .setParameter("cartItemId", cartItemId)
                                 .setParameter("quantity", quantity);

            // Thực thi stored procedure
            List<String> result = query.list();
            
            // Kiểm tra kết quả trả về
            if (!result.isEmpty()) {
                System.out.println(result.get(0)); // In ra thông báo từ stored procedure
            } else {
                System.out.println("Không nhận được phản hồi từ stored procedure.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage(), e);
        }
    }

    
    public boolean saveNewCart(CartsEntity cart) {
    	Session session = sessionFactory.openSession();
    	Transaction t = session.beginTransaction();
    	
    	try {
    		session.save(cart);
    		t.commit();
    		
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		t.rollback();
    	} finally {
			session.close();
		}
    	
    	return false;
    }

    
}
