package bookstore.Service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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
	
	public boolean saveInventory(InventoryEntity inventory) {
		return inventoryDAO.saveInventory(inventory);
	}
	
	public boolean handleErrors(ModelMap model, InventoryEntity inventory) {
		boolean hasError = false;
		
		if (inventory.getStock_quantity() == null) {
			model.addAttribute("errorstock_quantity", "This field must not be empty!");
			hasError = true;
		}
		
		return hasError;
	}
	
	public boolean checkUpdateStockQuantity(ModelMap model, InventoryEntity inventory, InventoryEntity inventoryGetById) {
		if (inventory.getStock_quantity() > inventoryGetById.getBook().getQuantity()) {
			model.addAttribute("errorstock_quantity", "Cannot update Stock quantity to be greater than Total quantity!");
			return false;
		}
		
		return true;
	}

}
