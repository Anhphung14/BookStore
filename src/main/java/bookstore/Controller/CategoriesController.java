package bookstore.Controller;

import bookstore.Service.CategoriesService;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.SubcategoriesEntity;
import bookstore.Entity.UsersEntity;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Controller("mainCategoriesController")
@Transactional
public class CategoriesController {

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private SessionFactory factory;

	@RequestMapping("/categories")
	public String showCategories(Model model) {
		List<CategoriesEntity> categories = categoriesService.getAllCategoriesWithSubcategories();
		model.addAttribute("categories", categories);
		return "categories/index";
	}

	@RequestMapping(value = "/category/delete/{id}.htm", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, id);
		if(category != null) {
			session.delete(category);
		}

		return "redirect:/categories.htm";
	}

	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	public String categoryEdit(@PathVariable("id") Long id, ModelMap model) {
		CategoriesEntity category = getCategoryById(id);

		Hibernate.initialize(category.getSubcategoriesEntity());

		List<CategoriesEntity> categories = categoriesService.getAllCategoriesWithSubcategories();

		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		model.addAttribute("task", "edit");

		return "categories/edit";
	}

	@RequestMapping(value = "/category/new", method = RequestMethod.GET)
	public String newCategory(Model model) {
		model.addAttribute("category", new CategoriesEntity());
		model.addAttribute("task", "new");
		return "categories/edit";
	}

	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") CategoriesEntity category, @RequestParam("task") String task,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "subcategoryNames", required = false) String subcategoryNames,
			@RequestParam(value = "subcategoryIdsToDelete", required = false) String[] subcategoryIdsToDelete,
			@RequestParam(value = "subcategoryIdsToEdit", required = false) String[] subcategoryIdsToEdit,
			ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();

		try {
			if ("new".equals(task)) {
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
			} else if ("edit".equals(task)) {
				CategoriesEntity existingCategory = getCategoryById(id);
				existingCategory.setName(category.getName());
				existingCategory.setUpdated_at(new Date(System.currentTimeMillis()));

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

				if (subcategoryIdsToEdit != null && subcategoryIdsToEdit.length > 0) {
				    for (int i = 0; i < subcategoryIdsToEdit.length; i++) {
				        Long subcategoryId = Long.valueOf(subcategoryIdsToEdit[i]);

				        String newSubcategoryName = subcategoryIdsToEdit[i].trim();
				        if (!newSubcategoryName.isEmpty()) {
				            try {
				                SubcategoriesEntity subcategoryToEdit = (SubcategoriesEntity) session
				                        .get(SubcategoriesEntity.class, subcategoryId);
				                if (subcategoryToEdit != null) {
				                    subcategoryToEdit.setName(newSubcategoryName);
				                }
				            } catch (Exception e) {
				                e.printStackTrace();
				            }
				        }
				    }
				}

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


				session.merge(existingCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "An error occurred: " + e.getMessage());
			return "redirect:/categories.htm";
		}

		return "redirect:/categories.htm";
	}

	public CategoriesEntity getCategoryById(Long id) {
		Session session = factory.getCurrentSession();
		CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, id);

		return category;
	}

}