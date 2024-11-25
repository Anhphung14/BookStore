package bookstore.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.InventoryEntity;

@Repository
public class InventoryDAO {
	@Autowired
	SessionFactory factory;
	
	public int getQuantityById(Long id) {
		Session session = factory.getCurrentSession();
		InventoryEntity inventory = (InventoryEntity) session.get(InventoryEntity.class, id);
		
		Integer stockQuantity = inventory.getQuantity();
		
		return stockQuantity;
	}
	
	public InventoryEntity getInventoryByBookId (Long id) {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM InventoryEntity i WHERE i.book.id = :bookId";
		Query query = session.createQuery(hql);
		query.setParameter("bookId", id);
		
		return (InventoryEntity) query.uniqueResult();
	}
	
	public boolean updateQuantityByBookId(InventoryEntity inventory, Integer quantity) {
		Session session = factory.getCurrentSession();
		
		if (inventory != null) {
			inventory.setQuantity(quantity);
			session.update(inventory);
//			System.out.println("Cap nhat inventory thanh cong");
			
			return true;
		} else {
			System.out.println("cap nhat inventory that bai roi!");
		}
		
		return false;
	}
}
