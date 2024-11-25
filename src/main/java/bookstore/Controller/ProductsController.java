package bookstore.Controller;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import com.ckfinder.connector.plugins.SaveFileCommand;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.SuppliersEntity;
import bookstore.Service.BooksService;
import bookstore.Service.CategoriesService;
import bookstore.Service.InventoryService;
import bookstore.Service.SuppliersService;

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
		
//		System.out.println(servletContext.getRealPath("/"));
		
//		System.out.println(new File(this.getClass().getClassLoader().getResource("").getPath()).getParentFile().getParentFile().getPath());
		
		return "products/index";
		
	}
	
	@RequestMapping("/product/edit/{id}")
	public String productEdit(@PathVariable("id") Long id, ModelMap model) {
//		BooksEntity book = getBookById(id);
		
		model.addAttribute("task", "edit");
//		model.addAttribute("book", book);
//		model.addAttribute("stock_quantity", getStockQuantityById(id));
		Object[] bookWithQuantity = booksService.getBookWithQuantityById(id);
		
		BooksEntity book = (BooksEntity) bookWithQuantity[0]; 
		Integer quantity = (Integer) bookWithQuantity[1]; 
		model.addAttribute("book", book); 
		model.addAttribute("quantity", quantity);
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		return "products/edit";
	}
	
	@RequestMapping("/product/new")
	public String productNew(ModelMap model) {
		
		model.addAttribute("task", "new");
		model.addAttribute("listCategories", categoriesService.getAllCategories());
		model.addAttribute("listSuppliers", suppliersService.getAllSuppliers());
		
		return "products/new";
	}
	
	@RequestMapping(value = "/product/save", method = RequestMethod.POST)
	public String productSave(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam("task") String task, @RequestParam(value = "id", required = false) Long id,
			@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("price") Double price, @RequestParam("description") String description,
			@RequestParam("category") Long categoryId, @RequestParam("supplier") Long supplierId,
			@RequestParam("quantity") int quantity, @RequestParam("publication_year") int publication_year, 
			@RequestParam("page_count") int page_count, @RequestParam("language") String language,
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail, @RequestParam(value = "images", required = false) MultipartFile[] images) {
		Session session = factory.getCurrentSession();
		
		try {
			if (task.equals("new")) {
				BooksEntity newBook = new BooksEntity();
				
				newBook.setTitle(title);
				newBook.setAuthor(author);
				newBook.setPrice(price);
				newBook.setDescription(description);
				newBook.setStock_quantity(quantity);
				newBook.setPublication_year(publication_year);
				newBook.setLanguage(language);
				newBook.setPage_count(page_count);
				newBook.setCreated_at(new Date());
				newBook.setUpdated_at(new Date());
				
				CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
				SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
			
				newBook.setCategory(category);
				newBook.setSupplier(supplier);
				
				try {
					if (!thumbnail.isEmpty()) {
						String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
						
						newBook.setThumbnail(thumbnailPath);
						
						File savedFile = new File(context.getRealPath("/" + thumbnailPath));
						while (!savedFile.exists()) {
							Thread.sleep(100);
						}
					}
					
					if (images != null && images.length > 0) {
						String imagesPath = "resources/images/books/" + toSlug(title) + "/";
						StringBuilder imagePaths = new StringBuilder();

						for (MultipartFile image : images) {
							if (!image.isEmpty()) {
								String imagePath = saveImage(image, imagesPath);
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
				
				return "redirect:/products.htm";
			} else if (task.equals("edit")) {
				BooksEntity selectedBook = booksService.getBookById(id);
				
				if (selectedBook == null) {
					return "redirect:/product/new.htm";
				}
			
				
				CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
				SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
				InventoryEntity inventory = inventoryService.getInventoryByBookId(id);
				
				selectedBook.setTitle(title);
				selectedBook.setAuthor(author);
				selectedBook.setPrice(price);
				selectedBook.setDescription(description);
				selectedBook.setPublication_year(publication_year);
				selectedBook.setPage_count(page_count);
				selectedBook.setLanguage(language);
				selectedBook.setUpdated_at(new Date());
				
				selectedBook.setCategory(category);
				selectedBook.setSupplier(supplier);
				
				try {
					if (!thumbnail.isEmpty()) {
						String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
						selectedBook.setThumbnail(thumbnailPath);
						
						File savedFile = new File(context.getRealPath("/" + thumbnailPath));
						while (!savedFile.exists()) {
							Thread.sleep(100);
						}
					}
					
					if (images != null && images.length > 0) {
						String imagesPath = "resources/images/books/" + toSlug(title) + "/";
						StringBuilder imagePaths = new StringBuilder();

						for (MultipartFile image : images) {
							if (!image.isEmpty()) {
								String imagePath = saveImage(image, imagesPath);
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

//						model.addAttribute("alertMessage", "Successfully updated BookID: " + id);
//				        model.addAttribute("alertType", "success");
						
				        redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated BookID: " + id);
				        redirectAttributes.addFlashAttribute("alertType", "success");
					} else {
						redirectAttributes.addFlashAttribute("alertMessage", "An error occurred while updating the BookId: " + id);
						redirectAttributes.addFlashAttribute("alertType", "error");
					}
					
				} catch (Exception e) {
					System.err.println("An error occurred while updating the book: " + e.getMessage());
				}
				

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi roi");
		}
		
        
//        return "redirect:/product/edit/" + id + ".htm";
		return "products/edit";
	}
	
	@RequestMapping(value = "/product/edit", method = RequestMethod.POST)
	public String productEdit (ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("price") Double price, @RequestParam("description") String description,
			@RequestParam("category") Long categoryId, @RequestParam("supplier") Long supplierId,
			@RequestParam("quantity") int quantity, @RequestParam("publication_year") int publication_year, 
			@RequestParam("page_count") int page_count, @RequestParam("language") String language,
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail, @RequestParam(value = "images", required = false) MultipartFile[] images) {
		
		Session session = factory.getCurrentSession();
		
		try {
			BooksEntity selectedBook = booksService.getBookById(id);
			
			if (selectedBook == null) {
				return "redirect:/product/new.htm";
			}
			
			
			CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
			SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
			InventoryEntity inventory = inventoryService.getInventoryByBookId(id);
			
			selectedBook.setTitle(title);
			selectedBook.setAuthor(author);
			selectedBook.setPrice(price);
			selectedBook.setDescription(description);
			selectedBook.setPublication_year(publication_year);
			selectedBook.setPage_count(page_count);
			selectedBook.setLanguage(language);
			selectedBook.setUpdated_at(new Date());
			
			selectedBook.setCategory(category);
			selectedBook.setSupplier(supplier);
			
			try {
				if (!thumbnail.isEmpty()) {
//					String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
					String thumbnailPath = uploadByCloudinary(thumbnail, "images/thumbnails/" + toSlug(title));
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
							String imagePath = uploadByCloudinary(image, "images/books/" + toSlug(title));
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
			@RequestParam("category") Long categoryId, @RequestParam("supplier") Long supplierId,
			@RequestParam("quantity") int quantity, @RequestParam("publication_year") int publication_year, 
			@RequestParam("page_count") int page_count, @RequestParam("language") String language,
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail, @RequestParam(value = "images", required = false) MultipartFile[] images) {
		
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
			newBook.setCreated_at(new Date());
			newBook.setUpdated_at(new Date());
			
			CategoriesEntity category = (CategoriesEntity) session.get(CategoriesEntity.class, categoryId);
			SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, supplierId);
			
			newBook.setCategory(category);
			newBook.setSupplier(supplier);
			
			try {
				if (!thumbnail.isEmpty()) {
					String thumbnailPath = uploadByCloudinary(thumbnail, "images/thumbnails/" + toSlug(title));
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
							String imagePath = uploadByCloudinary(image, "images/books/" + toSlug(title));
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
	
	public String saveThumbnail(MultipartFile thumbnail, String uploadPath) throws IOException {
		String realPath = "D:\\Codes\\Eclipse3\\src\\main\\webapp" + "/" + uploadPath;
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String originalFilename = thumbnail.getOriginalFilename();
		File destination = new File(dir, originalFilename);
		thumbnail.transferTo(destination);
		
		return uploadPath + originalFilename;
	}
	
	public String saveImage(MultipartFile image, String uploadPath) throws IOException {
	    String realPath = "D:\\Codes\\Eclipse3\\src\\main\\webapp" + "/" + uploadPath;
	    File dir = new File(realPath);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    // Lưu file vào thư mục
	    File destination = new File(dir, image.getOriginalFilename());
	    image.transferTo(destination);

	    // Trả về đường dẫn tương đối để lưu trong database
	    return uploadPath + image.getOriginalFilename();
	}
	
	public String toSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        // Chuẩn hóa chuỗi và loại bỏ dấu
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noDiacritics = normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu chính xác

        // Giữ lại chữ cái, số, thay Đ/đ thành d, rồi loại bỏ ký tự đặc biệt
        noDiacritics = noDiacritics.replaceAll("[Đđ]", "d"); // Xử lý chữ Đ và đ
        return Arrays.stream(noDiacritics.split("\\s+")) // Split theo khoảng trắng
                     .map(word -> word.replaceAll("[^a-zA-Z0-9]", "")) // Loại bỏ ký tự đặc biệt
                     .filter(word -> !word.isEmpty()) // Loại bỏ từ rỗng
                     .map(String::toLowerCase) // Chuyển thành chữ thường
                     .collect(Collectors.joining("-")); // Ghép lại bằng dấu gạch ngang
    }
	
	public String uploadByCloudinary(MultipartFile thumbnail, String folderName) throws IOException {
		Map r = cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("folder", folderName));
		
		String imagePath = (String) r.get("secure_url");
		
		System.out.println(imagePath);
		
		return imagePath;
	}
	

}