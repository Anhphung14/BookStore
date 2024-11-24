package bookstore.Service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.InventoryDAO;
import bookstore.Entity.InventoryEntity;

@Service
public class InventoryService {
	@Autowired
	InventoryDAO inventoryDAO;
	
	public int getQuantityById(Long id) {
		return inventoryDAO.getQuantityById(id);
	}
	
	public InventoryEntity getInventoryByBookId (Long id) {
		return inventoryDAO.getInventoryByBookId(id);
	}
	
	public boolean updateQuantityByBookId(InventoryEntity inventory, Integer quantity) {
		return inventoryDAO.updateQuantityByBookId(inventory, quantity);
	} 
}
