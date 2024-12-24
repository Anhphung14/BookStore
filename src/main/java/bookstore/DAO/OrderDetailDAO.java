package bookstore.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.BooksEntity;
import bookstore.Entity.OrdersDetailEntity;

@Repository
@Transactional
public class OrderDetailDAO {
    @Autowired
    private SessionFactory sessionFactory;
    

    public List<OrdersDetailEntity> getCartItems(Long userId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT od FROM OrdersDetailEntity od JOIN od.order o WHERE o.user.id = :userId AND o.status = :status";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("status", 0);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cart items for user ID: " + userId, e);
        }
    }
    
    
    //lưu vào order_detail
    public void saveOrderDetail(OrdersDetailEntity orderDetail) {
        Session session = sessionFactory.getCurrentSession();
        try {
            // Chuỗi SQL để gọi stored procedure
            String sql = "EXEC SaveOrderDetail :orderId, :bookId, :quantity, :price, :createdAt, :updatedAt";

            // Tạo Query và thiết lập tham số
            Query query = session.createSQLQuery(sql)
                                 .setParameter("orderId", orderDetail.getOrder().getId()) // Lấy orderId từ đối tượng
                                 .setParameter("bookId", orderDetail.getBook().getId()) // Lấy bookId từ đối tượng
                                 .setParameter("quantity", orderDetail.getQuantity()) // Lấy số lượng từ đối tượng
                                 .setParameter("price", orderDetail.getPrice()) // Lấy giá từ đối tượng
                                 .setParameter("createdAt", orderDetail.getCreatedAt()) // Lấy thời gian tạo
                                 .setParameter("updatedAt", orderDetail.getUpdatedAt()); // Lấy thời gian cập nhật

            // Thực thi stored procedure
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể lưu chi tiết đơn hàng: " + e.getMessage(), e);
        }
    }
    
    public Map<String, Object> getBestSellingBook() {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT od.book.id, SUM(od.quantity) FROM OrdersDetailEntity od " +
                         "GROUP BY od.book.id " +
                         "ORDER BY SUM(od.quantity) DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);

            Object[] result = (Object[]) query.uniqueResult();

            if (result != null && result.length == 2) {
                Long bookId = (Long) result[0];
                Long totalQuantity = (Long) result[1];

                // Lấy thông tin sách bán chạy nhất từ ID
                BooksEntity bestSellingBook = (BooksEntity) session.get(BooksEntity.class, bookId);

                // Tạo một Map chứa sách bán chạy nhất và tổng số lượng bán ra
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("bestSellingBook", bestSellingBook);
                resultMap.put("totalQuantity", totalQuantity);

                return resultMap;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching best selling book", e);
        }
        return null;
    }




	/*
	 * //xóa giỏ hàng sau khi thanh toán public void clearCart(Long userId) { String
	 * hql = "DELETE FROM OrdersDetailEntity od WHERE od.userId = :userId"; Session
	 * session = sessionFactory.openSession(); Transaction transaction =
	 * session.beginTransaction(); try { session.createQuery(hql)
	 * .setParameter("userId", userId) .executeUpdate(); transaction.commit(); }
	 * catch (Exception e) { transaction.rollback(); throw e; } finally {
	 * session.close(); } }
	 */
}
