package bookstore.Controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.Entity.BooksEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Service.BooksService;
import bookstore.Service.InventoryService;

@Controller
@Transactional
public class InventoriesController {
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	BooksService booksService;
	
	@RequestMapping("/inventories")
	public String inventories(ModelMap model) {
		
		model.addAttribute("listInventories", inventoryService.getAllInventories());
		return "inventories/index";
	}
	
	@RequestMapping("/inventory/edit/{id}")
	public String productEdit(@PathVariable("id") Long id, ModelMap model) {
		
		model.addAttribute("task", "edit");
	
		InventoryEntity inventory = inventoryService.getInventoryById(id);
		
		model.addAttribute("inventory", inventory);
		return "inventories/edit";
	}
	
	@RequestMapping(value = "/inventory/edit", method = RequestMethod.POST)
	public String edit(ModelMap model, RedirectAttributes redirectAttributes, @ModelAttribute("inventory") InventoryEntity inventory) {
		if (inventoryService.updateQuantityByInventoryId(inventory.getId(), inventory.getStock_quantity(), new Date())) {
			redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated Inventory Id: " + inventory.getId());
			redirectAttributes.addFlashAttribute("alertType", "success");
		} else {
			model.addAttribute("alertMessage", "An error occurred while updating the BookId: " + inventory.getId());
			model.addAttribute("alertType", "error");
			return "inventories/edit";
		}
		return "redirect:/inventories.htm";
	}
	
	@RequestMapping(value = "/inventory/addQuantity", method = RequestMethod.POST)
	public String addQuantity(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam("stock_quantity") int stock_quantity, @RequestParam("bookId") Long bookId,
			@RequestParam("totalQuantity") int totalQuantity, @RequestParam("id") Long inventoryId,
			@RequestParam("addQuantity") int addQuantity) {
		
		InventoryEntity inventory = inventoryService.getInventoryById(inventoryId);
		BooksEntity book = booksService.getBookById(bookId);
		
		totalQuantity += addQuantity;
		stock_quantity += addQuantity;
		
		
		if (inventoryService.addQuantity(inventoryId, bookId, totalQuantity, stock_quantity)) {
			redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated Inventory Id: " + inventory.getId());
			redirectAttributes.addFlashAttribute("alertType", "success");
		} else {
			model.addAttribute("alertMessage", "An error occurred while updating the BookId: " + inventory.getId());
			model.addAttribute("alertType", "error");
		}
		
		return "redirect:/inventories.htm"; 
	}
}