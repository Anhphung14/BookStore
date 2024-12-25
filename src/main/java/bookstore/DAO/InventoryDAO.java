package bookstore.DAO;

import java.util.Date;
import java.util.List;


import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

import bookstore.Entity.InventoryEntity;

@Repository
@Transactional
public class InventoryDAO {
	@Autowired
	SessionFactory factory;
	
	public List<InventoryEntity> getAllInventories() {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM InventoryEntity";
		Query query = session.createQuery(hql);
		
		List<InventoryEntity> listInventories = query.list();
		return listInventories;
	}
	
	public int getQuantityById(Long id) {
		Session session = factory.getCurrentSession();
		InventoryEntity inventory = (InventoryEntity) session.get(InventoryEntity.class, id);
		
		Integer stockQuantity = inventory.getStock_quantity();
		
		return stockQuantity;
	}
	
	public InventoryEntity getInventoryById(Long id) {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM InventoryEntity WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		
		return (InventoryEntity) query.uniqueResult();
	}
	
	public InventoryEntity getInventoryByBookId(Long id) {
	    Session session = factory.getCurrentSession();

	    String hql = "FROM InventoryEntity i WHERE i.book.id = :bookId";
	    Query query = session.createQuery(hql);
	    query.setParameter("bookId", id);

	    InventoryEntity inventory = (InventoryEntity) query.uniqueResult();
	    
	    // Khởi tạo lazy object để tránh LazyInitializationException
	    if (inventory != null) {
	        Hibernate.initialize(inventory.getBook());
	    }

	    return inventory;
	}

	
	public boolean updateQuantityByBookId(InventoryEntity inventory, Integer quantity) {
		Session session = factory.getCurrentSession();
		
		if (inventory != null) {
			inventory.setStock_quantity(quantity);
			session.update(inventory);
//			System.out.println("Cap nhat inventory thanh cong");
			
			return true;
		} else {
			System.out.println("cap nhat inventory that bai roi!");
		}
		
		return false;
	}
	
	public boolean updateQuantityByInventoryId(Long id, int quantity, Date time) {
		Session session = factory.getCurrentSession();
		try {
			
			String hql = "UPDATE InventoryEntity SET stock_quantity = :stock_quantity, updated_at = :time WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.setParameter("stock_quantity", quantity);
			query.setParameter("time", time);
			
			int result = query.executeUpdate();

			return result > 0;
			
		} catch (Exception e) {
	        e.printStackTrace();
	        
	        return false;
		}
	}
	
	public boolean addQuantity(Long inventoryId, Long bookId, int bookQuantity, int inventoryStockQuantity) {
		Session session = factory.getCurrentSession();
		
		try {
			String hql1 = "UPDATE InventoryEntity SET stock_quantity = :stock_quantity, updated_at = :time WHERE id = :id";
			Query query1 = session.createQuery(hql1);
			query1.setParameter("id", inventoryId);
			query1.setParameter("stock_quantity", inventoryStockQuantity);
			query1.setParameter("time", new Date());		
			int result1 = query1.executeUpdate();
			
			String hql2 = "UPDATE BooksEntity SET quantity = :quantity, updated_at = :time WHERE id = :id";
			Query query2 = session.createQuery(hql2);
			query2.setParameter("id", bookId);
			query2.setParameter("quantity", bookQuantity);
			query2.setParameter("time", new Date());	
			int result2 = query2.executeUpdate();
			
			return result1 > 0 && result2 > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveInventory(InventoryEntity inventory) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(inventory);
			t.commit();
			
			return true;
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			
		} finally {
			session.close();
		}
		
		return false;
	}
	
	public boolean updateInventoryStock(InventoryEntity inventory) {
	    Session session = factory.getCurrentSession();
	    try {
	    	
	        // Chuỗi SQL để gọi stored procedure
	        String sql = "EXEC UpdateInventoryStock :bookId, :newStockQuantity";

	        // Tạo Query và thiết lập tham số
	        Query query = session.createSQLQuery(sql)
	                             .setParameter("bookId", inventory.getBook().getId())
	                             .setParameter("newStockQuantity", inventory.getStock_quantity());

	        // Thực thi stored procedure
	        query.executeUpdate();

	        return true; // Nếu không có lỗi, trả về true
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu có lỗi
	    }
	}
	
	public boolean updateInventoryStock2(InventoryEntity inventory, Integer newStockQuantity) {
	    Session session = factory.getCurrentSession();
	    try {
	    	
	        // Chuỗi SQL để gọi stored procedure
	        String sql = "EXEC UpdateInventoryStock :bookId, :newStockQuantity";

	        // Tạo Query và thiết lập tham số
	        Query query = session.createSQLQuery(sql)
	                             .setParameter("bookId", inventory.getBook().getId())
	                             .setParameter("newStockQuantity", newStockQuantity);

	        // Thực thi stored procedure
	        query.executeUpdate();

	        return true; // Nếu không có lỗi, trả về true
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu có lỗi
	    }
	}

	public boolean updateInventoryStock2(InventoryEntity inventory, Integer stock_quantity) {
	    Session session = factory.getCurrentSession();
	    try {
	    	
	        // Chuỗi SQL để gọi stored procedure
	        String sql = "EXEC UpdateInventoryStock :bookId, :newStockQuantity";

	        // Tạo Query và thiết lập tham số
	        Query query = session.createSQLQuery(sql)
	                             .setParameter("bookId", inventory.getBook().getId())
	                             .setParameter("newStockQuantity", stock_quantity);

	        // Thực thi stored procedure
	        query.executeUpdate();

	        return true; // Nếu không có lỗi, trả về true
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu có lỗi
	    }
	}
}
