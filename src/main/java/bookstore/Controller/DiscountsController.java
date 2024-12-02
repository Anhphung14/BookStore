package bookstore.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.DiscountsDAO;
import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.DiscountsEntity;
import bookstore.Entity.SubcategoriesEntity;

@Controller
public class DiscountsController {
	@Autowired
	DiscountsDAO discountsDAO;
	@Autowired
	CategoriesDAO categoriesDAO;
	@Autowired
	SubcategoriesDAO subCategoriesDAO;
	
	@RequestMapping("/discounts")
	public String index(ModelMap modelMap) {
		
		/* Cập nhật trạng thái mã giảm giá */
		discountsDAO.updateStatusDiscounts();

		List<DiscountsEntity> getAllDiscounts = discountsDAO.getAllDiscounts();
		for (DiscountsEntity discount : getAllDiscounts) {
	        // Gọi hàm getUsedCountByDiscountId để lấy số lần sử dụng của discount
	        int usedCount = discountsDAO.getUsedCountByDiscountId(discount.getId());
	        discount.setUsed(usedCount);
	        if (discount.getApplyTo().equals("categories")) {
	            // Gọi hàm getNameCategoryOfDiscount để lấy Map các Category và Subcategory
	            Map<String, List<String>> categorySubcategoryMap = discountsDAO.getNameCategoryOfDiscount(discount.getId());

	            // Lấy danh sách Category (vì Map có khóa là Category)
	            List<String> categories = new ArrayList<>(categorySubcategoryMap.keySet());

	            // Lấy danh sách Subcategories (lấy giá trị trong Map)
	            List<String> subcategories = new ArrayList<>();
	            if (!categories.isEmpty()) {
	                // Lấy Subcategory từ danh sách các categories (tất cả Subcategory của Category đầu tiên)
	                subcategories = categorySubcategoryMap.get(categories.get(0)); 
	            }

	            // Cập nhật thông tin cho discount
	            discount.setCategoryName(categories.isEmpty() ? "" : categories.get(0)); // Lấy tên category đầu tiên
	            discount.setSubcategoriesName(subcategories); // Cập nhật danh sách Subcategory

	            // Nếu cần, có thể sử dụng thêm categories/subcategories cho việc hiển thị trên giao diện
	        }
	      
	    }
		modelMap.addAttribute("listDiscounts", getAllDiscounts);
		return "discounts/index";
	}
	
	@RequestMapping("/discount/create")
	public String createDiscount(ModelMap modelMap) {
		List<CategoriesEntity> listCategories = categoriesDAO.getAllCategories();
		modelMap.addAttribute("listCategories", listCategories);
		return "discounts/new";
	}
	
	@RequestMapping(value = "/discount/create", method = RequestMethod.POST)
	public String createDiscountPost(@RequestParam("code") String code, @RequestParam("discountType") String discountType, 
	        @RequestParam("discountValue") Long discountValue, @RequestParam("applyTo") String applyTo,  
	        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate, 
	        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate, 
	        @RequestParam(value = "minOrderValue", required = false) Long minOrderValue, @RequestParam(value = "maxUses", required = false) Integer maxUses, 
	        @RequestParam("status") String status, @RequestParam(value = "category", required = false) Long category_id, 
	        @RequestParam(value = "subcategory[]", required = false) List<Long> subcategories_id, RedirectAttributes redirectAttributes) {
		
	    DiscountsEntity newDiscount = new DiscountsEntity();
	    newDiscount.setCode(code);
	    newDiscount.setDiscountType(discountType);
	    newDiscount.setDiscountValue(discountValue);
	    newDiscount.setApplyTo(applyTo);
	    newDiscount.setStartDate(startDate);
	    newDiscount.setEndDate(endDate);
	    newDiscount.setMinOrderValue(minOrderValue);
	    newDiscount.setMaxUses(maxUses);
	    newDiscount.setStatus(status);
	    Date currentTimestamp = new Date();
	    newDiscount.setCreatedAt(currentTimestamp); 
	    newDiscount.setUpdatedAt(currentTimestamp);
	    
	    // Kiểm tra xem mã code đã tồn tại chưa
	    if (!discountsDAO.checkDiscountCodeExist(code)) {
	        String result = discountsDAO.createNewDiscount(newDiscount, subcategories_id);
	        if ("success".equals(result)) {
	            redirectAttributes.addFlashAttribute("alertMessage", "Successfully added a new Discount!");
	            redirectAttributes.addFlashAttribute("alertType", "success");
	            return "redirect:/discounts.htm";
	        } else {
	            redirectAttributes.addFlashAttribute("alertMessage", result); // Trả về lỗi từ DAO
	            redirectAttributes.addFlashAttribute("alertType", "error");
	            return "redirect:/discounts.htm";
	        }
	    } else {
	        redirectAttributes.addFlashAttribute("alertMessage", "Code discount is exist!");
	        redirectAttributes.addFlashAttribute("alertType", "error");
	        return "redirect:/discounts.htm";
	    }
	}

	
	@RequestMapping("/discount/edit/{discount_id}")
	public String editDiscount(ModelMap modelMap, @PathVariable(value = "discount_id") Long discount_id) {
		modelMap.addAttribute("task", "edit");
	    DiscountsEntity discount = discountsDAO.findDiscountById(discount_id);
	    if(discount.getApplyTo().equals("categories")) {
    		List<Long> idCategoryAndSubcategory = discountsDAO.foundCategoryOfDiscount(discount_id);
	    	Long category = idCategoryAndSubcategory.get(0);
 	    	List<Long> subcategories = idCategoryAndSubcategory.subList(1, idCategoryAndSubcategory.size());
 	    	List<CategoriesEntity> listCategories = categoriesDAO.getAllCategories();
 		    List<SubcategoriesEntity> listSubCategories = subCategoriesDAO.getSubcategoryByCategoryId(category);

 		    modelMap.addAttribute("category_id", category);
 		    modelMap.addAttribute("subcategories_id", subcategories);
 		    modelMap.addAttribute("listCategories", listCategories);
 		    modelMap.addAttribute("listSubCategories", listSubCategories);
	    }
	   
	    modelMap.addAttribute("discount", discount);
	    return "discounts/edit";
	}
	
	@RequestMapping(value = "/discount/edit", method = RequestMethod.POST)
	public String editDiscountPost(@RequestParam("discount_id") Long discount_id, 
	                               @RequestParam("code") String code, 
	                               @RequestParam("discountType") String discountType, 
	                               @RequestParam("discountValue") Long discountValue, 
	                               @RequestParam("applyTo") String applyTo,  
	                               @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate, 
	                               @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate, 
	                               @RequestParam(value = "minOrderValue", required = false) Long minOrderValue, 
	                               @RequestParam(value = "maxUses", required = false) Integer maxUses, 
	                               @RequestParam("status") String status, 
	                               @RequestParam("category") Long category_id, 
	                               @RequestParam(value = "subcategory[]", required = false) List<Long> subcategories_id, 
	                               RedirectAttributes redirectAttributes) {

	    // Tìm kiếm discount theo ID
	    DiscountsEntity existingDiscount = discountsDAO.findDiscountById(discount_id);
	    if (existingDiscount == null) {
	        // Nếu không tìm thấy discount, chuyển hướng và thông báo lỗi
	        redirectAttributes.addFlashAttribute("error", "Discount not found!");
	        return "redirect:/discounts.htm";
	    }


	    // Cập nhật thông tin discount
	    existingDiscount.setCode(code);
	    existingDiscount.setDiscountType(discountType);
	    existingDiscount.setDiscountValue(discountValue);
	    existingDiscount.setApplyTo(applyTo);
	    existingDiscount.setStartDate(startDate);
	    existingDiscount.setEndDate(endDate);
	    existingDiscount.setMinOrderValue(minOrderValue);
	    existingDiscount.setMaxUses(maxUses);
	    existingDiscount.setStatus(status);

	    existingDiscount.setUpdatedAt(new Date());
	    
	    // Lưu discount đã chỉnh sửa
	    discountsDAO.updateDiscount(discount_id, existingDiscount, subcategories_id);

	    // Thêm thông báo thành công và chuyển hướng đến trang danh sách giảm giá
	    redirectAttributes.addFlashAttribute("alertMessage", "Discount updated successfully!");
	    redirectAttributes.addFlashAttribute("alertType", "success");
	    return "redirect:/discounts.htm";
	}

	 @RequestMapping(value = "/discount/delete", method = RequestMethod.POST)
	    public String deleteDiscount(@RequestParam("discount_id") Long discount_id, RedirectAttributes redirectAttributes) {
	        //System.out.println("discount_id: " + discount_id);
	        // Gọi phương thức xóa discount từ DAO
		 	DiscountsEntity existingDiscount = discountsDAO.findDiscountById(discount_id);
		 	if(existingDiscount.getStatus().equals("expired")) {
		 		boolean isDeleted = discountsDAO.deleteDiscount(existingDiscount);
		 		if (isDeleted) {
		            // Nếu xóa thành công
		            redirectAttributes.addFlashAttribute("alertMessage", "Discount has been successfully deleted!");
		            redirectAttributes.addFlashAttribute("alertType", "success");
		        } else {
		            // Nếu xóa thất bại (có thể vì discount không tồn tại)
		            redirectAttributes.addFlashAttribute("alertMessage", "Discount not found or couldn't be deleted!");
		            redirectAttributes.addFlashAttribute("alertType", "error");
		        }
		 	}else {
		 		redirectAttributes.addFlashAttribute("alertMessage", "Discount must expired");
	            redirectAttributes.addFlashAttribute("alertType", "error");
		 	}	        
	        
	        // Chuyển hướng về danh sách discounts sau khi xóa
	        return "redirect:/discounts.htm";
	    }
	
	
	@RequestMapping(value = "/discount/getSubcategories", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String fetchGetSubcategories(@RequestParam("categoryId") Long categoryId) {
	    // Lấy danh sách các subcategories từ service hoặc DAO
	    List<SubcategoriesEntity> listSubCategories = subCategoriesDAO.getSubcategoryByCategoryId(categoryId);

	    // Tạo StringBuilder để xây dựng chuỗi HTML trả về
	    StringBuilder html = new StringBuilder();
	    
	    // Thêm option mặc định
	    //html.append("<option value=\"\" disabled selected>Select an option</option>");
	    
	    // Lặp qua các subcategories và thêm vào các option
	    for (SubcategoriesEntity subcategory : listSubCategories) {
	        html.append("<option value=\"")
	            .append(subcategory.getId())
	            .append("\">")
	            .append(subcategory.getName())
	            .append("</option>");
	    }

	    // Trả về chuỗi HTML
	    return html.toString();
	}

}