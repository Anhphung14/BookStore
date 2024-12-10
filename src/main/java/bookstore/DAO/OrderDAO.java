package bookstore.DAO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.DiscountsEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.Order_DiscountsEntity;
import bookstore.Entity.OrdersDetailEntity;
import bookstore.Entity.OrdersEntity;
@Repository
@Transactional
public class OrderDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private InventoryDAO inventoryDAO;
    
    
    public List<OrdersEntity> listOrders(){
    	Session session = sessionFactory.getCurrentSession();
    	String hql = "From OrdersEntity o JOIN FETCH o.user";
    	Query query = session.createQuery(hql);
    	return query.list();
    }
    
    public List<OrdersEntity> searchOrders(String customerName, String fromDate, String toDate, Double minPrice, Double maxPrice, String paymentStatus, String orderStatus) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
    	String hql = "FROM OrdersEntity o WHERE 1=1";  

        if (customerName != null && !customerName.isEmpty()) {
            hql += " AND o.user.fullname LIKE :customerName";
        }
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            hql += " AND CONVERT(date, o.createdAt) BETWEEN :fromDate AND :toDate";
        }

        if (minPrice != null) {
            hql += " AND o.price >= :minPrice";
        }
        if (maxPrice != null) {
            hql += " AND o.price <= :maxPrice";
        }
        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            hql += " AND o.paymentStatus = :paymentStatus";
        }
        if (orderStatus != null && !orderStatus.isEmpty()) {
            hql += " AND o.orderStatus = :orderStatus";
        }

        Query query = session.createQuery(hql);
        if (customerName != null && !customerName.isEmpty()) {
            query.setParameter("customerName", "%" + customerName + "%");
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(fromDate);
            query.setParameter("fromDate", date);
        }
        if (toDate != null && !toDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(toDate);
            query.setParameter("toDate", date);
        }
        
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            query.setParameter("paymentStatus", paymentStatus);
        }
        if (orderStatus != null && !orderStatus.isEmpty()) {
            query.setParameter("orderStatus", orderStatus);
        }

        List<OrdersEntity> orders = query.list();
        return orders;
    }

    
    public boolean updateOrderStatus(Long orderId, String orderStatus) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            OrdersEntity order = (OrdersEntity) session.get(OrdersEntity.class, orderId);
            
            if (order != null) {
                if (orderStatus.equals("Hoàn thành") && order.getPaymentMethod().equals("COD")) {
                    order.setPaymentStatus("Đã thanh toán");
                }
                if(orderStatus.equals("Huỷ đơn hàng")) {
                	if(order.getPaymentStatus().equals("Đã thanh toán")) {
                		order.setPaymentStatus("Đã hoàn tiền");
                	}
        	        for(OrdersDetailEntity orderDetail : order.getOrderDetails()) {
        	        	System.out.println("orderDetail.getBook().getId(): " + orderDetail.getBook().getId());
        	        	InventoryEntity inventoryOfCurrentBook = inventoryDAO.getInventoryByBookId(orderDetail.getBook().getId());
                        Integer currentStockQuantity = inventoryOfCurrentBook.getStock_quantity();
                        inventoryOfCurrentBook.setStock_quantity(currentStockQuantity + 1);
                        boolean isUpdateStockQuantity = inventoryDAO.updateInventory(inventoryOfCurrentBook);
        	        }
                }
                order.setOrderStatus(orderStatus);
                session.update(order);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public OrdersEntity getOrderWithDetails(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT o FROM OrdersEntity o LEFT JOIN FETCH o.orderDetails WHERE o.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (OrdersEntity) query.uniqueResult();
    }
	
    public Long createOrder(OrdersEntity order) {
        Session session = sessionFactory.getCurrentSession();
        
        System.out.println(order.toString());
        
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
    
    public void save(OrdersEntity ordersEntity) {
    	Session session = sessionFactory.getCurrentSession();
        // Lưu hoặc cập nhật OrdersEntity
        if (ordersEntity.getId() == null) {
            // Nếu là bản ghi mới (chưa có id), thì persist OrdersEntity
        	session.persist(ordersEntity);
        } else {
            // Nếu đã có id (bản ghi cũ), thì merge OrdersEntity
        	session.merge(ordersEntity);
        }
    }
    
    public boolean updateOrder(OrdersEntity order) {
    	try {
	    	Session session = sessionFactory.getCurrentSession();
	    	session.update(order);
	    	return true;
		} catch (Exception e) {
			e.printStackTrace();
	        return false;
			// TODO: handle exception
		}
    }
    
    public OrdersDetailEntity getOrderDetailById(Long ordersDetailId) {
    	// Lấy session mới từ sessionFactory
        Session session = sessionFactory.openSession();

        try {
        	OrdersDetailEntity orderDetail = (OrdersDetailEntity) session.get(OrdersDetailEntity.class, ordersDetailId);

            // Nếu không tìm thấy đơn hàng, trả về null
            if (orderDetail == null) {
                throw new EntityNotFoundException("Không tìm thấy orderDetail: " + ordersDetailId);
            }

            return orderDetail;

        } finally {
            // Đảm bảo đóng session sau khi sử dụng
            session.close();
        }
    }
    
    public void updateOrderItems(OrdersDetailEntity orderDetail) {
    	try {
	    	Session session = sessionFactory.getCurrentSession();
	    	session.update(orderDetail);
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public DiscountsEntity getDiscountUsedByOrder(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        
        // Truy vấn để lấy giảm giá cho một đơn hàng
        String hql = "SELECT d FROM DiscountsEntity d JOIN d.orderDiscountsEntity od WHERE od.order_id.id = :orderId";
        Query query = session.createQuery(hql);
        query.setParameter("orderId", orderId);
        
        // Trả về giảm giá đầu tiên nếu có, hoặc null nếu không có
        List<DiscountsEntity> discounts = query.list();
        if (discounts != null && !discounts.isEmpty()) {
            return discounts.get(0); 
        }
        return null; 
    }

   
    
    
    public List<OrdersEntity> getOrdersByUserId(Long userId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM OrdersEntity o WHERE o.user.id = :userId ORDER BY o.createdAt DESC";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		List<OrdersEntity> listOrder = query.list();
		return listOrder;
	}
	
	public List<OrdersDetailEntity> getOrderDetailsByOrderId(Long orderId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM OrdersDetailEntity od WHERE od.order.id = :orderId";
		Query query = session.createQuery(hql);
		query.setParameter("orderId", orderId);
		List<OrdersDetailEntity> listOrderDetail = query.list();
		return listOrderDetail;
	}
	
	public List<Order_DiscountsEntity> getOrderDiscountsByOrderId(Long orderId) {
	    Session session = sessionFactory.getCurrentSession();
	    String hql = "FROM Order_DiscountsEntity od WHERE od.order_id.id = :orderId";
	    Query query = session.createQuery(hql);
	    query.setParameter("orderId", orderId);
	    List<Order_DiscountsEntity> listOrderDiscount = query.list();
	    return listOrderDiscount;
	}
	
	public OrdersEntity getOrderByOrderId(Long orderId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM OrdersEntity WHERE id = :orderId";
		OrdersEntity order = (OrdersEntity) session.createQuery(hql).setParameter("orderId", orderId).uniqueResult();
		return order;
	}
	
	public int updateOrderStatusToCancel(Long orderId) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		int isUpdate = 0;
		try {
			String hql = "FROM OrdersEntity WHERE id = :orderId";
			OrdersEntity updateOrder = (OrdersEntity) session.createQuery(hql).setParameter("orderId", orderId).uniqueResult();
			//OrderEntity updateOrder = getOrderByOrderId(orderId);
			if(updateOrder.getPaymentStatus().equals("Đã thanh toán")) {
				updateOrder.setPaymentStatus("Đã hoàn tiền");
			}
			updateOrder.setUpdatedAt(new Date());
			updateOrder.setOrderStatus("Huỷ đơn hàng");
			session.update(updateOrder);
			t.commit();
			isUpdate = 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
		}
		return isUpdate;
	}
}
