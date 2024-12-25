package bookstore.Controller;

import bookstore.Service.CategoriesService;
import bookstore.Utils.EscapeHtmlUtil;
import bookstore.DAO.CartDAO;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.SubcategoriesEntity;
import bookstore.Entity.UsersEntity;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller("mainCategoriesController")
@Transactional
@RequestMapping("/admin1337")
public class CategoriesController {

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private SessionFactory factory;
	
	@Autowired
	private CartDAO cartDAO;
	
	@PreAuthorize("hasAuthority('VIEW_CATEGORY')")
	@RequestMapping("/categories")
	public String showCategories(
	        Model model,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "search", required = false) String search, HttpSession httpSession) {
	    
	    Session session = factory.getCurrentSession();
	    // Xây dựng câu lệnh HQL động dựa trên tham số tìm kiếm
	    String hql = "FROM CategoriesEntity c";
	    String countQuery = "SELECT count(c) FROM CategoriesEntity c";
	    
	    if (search != null && !search.isEmpty()) {
	        hql += " WHERE c.name LIKE :search";
	        countQuery += " WHERE c.name LIKE :search";
	    }
	    
	    // Tính tổng số bản ghi
	    Query countQ = session.createQuery(countQuery);
	    if (search != null && !search.isEmpty()) {
	        countQ.setParameter("search", "%" + search + "%");
	    }
	    Long count = (Long) countQ.uniqueResult();
	    int totalPages = (int) Math.ceil((double) count / size);
	    
	    // Lấy danh sách theo trang
	    Query query = session.createQuery(hql);
	    if (search != null && !search.isEmpty()) {
	        query.setParameter("search", "%" + search + "%");
	    }
	    query.setFirstResult((page - 1) * size);
	    query.setMaxResults(size);
	    
	    List<CategoriesEntity> categories = query.list();
	    
	    model.addAttribute("categories", categories);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("search", search); 
	    
	    return "categories/index";
	}


	@RequestMapping(value = "/category/delete/{id}.htm", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		
		CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, id);
		if (category != null) {
			session.delete(category);
		}

		return "redirect:/admin1337/categories.htm";
	}

	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	public String categoryEdit(@PathVariable("id") Long id, ModelMap model) {
		CategoriesEntity category = getCategoryById(id);

		Hibernate.initialize(((CategoriesEntity) category).getSubcategoriesEntity());

		List<CategoriesEntity> categories = categoriesService.getAllCategoriesWithSubcategories();

		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		model.addAttribute("task", "edit");

		return "categories/edit";
	}
	
	@PreAuthorize("hasAuthority('ADD_CATEGORY')")
	@RequestMapping(value = "/category/new", method = RequestMethod.GET)
	public String newCategory(Model model) {
		model.addAttribute("category", new CategoriesEntity());
		model.addAttribute("task", "new");
		return "categories/edit";
	}
	
	@PreAuthorize("hasAuthority('UPDATE_CATEGORY') or hasAuthority('ADD_CATEGORY')")
	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") CategoriesEntity category, @RequestParam("task") String task,
	                           @RequestParam(value = "id", required = false) Long id,
	                           @RequestParam(value = "subcategoryNames", required = false) String subcategoryNames,
	                           @RequestParam(value = "subcategoryIdsToDelete", required = false) String[] subcategoryIdsToDelete,
	                           ModelMap model, HttpServletRequest request,
	                           RedirectAttributes redirectAttributes) {

	    Session session = factory.getCurrentSession();
	    category.setName(EscapeHtmlUtil.encodeHtml(category.getName()));
	    subcategoryNames = EscapeHtmlUtil.encodeHtml(subcategoryNames);
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean hasUpdateAuthority = auth.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("UPDATE_CATEGORY"));
	    boolean hasAddAuthority = auth.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADD_CATEGORY"));
	    
	    try {
	        if ("new".equals(task) && hasAddAuthority) {
	            // Kiểm tra tên category trùng
	            String hql = "FROM CategoriesEntity WHERE name = :name";
	            Query query = session.createQuery(hql);
	            query.setParameter("name", category.getName().trim());
	            List<CategoriesEntity> existingCategories = query.list();

	            if (!existingCategories.isEmpty()) {
	                redirectAttributes.addFlashAttribute("alertMessage", "Category name already exists.");
	                redirectAttributes.addFlashAttribute("alertType", "error");
	                return "redirect:/admin1337/categories.htm";
	            }

	            // Tạo mới category
	            LocalDateTime now = LocalDateTime.now();
	            Timestamp currentDate = Timestamp.valueOf(now);
	            category.setCreated_at(currentDate);
	            category.setUpdated_at(currentDate);

	            if (subcategoryNames != null && !subcategoryNames.isEmpty()) {
	                List<SubcategoriesEntity> subcategories = new ArrayList<>();
	                String[] subcategoryArray = subcategoryNames.split(",");
	                for (String subcategoryName : subcategoryArray) {
	                    SubcategoriesEntity subcategory = new SubcategoriesEntity();
	                    subcategory.setName(subcategoryName.trim());
	                    subcategory.setCategoriesEntity(category);
	                    subcategories.add(subcategory);
	                }
	                category.setSubcategoriesEntity(subcategories);
	            }

	            session.save(category);
	            redirectAttributes.addFlashAttribute("alertMessage", "Category saved successfully!");
	            redirectAttributes.addFlashAttribute("alertType", "success");

	        } else if ("edit".equals(task) && hasUpdateAuthority) {
	            // Chỉnh sửa category hiện có
	            CategoriesEntity existingCategory = getCategoryById(id);
	            existingCategory.setName(category.getName());
	            existingCategory.setUpdated_at(new Date(System.currentTimeMillis()));

	            // Xử lý xóa subcategory
	            if (subcategoryIdsToDelete != null && subcategoryIdsToDelete.length > 0) {
	                for (String subcategoryIdToDelete : subcategoryIdsToDelete) {
	                    Long subcategoryId = Long.valueOf(subcategoryIdToDelete);
	                    SubcategoriesEntity subcategoryToDelete = (SubcategoriesEntity) session.get(SubcategoriesEntity.class, subcategoryId);
	                    if (subcategoryToDelete != null) {
	                        existingCategory.getSubcategoriesEntity().remove(subcategoryToDelete);
	                        session.delete(subcategoryToDelete);
	                    }
	                }
	            }

	            // Cập nhật subcategory
	            if (subcategoryNames != null && !subcategoryNames.isEmpty()) {
	                List<SubcategoriesEntity> subcategories = new ArrayList<>();
	                String[] subcategoryArray = subcategoryNames.split(",");
	                for (String subcategoryName : subcategoryArray) {
	                    SubcategoriesEntity subcategory = new SubcategoriesEntity();
	                    subcategory.setName(subcategoryName.trim());
	                    subcategory.setCategoriesEntity(category);
	                    subcategories.add(subcategory);
	                }
	                existingCategory.setSubcategoriesEntity(subcategories);
	            }

	            session.merge(existingCategory);
	            redirectAttributes.addFlashAttribute("alertMessage", "Category updated successfully!");
	            redirectAttributes.addFlashAttribute("alertType", "success");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("alertMessage", "Error occurred while saving the category.");
	        redirectAttributes.addFlashAttribute("alertType", "error");
	        return "redirect:/admin1337/categories.htm";
	    }

	    return "redirect:/admin1337/categories.htm";
	}

	@RequestMapping(value = "/category/saveSubcategory", method = RequestMethod.POST)
	public String saveSubcategory(@RequestParam("subcategoryId") Long subcategoryId, @RequestParam("name") String name,
	                              ModelMap model, HttpServletRequest request) {
	    Session session = factory.getCurrentSession();
	    name = EscapeHtmlUtil.encodeHtml(name);
	    try {
	        // Chuẩn hóa tên subcategory: loại bỏ khoảng trắng thừa và chuyển về chữ thường
	        String normalizedSubcategoryName = name.trim().toLowerCase().replaceAll("\\s+", " ");
	        
	        // Kiểm tra trùng lặp tên Subcategory không phân biệt chữ hoa, chữ thường và khoảng trắng
	        String hql = "FROM SubcategoriesEntity WHERE LOWER(TRIM(REGEXP_REPLACE(name, '\\s+', ' '))) = :name AND id != :subcategoryId";
	        Query query = session.createQuery(hql);
	        query.setParameter("name", normalizedSubcategoryName);
	        query.setParameter("subcategoryId", subcategoryId);
	        List<SubcategoriesEntity> existingSubcategories = query.list();

	        if (!existingSubcategories.isEmpty()) {
	            model.addAttribute("message", "Subcategory name already exists (case-insensitive, ignoring spaces).");
	            return "redirect:/admin1337/categories.htm";
	        }

	        // Tiến hành cập nhật nếu không trùng
	        SubcategoriesEntity subcategory = (SubcategoriesEntity) session.get(SubcategoriesEntity.class, subcategoryId);

	        if (subcategory != null) {
	            subcategory.setName(name.trim());
	            session.update(subcategory);
	        } else {
	            model.addAttribute("message", "Subcategory not found.");
	            return "redirect:/admin1337/categories.htm";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "An error occurred: " + e.getMessage());
	        return "redirect:/admin1337/categories.htm";
	    }

	    String referer = request.getHeader("Referer");
	    return "redirect:" + referer;
	}




	public CategoriesEntity getCategoryById(Long id) {
		Session session = factory.getCurrentSession();
		CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, id);

		return category;
	}

}
