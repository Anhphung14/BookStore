package bookstore.Controller;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
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

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.comparator.InvertibleComparator;
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
	public String products(ModelMap model, HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
		
		List<BooksEntity> listBooks = booksService.getAllBooks();
//        model.addAttribute("listBooks", listBooks);
		List<Long> ids = new ArrayList<Long>();
		for (BooksEntity book : listBooks) {
			ids.add(book.getId());
		}
		
		List<Object[]> booksWithQuantities = booksService.getBooksWithQuantitiesByIds(ids);
		List<BooksEntity> books = new ArrayList<>(); 
		List<Integer> quantities = new ArrayList<>(); 
		for (Object[] result : booksWithQuantities) { 
			books.add((BooksEntity) result[0]); 
			quantities.add((Integer) result[1]); 
		} 
		
		model.addAttribute("listBooks", books); 
		model.addAttribute("quantities", quantities);
		
		return "products/index";
		
	}
	
	@RequestMapping("/product/edit/{id}")
	public String productEdit(@PathVariable("id") Long id, ModelMap model) {
		
		model.addAttribute("task", "edit");

		Object[] bookWithQuantity = booksService.getBookWithQuantityById(id);
		
		BooksEntity book = (BooksEntity) bookWithQuantity[0];
		
		Integer quantity = (Integer) bookWithQuantity[1]; 
		model.addAttribute("book", book); 
		model.addAttribute("quantity", quantity);
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
	public String productEdit (ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("price") Double price, @RequestParam("description") String description,
			@RequestParam("category") Long categoryId, @RequestParam("subcategory") Long subcategoryId, @RequestParam("supplier") Long supplierId,
			@RequestParam("quantity") int quantity, @RequestParam("publication_year") int publication_year, 
			@RequestParam("page_count") int page_count, @RequestParam("language") String language, @RequestParam("status") int status,
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail, @RequestParam(value = "images", required = false) MultipartFile[] images) {
		
		Session session = factory.getCurrentSession();
		
		try {
			BooksEntity selectedBook = booksService.getBookById(id);
			
			if (selectedBook == null) {
				return "redirect:/product/new.htm";
			}
			
			
			CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
			SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
			SubcategoriesEntity subcategory = (SubcategoriesEntity) session.get(SubcategoriesEntity.class, subcategoryId);
			InventoryEntity inventory = inventoryService.getInventoryByBookId(id);
			
			selectedBook.setTitle(title);
			selectedBook.setAuthor(author);
			selectedBook.setPrice(price);
			selectedBook.setDescription(description);
			selectedBook.setPublication_year(publication_year);
			selectedBook.setPage_count(page_count);
			selectedBook.setLanguage(language);
			selectedBook.setStatus(status);
			selectedBook.setUpdatedAt(new Date());
			
			selectedBook.setSubcategoriesEntity(subcategory);
			selectedBook.setSupplier(supplier);
			
			try {
				if (!thumbnail.isEmpty()) {
//					String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
					String thumbnailPath = uploadService.uploadByCloudinary(thumbnail, "images/thumbnails/" + uploadService.toSlug(title));
					selectedBook.setThumbnail(thumbnailPath);
					
//					File savedFile = new File(context.getRealPath("/" + thumbnailPath));
//					while (!savedFile.exists()) {
//						Thread.sleep(100);
//					}
				}
				
				if (images != null && images.length > 0) {
//					String imagesPath = "resources/images/books/" + toSlug(title) + "/";
					StringBuilder imagePaths = new StringBuilder();
					
					for (MultipartFile image : images) {
						if (!image.isEmpty()) {
//							String imagePath = saveImage(image, imagesPath);
							String imagePath = uploadService.uploadByCloudinary(image, "images/books/" + uploadService.toSlug(title));
							imagePaths.append(imagePath).append(";");
						}
					}
					
					if (imagePaths.length() > 0) {
						selectedBook.setImages(imagePaths.toString());							
					} else {
						selectedBook.setImages(selectedBook.getImages());
					}
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				boolean is_updateQuantity = inventoryService.updateQuantityByBookId(inventory, quantity);
				session.update(selectedBook);
				
				if (is_updateQuantity == true) {
					
//				model.addAttribute("alertMessage", "Successfully updated BookID: " + id);
//		        model.addAttribute("alertType", "success");
					
					redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated BookID: " + id);
					redirectAttributes.addFlashAttribute("alertType", "success");
				} else {
					model.addAttribute("alertMessage", "An error occurred while updating the BookId: " + id);
					model.addAttribute("alertType", "error");
					
					return "products/edit";
				}
				
			} catch (Exception e) {
				System.err.println("An error occurred while updating the book: " + e.getMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/products.htm"; 
		
	}
	
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String productAdd (ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("price") Double price, @RequestParam("description") String description,
			@RequestParam("category") Long categoryId, @RequestParam("subcategory") Long subcategoryId, @RequestParam("supplier") Long supplierId,
			@RequestParam("quantity") int quantity, @RequestParam("publication_year") int publication_year, 
			@RequestParam("page_count") int page_count, @RequestParam("language") String language, @RequestParam("status") int status,
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail, @RequestParam(value = "images", required = false) MultipartFile[] images) {
		System.out.println("title: " + title);
		System.out.println("author: " + author);
		System.out.println("price: " + price);
		System.out.println("description: " + description);
		System.out.println("category: " + categoryId);
		System.out.println("supplier: " + supplierId);
		System.out.println("quantity: " + quantity);
		System.out.println("publication_year: " + publication_year);
		System.out.println("page_count: " + page_count);
		
		Session session = factory.getCurrentSession();
		
		try {
			BooksEntity newBook = new BooksEntity();
			
			newBook.setTitle(title);
			newBook.setAuthor(author);
			newBook.setPrice(price);
			newBook.setDescription(description);
			newBook.setStock_quantity(quantity);
			newBook.setPublication_year(publication_year);
			newBook.setLanguage(language);
			newBook.setPage_count(page_count);
			newBook.setStatus(status);
			newBook.setCreatedAt(new Date());
			newBook.setUpdatedAt(new Date());
			
			CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
			SubcategoriesEntity subcategory = (SubcategoriesEntity) session.get(SubcategoriesEntity.class, subcategoryId);
			SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
			
			newBook.setSubcategoriesEntity(subcategory);
			newBook.setSupplier(supplier);
			
			try {
				if (!thumbnail.isEmpty()) {
					String thumbnailPath = uploadService.uploadByCloudinary(thumbnail, "images/thumbnails/" + uploadService.toSlug(title));
//					String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
					newBook.setThumbnail(thumbnailPath);
					
//					File savedFile = new File(context.getRealPath("/" + thumbnailPath));
//					while (!savedFile.exists()) {
//						Thread.sleep(100);
//					}
				}
				
				if (images != null && images.length > 0) {
//					String imagesPath = "resources/images/books/" + toSlug(title) + "/";
					StringBuilder imagePaths = new StringBuilder();
					
					for (MultipartFile image : images) {
						if (!image.isEmpty()) {
//							String imagePath = saveImage(image, imagesPath);
							String imagePath = uploadService.uploadByCloudinary(image, "images/books/" + uploadService.toSlug(title));
							imagePaths.append(imagePath).append(";");
						}
					}
					
					newBook.setImages(imagePaths.toString());
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			session.save(newBook);
			
			InventoryEntity inventory = new InventoryEntity();
			inventory.setBook(newBook);
			inventory.setQuantity(quantity);
			inventory.setCreated_at(new Date());
			inventory.setUpdated_at(new Date());
			
			session.save(inventory);
			
			redirectAttributes.addFlashAttribute("alertMessage", "Successfully added a new book!");
			redirectAttributes.addFlashAttribute("alertType", "success");
		} catch (Exception e) {
			model.addAttribute("alertMessage", "An error occurred while adding the new book!");
			model.addAttribute("alertType", "error");
			
			return "products/new";
		}
		
		return "redirect:/products.htm";
		
	}
	
	@RequestMapping(value = "/product/delete", method = RequestMethod.POST)
    public String deleteBook(@RequestParam("bookId") Long bookId, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            // Gọi service để xóa sách
            booksService.deleteBookById2(bookId);
            redirectAttributes.addFlashAttribute("alertMessage", "Sách đã được xóa thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sách. Vui lòng thử lại!");
            model.addAttribute("alertType", "error");
        }
        return "redirect:/products.htm"; // Quay lại danh sách sản phẩm
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
	        // Lấy Category từ Subcategory ID
	        CategoriesEntity category = subcategoriesService.getCategoryBySubcategoryId(subcategoryId);
	        if (category != null) {
	            return "<span id='categoryId'>" + category.getId() + "</span>"; // Trả về ID dạng HTML
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "<span id='categoryId'>No category found</span>"; // Trả về nếu không tìm thấy
	}

}