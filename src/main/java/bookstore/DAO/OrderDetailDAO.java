package bookstore.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
