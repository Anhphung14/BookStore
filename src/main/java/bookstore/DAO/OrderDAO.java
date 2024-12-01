package bookstore.DAO;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.OrdersEntity;
@Repository
@Transactional
public class OrderDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    public OrdersEntity getOrderWithDetails(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT o FROM OrdersEntity o LEFT JOIN FETCH o.orderDetails WHERE o.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (OrdersEntity) query.uniqueResult();
    }
	
    public Long createOrder(OrdersEntity order) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(order); // Lưu đơn hàng hoặc cập nhật nếu đã tồn tại
        return order.getId(); // Trả về ID của đơn hàng mới tạo
    }
    
    
    // Phương thức lấy đơn hàng theo ID 
    public OrdersEntity getOrderById(Long orderId) {
        // Lấy session mới từ sessionFactory
        Session session = sessionFactory.openSession();

        try {
            // Truy vấn đơn hàng theo ID
            OrdersEntity order = (OrdersEntity) session.get(OrdersEntity.class, orderId);

            // Nếu không tìm thấy đơn hàng, trả về null
            if (order == null) {
                throw new EntityNotFoundException("Đơn hàng không tồn tại với ID: " + orderId);
            }

            return order;

        } finally {
            // Đảm bảo đóng session sau khi sử dụng
            session.close();
        }
    }

    
    public double calculateTotalPrice(Long userId) {
        
    	String hql = "SELECT SUM(od.price * od.quantity) FROM OrdersDetailEntity od LEFT JOIN od.order o WHERE o.user.id = :userId";
        Session session = sessionFactory.openSession();
        try {
            // Chuyển từ BigDecimal sang double
            Double totalPrice = (Double) session.createQuery(hql)
                    .setParameter("userId", userId)
                    .uniqueResult();
            
            // Nếu totalPrice là null, trả về 0.0
            return totalPrice != null ? totalPrice : 0.0;
        } finally {
            session.close();
        }
    }
}
