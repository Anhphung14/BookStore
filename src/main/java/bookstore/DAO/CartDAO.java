package bookstore.DAO;

import java.util.Date;
import java.util.List;

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
                // Nếu giỏ hàng chưa tồn tại, tạo mới
                cart = new CartsEntity();
                String hqlUser = "FROM UsersEntity u WHERE u.id = :userId";
                Query queryUser = session.createQuery(hqlUser);
                queryUser.setParameter("userId", userId);
                UsersEntity user = (UsersEntity) queryUser.uniqueResult();
                //System.out.println("user: " + user);

                if (user == null) {
                    throw new IllegalArgumentException("User with ID " + userId + " not found!");
                }

                cart.setUser(user);
                cart.setStatus(0); // Trạng thái giỏ hàng: 0 = đang mở
                session.persist(cart); // Sử dụng persist để lưu giỏ hàng
                //System.out.println("Tạo giỏ mới thành công");
            }

            // Truy vấn để kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
            String hqlCartItem = "FROM CartItemsEntity ci WHERE ci.cart.id = :cartId AND ci.book.id = :bookId";
            Query queryCartItem = session.createQuery(hqlCartItem);
            queryCartItem.setParameter("cartId", cart.getId());
            queryCartItem.setParameter("bookId", bookId);
            CartItemsEntity cartItem = (CartItemsEntity) queryCartItem.uniqueResult();
            //System.out.println("cartItem: " + cartItem);

            if (cartItem != null) {
                // Nếu đã có, cập nhật số lượng và giá
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setPrice(cartItem.getBook().getPrice() * cartItem.getQuantity());
                cartItem.setUpdatedAt(new Date());
                session.update(cartItem); // Cập nhật thông tin giỏ hàng
                //System.out.println("Cập nhật thành công sản phẩm trong giỏ hàng");
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

                cartItem = new CartItemsEntity();
                cartItem.setCart(cart);
                cartItem.setBook(book);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(book.getPrice() * quantity);
                cartItem.setCreatedAt(new Date());
                cartItem.setUpdatedAt(new Date());
                session.persist(cartItem); // Sử dụng persist để lưu sản phẩm mới vào giỏ hàng
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

        // Viết câu truy vấn HQL để chỉ xóa những sản phẩm đã chọn trong giỏ hàng của user
        String hql = "DELETE FROM CartItemsEntity ci WHERE ci.cart.id IN (SELECT od.id FROM CartsEntity od WHERE od.user.id = :userId) AND ci.id IN :selectedItemIds";


        // Mở session và bắt đầu transaction
        Session session = sessionFactory.getCurrentSession();
        // Không cần explicit Transaction, vì @Transactional sẽ quản lý việc này
        try {
            // Thực hiện câu truy vấn xóa sản phẩm trong giỏ hàng theo các ID được chọn
            Query query = session.createQuery(hql);  // Không cần phải sử dụng Query<?> nữa
            query.setParameter("userId", userId); // Set parameter userId
            query.setParameterList("selectedItemIds", selectedItemIds); // Set parameter selectedItemIds
            query.executeUpdate(); // Thực hiện câu truy vấn

            // No need to commit, @Transactional will handle it
        } catch (Exception e) {
            // Nếu có lỗi, Spring @Transactional sẽ tự động rollback
            throw e;
        }
    }

    
    public String removeCartItem(Long cartItemId) {
        try {
        	Session session = sessionFactory.getCurrentSession();
            CartItemsEntity cartItem = (CartItemsEntity) session.get(CartItemsEntity.class, cartItemId);
            if (cartItem != null) {
                session.delete(cartItem); 
                session.flush();
                return "Xóa thành công!";
            } else {
                return "Sản phẩm không tồn tại!";
            }
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
        // Mở session và bắt đầu transaction
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Truy vấn CartItem theo cartItemId
            CartItemsEntity cartItem = (CartItemsEntity) session.get(CartItemsEntity.class, cartItemId);

            if (cartItem != null) {
                // Cập nhật số lượng mới
                cartItem.setQuantity(quantity);

                // Cập nhật giá lại dựa trên số lượng mới
                cartItem.setPrice(cartItem.getBook().getPrice() * quantity);

                // Cập nhật thời gian chỉnh sửa
                cartItem.setUpdatedAt(new Date());

                // Cập nhật CartItem vào cơ sở dữ liệu
                session.update(cartItem);
                System.out.println("Cập nhật số lượng thành công!");

                // Commit transaction
                transaction.commit();
            } else {
                throw new IllegalArgumentException("CartItem không tồn tại với ID: " + cartItemId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage(), e);
        } finally {
            // Đảm bảo đóng session
            if (session != null) {
                session.close();
            }
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
