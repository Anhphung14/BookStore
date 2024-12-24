package bookstore.DAO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.DiscountsEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.Order_DiscountsEntity;
import bookstore.Entity.OrdersDetailEntity;
import bookstore.Entity.OrdersEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Service.MailService;
@Repository
@Transactional
public class OrderDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private InventoryDAO inventoryDAO;
    @Autowired
	MailService mailService;
    
    
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
                        inventoryOfCurrentBook.setStock_quantity(currentStockQuantity + orderDetail.getQuantity());
                        System.out.println("currentStockQuantity + orderDetail.getQuantity(): " + currentStockQuantity + orderDetail.getQuantity());
                        boolean isUpdateStockQuantity = inventoryDAO.updateInventoryStock(inventoryOfCurrentBook);
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
    
	
    public Long createOrder(OrdersEntity newOrder) {
        Session session = sessionFactory.getCurrentSession();

        try {
            // Tạo SQL để gọi stored procedure
            String sql = "DECLARE @orderId BIGINT; " +
                         "EXEC CreateNewOrder :userId, :customerName, :customerPhone, :shippingAddress, " +
                         ":totalPrice, :paymentMethod, :paymentStatus, :orderStatus, :createdAt, :updatedAt, @orderId OUTPUT; " +
                         "SELECT @orderId AS orderId";

            // Tạo Query và thiết lập tham số
            Query query = session.createSQLQuery(sql)
                                 .setParameter("userId", newOrder.getUser().getId())
                                 .setParameter("customerName", newOrder.getCustomerName())
                                 .setParameter("customerPhone", newOrder.getCustomerPhone())
                                 .setParameter("shippingAddress", newOrder.getShippingAddress())
                                 .setParameter("totalPrice", newOrder.getTotalPrice())
                                 .setParameter("paymentMethod", newOrder.getPaymentMethod())
                                 .setParameter("paymentStatus", newOrder.getPaymentStatus())
                                 .setParameter("orderStatus", newOrder.getOrderStatus())
                                 .setParameter("createdAt", newOrder.getCreatedAt())
                                 .setParameter("updatedAt", newOrder.getUpdatedAt());

            // Thực thi stored procedure và lấy kết quả
            Object result = query.uniqueResult();

            // Kiểm tra kết quả trả về
            if (result != null) {
                return ((Number) result).longValue(); // Chuyển đổi kết quả thành Long
            } else {
                throw new RuntimeException("Không thể lấy ID của đơn hàng mới.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo đơn hàng: " + e.getMessage(), e);
        }
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
	        // Chuỗi SQL để gọi stored procedure
	        String sql = "EXEC UpdateOrderStatusToCancel :orderId";

	        // Tạo Query và thiết lập các tham số
	        Query query = session.createSQLQuery(sql)
	                             .setParameter("orderId", orderId);

	        // Thực thi stored procedure
	        query.executeUpdate(); // Không sử dụng uniqueResult()

	        // Set isUpdate = 1 nếu thực thi thành công
	        isUpdate = 1;

	        t.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        t.rollback();
	    } finally {
	        session.close();
	    }

	    return isUpdate;
	}

	
	public List<OrdersEntity> autoCancelUnconfirmedOrders() {
	    List<OrdersEntity> listOrdersEntities = listOrders(); // Lấy danh sách tất cả đơn hàng
	    List<OrdersEntity> listOrdersEntitiesAutoCancel = new ArrayList<OrdersEntity>();
	    long currentTime = System.currentTimeMillis(); // Thời gian hiện tại tính bằng mili giây
	    for (OrdersEntity orderEntity : listOrdersEntities) {
	        Date orderDate = orderEntity.getCreatedAt(); // Thời gian đặt đơn hàng
	        long timeElapsed = currentTime - orderDate.getTime(); // Khoảng thời gian đã qua (ms)
	        if (timeElapsed >= 3 * 24 * 60 *  60 * 1000 && orderEntity.getOrderStatus().equals("Chờ xác nhận")) { 
	            //updateOrderStatusToCancel(orderEntity.getId()); 
	            updateOrderStatus(orderEntity.getId(), "Huỷ đơn hàng");
	            listOrdersEntitiesAutoCancel.add(orderEntity);
	        }
	    }
	    return listOrdersEntitiesAutoCancel;
	}

	public boolean deleteOrder(Long orderId) {
	    try {
	        // Lấy session hiện tại
	        Session session = sessionFactory.getCurrentSession();

	        // Lấy đơn hàng cần xóa
	        OrdersEntity order = (OrdersEntity) session.get(OrdersEntity.class, orderId);
	        if (order == null) {
	            throw new EntityNotFoundException("Không tìm thấy đơn hàng với ID: " + orderId);
	        }

	        // Kiểm tra và cập nhật tồn kho từ các chi tiết đơn hàng
	        List<OrdersDetailEntity> orderDetails = order.getOrderDetails();
	        if (orderDetails != null && !orderDetails.isEmpty()) {
	            for (OrdersDetailEntity orderDetail : orderDetails) {
	                InventoryEntity inventoryOfCurrentBook = inventoryDAO.getInventoryByBookId(orderDetail.getBook().getId());
	                if (inventoryOfCurrentBook != null) {
	                    Integer currentStockQuantity = inventoryOfCurrentBook.getStock_quantity();
	                    inventoryOfCurrentBook.setStock_quantity(currentStockQuantity + orderDetail.getQuantity());
	                    System.out.println("Cập nhật tồn kho: " + currentStockQuantity + " + " + orderDetail.getQuantity());
	                    boolean isUpdated = inventoryDAO.updateInventoryStock(inventoryOfCurrentBook);
	                    if (!isUpdated) {
	                        throw new IllegalStateException("Không thể cập nhật tồn kho cho sách ID: " + orderDetail.getBook().getId());
	                    }
	                } else {
	                    System.err.println("Không tìm thấy tồn kho cho sách ID: " + orderDetail.getBook().getId());
	                }
	            }
	        }

//	        // Xóa chi tiết đơn hàng
//	        if (orderDetails != null && !orderDetails.isEmpty()) {
//	            for (OrdersDetailEntity orderDetail : orderDetails) {
//	                session.delete(orderDetail);
//	            }
//	        }
//
//	        // Xóa giảm giá liên quan nếu có
//	        List<Order_DiscountsEntity> orderDiscounts = getOrderDiscountsByOrderId(orderId);
//	        if (orderDiscounts != null && !orderDiscounts.isEmpty()) {
//	            for (Order_DiscountsEntity orderDiscount : orderDiscounts) {
//	                session.delete(orderDiscount);
//	            }
//	        }

	        // Xóa đơn hàng
//	        session.delete(order);
	        String hql = "DELETE FROM OrdersEntity o WHERE o.id = :orderId";
	        Query query = session.createQuery(hql);
			query = query.setParameter("orderId", orderId);
			int orderDeleted = query.executeUpdate();
			if(orderDeleted != 0) {
				return true;
			}else {
				return false;
			}
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


}
