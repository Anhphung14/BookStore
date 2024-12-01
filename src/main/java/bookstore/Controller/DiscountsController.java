package bookstore.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		//System.out.println(getAllDiscounts);
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
	        @RequestParam("discountValue") Double discountValue, @RequestParam("applyTo") String applyTo,  
	        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate, 
	        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate, 
	        @RequestParam(value = "minOrderValue", required = false) Double minOrderValue, @RequestParam(value = "maxUses", required = false) Integer maxUses, 
	        @RequestParam("status") String status, @RequestParam("category") Long category_id, 
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
	    
	    if (discount == null) {
	        return "redirect:/discounts.htm"; // Chuyển hướng nếu không tìm thấy
	    }
	    
	    List<CategoriesEntity> listCategories = categoriesDAO.getAllCategories();
	    
	    
	    modelMap.addAttribute("discount", discount);
	    modelMap.addAttribute("listCategories", listCategories);
	    return "discounts/edit";
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
