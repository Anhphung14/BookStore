package bookstore.DAO;

import java.util.List;

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
        session.saveOrUpdate(orderDetail); // Lưu hoặc cập nhật đối tượng OrdersDetailEntity
    }
    public BooksEntity getBestSellingBook() {
        try {
            Session session = sessionFactory.getCurrentSession();
            // Sửa câu lệnh HQL để lấy ID của sách và tổng số lượng, sau đó chọn sách bán chạy nhất
            String hql = "SELECT od.book.id, SUM(od.quantity) FROM OrdersDetailEntity od " +
                         "GROUP BY od.book.id " +  // Nhóm theo ID của sách
                         "ORDER BY SUM(od.quantity) DESC"; // Sắp xếp theo tổng số lượng bán ra
            Query query = session.createQuery(hql);
            query.setMaxResults(1); // Lấy quyển sách bán chạy nhất

            // Lấy kết quả, đây là một mảng Object, phần tử đầu tiên là ID của sách
            Object[] result = (Object[]) query.uniqueResult();
            
            if (result != null) {
                Long bookId = (Long) result[0];  // ID của sách bán chạy nhất
                // Lấy thông tin chi tiết sách bán chạy nhất từ ID
                BooksEntity bestSellingBook = (BooksEntity) session.get(BooksEntity.class, bookId);
                return bestSellingBook; // Trả về sách bán chạy nhất
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching best selling book", e);
        }
        return null; // Trả về null nếu không có sách nào
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
