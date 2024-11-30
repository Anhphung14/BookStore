package bookstore.Service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.InventoryDAO;
import bookstore.Entity.InventoryEntity;

@Service
public class InventoryService {
	@Autowired
	InventoryDAO inventoryDAO;
	
	public List<InventoryEntity> getAllInventories() {
		return inventoryDAO.getAllInventories();
	}
	
	public int getQuantityById(Long id) {
		return inventoryDAO.getQuantityById(id);
	}
	
	public InventoryEntity getInventoryById(Long id) {
		return inventoryDAO.getInventoryById(id);
	}
	
	public InventoryEntity getInventoryByBookId (Long id) {
		return inventoryDAO.getInventoryByBookId(id);
	}
	
	public boolean updateQuantityByBookId(InventoryEntity inventory, Integer quantity) {
		return inventoryDAO.updateQuantityByBookId(inventory, quantity);
	}
	
	public boolean updateQuantityByInventoryId(Long id, int quantity, Date time) {
		return inventoryDAO.updateQuantityByInventoryId(id, quantity, time);
	}
	
	public boolean addQuantity(Long inventoryId, Long bookId, int bookQuantity, int inventoryStockQuantity) {
		return inventoryDAO.addQuantity(inventoryId, bookId, bookQuantity, inventoryStockQuantity);
	}

}
