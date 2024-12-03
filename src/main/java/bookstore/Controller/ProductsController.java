package bookstore.Controller;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.comparator.InvertibleComparator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import com.ckfinder.connector.plugins.SaveFileCommand;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import Mapper.BooksMapper;
import bookstore.DTO.BooksDTO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.SubcategoriesEntity;
import bookstore.Entity.SuppliersEntity;
import bookstore.Service.BooksService;
import bookstore.Service.CategoriesService;
import bookstore.Service.InventoryService;
import bookstore.Service.SubcategoriesService;
import bookstore.Service.SuppliersService;
import bookstore.Service.UploadService;

@Controller
@Transactional
public class ProductsController {
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	BooksService booksService;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	SuppliersService suppliersService;
	
	@Autowired
	CategoriesService categoriesService;
	
	@Autowired
	SubcategoriesService subcategoriesService;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	Cloudinary cloudinary;
	
	
	@RequestMapping("/products")
	public String products(ModelMap model, 
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "search", required = false) String search) {
	    
	    Session session = factory.getCurrentSession();
	    
	    String hql = "FROM BooksEntity c";
	    String countQuery = "SELECT count(c) FROM BooksEntity c";
	    
	    if (search != null && !search.isEmpty()) {
	        hql += " WHERE c.title LIKE :search";
	        countQuery += " WHERE c.title LIKE :search";
	    }
	    
	    Query countQ = session.createQuery(countQuery);
	    if (search != null && !search.isEmpty()) {
	        countQ.setParameter("search", "%" + search + "%");
	    }
	    Long count = (Long) countQ.uniqueResult();
	    
	    int totalPages = (int) Math.ceil((double) count / size);
	    
	    Query query = session.createQuery(hql);
	    if (search != null && !search.isEmpty()) {
	        query.setParameter("search", "%" + search + "%");
	    }
	    query.setFirstResult((page - 1) * size); 
	    query.setMaxResults(size); 
	    
	    List<BooksEntity> books = query.list();
	    
	    model.addAttribute("listBooks", books);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("search", search);
	    
	    return "products/index";
	}

	
	@RequestMapping("/product/edit/{id}")
	public String productEdit(@PathVariable("id") Long id, ModelMap model) {
		
		model.addAttribute("task", "edit");
	
		BooksEntity book = booksService.getBookById(id);
		
		model.addAttribute("book", book); 
		
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSubcategories", subcategoriesService.getAllSubcategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		return "products/edit";
	}
	
	@RequestMapping("/product/new")
	public String productNew(ModelMap model) {
		
		model.addAttribute("task", "new");
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		model.addAttribute("listSubcategories", subcategoriesService.getAllSubcategories());
		
		return "products/new";
	}
	
	@RequestMapping(value = "/product/edit", method = RequestMethod.POST)
	public String productEdit (ModelMap model, RedirectAttributes redirectAttributes, @ModelAttribute BooksDTO bookDTO) {

		BooksMapper booksMapper = new BooksMapper();
		BooksEntity bookEntity = booksMapper.DTOtoEntity(bookDTO);
		BooksEntity bookGetById = booksService.getBookById(bookEntity.getId());
		SubcategoriesEntity subcategory = subcategoriesService.getSubcategoryBySubcategoryId(bookDTO.getSubcategory_id());
		SuppliersEntity supplier = suppliersService.getSupplierBySupplierId(bookDTO.getSupplier_id());
		
		bookEntity.setSubcategoriesEntity(subcategory);
		bookEntity.setSupplier(supplier);
		
		booksService.editThumbnail_Images(bookDTO, bookEntity, bookGetById);

		if (!booksService.handleBookErrors(model, bookEntity)) {
			
			if (booksService.checkUpdateQuantity(model, bookGetById, bookEntity)) {
				boolean result = booksService.updateBook(bookEntity);
				
				if (result) {
					redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated BookID: " + bookEntity.getId());
					redirectAttributes.addFlashAttribute("alertType", "success");
	
					return "redirect:/products.htm"; 
				}
			}
		}
		
		model.addAttribute("alertMessage", "An error occurred while updating the BookId: " + bookEntity.getId());
		model.addAttribute("alertType", "error");
		
		model.addAttribute("book", bookEntity);
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		model.addAttribute("listSubcategories", subcategoriesService.getAllSubcategories());

		return "products/edit";
	}
	
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String productAdd (ModelMap model, RedirectAttributes redirectAttributes,
			@ModelAttribute BooksDTO bookDTO, @RequestParam("subcategory_id") Long subcategory_id) {

		BooksMapper bookMapper = new BooksMapper();
		BooksEntity bookEntity = bookMapper.DTOtoEntity(bookDTO);
		
		SubcategoriesEntity subcategory = subcategoriesService.getSubcategoryBySubcategoryId(bookDTO.getSubcategory_id());
		SuppliersEntity supplier = suppliersService.getSupplierBySupplierId(bookDTO.getSupplier_id());
			
		bookEntity.setSubcategoriesEntity(subcategory);
		bookEntity.setSupplier(supplier);
		bookEntity.setCreatedAt(new Date());
		bookEntity.setUpdatedAt(new Date());
		
		booksService.addThumbnail_Images(bookDTO, bookEntity);
		
		if (!booksService.handleBookErrors(model, bookEntity)) {
			InventoryEntity inventory = new InventoryEntity(bookEntity, bookEntity.getQuantity(), new Date(), new Date());
			
			boolean result1 = booksService.saveBook(bookEntity);
			boolean result2 = inventoryService.saveInventory(inventory);
			
			if (result1 && result2 ) {
				redirectAttributes.addFlashAttribute("alertMessage", "Successfully added a new book!");
				redirectAttributes.addFlashAttribute("alertType", "success");
				
				return "redirect:/products.htm";
			}
		}
		
		model.addAttribute("alertMessage", "An error occurred while adding the new book!");
		model.addAttribute("alertType", "error");
		
		model.addAttribute("book", bookEntity);
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		model.addAttribute("listSubcategories", subcategoriesService.getAllSubcategories());
		
		return "products/new";
	}
	
	@RequestMapping(value = "/product/delete", method = RequestMethod.POST)
    public String deleteBook(@RequestParam("bookId") Long bookId, RedirectAttributes redirectAttributes, ModelMap model) {
        
        if (booksService.deleteBookById2(bookId)) {	
	        redirectAttributes.addFlashAttribute("alertMessage", "Sách đã được xóa thành công!");
	        redirectAttributes.addFlashAttribute("alertType", "success");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sách. Vui lòng thử lại!");
            model.addAttribute("alertType", "error");
        }
        
        return "redirect:/products.htm";
    }
	
	@RequestMapping(value = "/product/changeStatus", method = RequestMethod.POST)
	public String changeStatus(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam("bookId") Long bookId, @RequestParam("newStatus") int newStatus) {
		
		if (booksService.changeStatus(bookId, newStatus)) {
			redirectAttributes.addFlashAttribute("alertMessage", "The book's status has been successfully updated!!");
	        redirectAttributes.addFlashAttribute("alertType", "success");
		} else {
			redirectAttributes.addFlashAttribute("alertMessage", "The book's status update has failed!!");
	        redirectAttributes.addFlashAttribute("alertType", "error");
		}
		
		return "redirect:/products.htm";
	}
	
	
	@RequestMapping(value = "/product/getSubcategories", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String getSubcategories(@RequestParam("categoryId") Long categoryId) {
	    List<SubcategoriesEntity> subcategories = subcategoriesService.getSubcategoriesByCategoryId(categoryId);
	    StringBuilder html = new StringBuilder();
	    html.append("<option value=\"\" disabled selected>Select an option</option>");
	    for (SubcategoriesEntity subcategory : subcategories) {
	        html.append("<option value=\"").append(subcategory.getId()).append("\">")
	            .append(subcategory.getName())
	            .append("</option>");
	    }
	    return html.toString();
	}
	
	@RequestMapping(value = "/product/getCategory", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String getCategory(@RequestParam("subcategoryId") Long subcategoryId) {
	    try {
	        CategoriesEntity category = subcategoriesService.getCategoryBySubcategoryId(subcategoryId);
	        if (category != null) {
	            return "<span id='categoryId'>" + category.getId() + "</span>";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "<span id='categoryId'>No category found</span>";
	}

}