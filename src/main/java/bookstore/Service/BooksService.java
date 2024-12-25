package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import bookstore.DAO.BooksDAO;
import bookstore.DTO.BooksDTO;
import bookstore.Entity.BooksEntity;

@Service
public class BooksService {
	@Autowired
	BooksDAO booksDAO;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	Cloudinary cloudinary;
	
	
	
	public List<BooksEntity> getAllBooks() {
		return booksDAO.listBooks();
	}
	
	public BooksEntity getBookById(Long id) {
		return booksDAO.getBookById(id);
	}
	
	public List<Object[]> getBooksWithQuantitiesByIds(List<Long> ids) {
	    return booksDAO.getBooksWithQuantities(ids);
	}
	
	public Object[] getBookWithQuantityById(Long id) {
	    return booksDAO.getBookWithQuantityById(id);
	}
	
	// Cach 1
	public boolean deleteBookById(Long id) {
		return booksDAO.deleteBookById(id);
	}
	
	// Cach 2
	public boolean deleteBookById2(Long id) {
		return booksDAO.deleteBookById2(id);
	}
	
	public boolean updateBook(BooksEntity book) {
		return booksDAO.updateBook(book);
	}
	
	public boolean saveBook(BooksEntity book) {
		return booksDAO.saveBook(book);
	}
	
	public boolean changeStatus(Long bookId, int newStats) {
		return booksDAO.changeStatus(bookId, newStats);
	}
	
	public void editThumbnail_Images(BooksDTO bookDTO, BooksEntity bookEntity, BooksEntity bookGetById) {
		try {
			if (bookDTO.getThumbnail() != null && !bookDTO.getThumbnail().isEmpty()) {
//				String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
				String thumbnailPath = uploadService.uploadByCloudinary(bookDTO.getThumbnail(), "images/thumbnails/" + uploadService.toSlug(bookEntity.getTitle()));
				bookEntity.setThumbnail(thumbnailPath);
				
//				File savedFile = new File(context.getRealPath("/" + thumbnailPath));
//				while (!savedFile.exists()) {
//					Thread.sleep(100);
//				}
			} else {
				bookEntity.setThumbnail(bookGetById.getThumbnail());
			}
//			
			if (bookDTO.getImages() != null && bookDTO.getImages().length > 0) {
//				String imagesPath = "resources/images/books/" + toSlug(title) + "/";
				StringBuilder imagePaths = new StringBuilder();
				
				for (MultipartFile image : bookDTO.getImages()) {
					if (!image.isEmpty()) {
//						String imagePath = saveImage(image, imagesPath);
						String imagePath = uploadService.uploadByCloudinary(image, "images/books/" + uploadService.toSlug(bookEntity.getTitle()));
						imagePaths.append(imagePath).append(";");
					}
				}
				
				if (imagePaths.length() > 0) {
					bookEntity.setImages(imagePaths.toString());							
				} else {
					bookEntity.setImages(bookGetById.getImages());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addThumbnail_Images(BooksDTO bookDTO, BooksEntity bookEntity) {
		try {
			if (!bookDTO.getThumbnail().isEmpty()) {
//				String thumbnailPath = saveThumbnail(thumbnail, "resources/images/thumbnails/" + toSlug(title) + "/");
				String thumbnailPath = uploadService.uploadByCloudinary(bookDTO.getThumbnail(), "images/thumbnails/" + uploadService.toSlug(bookEntity.getTitle()));
				bookEntity.setThumbnail(thumbnailPath);
				
//				File savedFile = new File(context.getRealPath("/" + thumbnailPath));
//				while (!savedFile.exists()) {
//					Thread.sleep(100);
//				}
			}
//			
			if (bookDTO.getImages() != null && bookDTO.getImages().length > 0) {
//				String imagesPath = "resources/images/books/" + toSlug(title) + "/";
				StringBuilder imagePaths = new StringBuilder();
				
				for (MultipartFile image : bookDTO.getImages()) {
					if (!image.isEmpty()) {
//						String imagePath = saveImage(image, imagesPath);
						String imagePath = uploadService.uploadByCloudinary(image, "images/books/" + uploadService.toSlug(bookEntity.getTitle()));
						imagePaths.append(imagePath).append(";");
					}
				}
				
				bookEntity.setImages(imagePaths.toString());							
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean handleBookErrors(ModelMap model, BooksEntity bookEntity) {
		boolean hasError = false;
		
	    if (bookEntity.getTitle() == null || bookEntity.getTitle().trim().length() == 0) {	
	    	model.addAttribute("errorTitle", "The field 'Title' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getAuthor() == null || bookEntity.getAuthor().length() == 0) {
	    	model.addAttribute("errorAuthor", "The field 'Author' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getDescription() == null || bookEntity.getDescription().length() == 0) {
	    	model.addAttribute("errorDescription", "The field 'Description' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getPublication_year() == null || bookEntity.getPublication_year() == 0) {
	    	model.addAttribute("errorpublication_year", "The field 'Publication year' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getPrice() == null || bookEntity.getPrice() == 0) {
	    	model.addAttribute("errorPrice", "The field 'Price' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getPage_count() == null || bookEntity.getPage_count() == 0) {
	    	model.addAttribute("errorpage_count", "The field 'Page count' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getSubcategoriesEntity().getId() == null) {
	    	model.addAttribute("errorSubcategory", "The field 'Subcategory' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getSupplier().getId() == null) {
	    	model.addAttribute("errorSupplier", "The field 'Supplier' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getQuantity() == null || bookEntity.getQuantity() == 0) {
	    	model.addAttribute("errortotal_quantity", "The field 'Total quantity' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getLanguage() == null || bookEntity.getLanguage().length() == 0) {
	    	model.addAttribute("errorLanguage", "The field 'Language' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getStatus() == null) {
	    	model.addAttribute("errorStatus", "The field 'Status' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getThumbnail() == null) {
	    	model.addAttribute("errorThumbnail", "The field 'Thumbnail' must not be empty!");
	    	hasError = true;
	    }
	    
	    if (bookEntity.getImages() == null || bookEntity.getImages().length() == 0) {
	    	model.addAttribute("errorImages", "The field 'Images' must not be empty!");
	    	hasError = true;
	    }
	    
	    return hasError;
	}
	
	public boolean checkUpdateQuantity(ModelMap model, BooksEntity bookGetById, BooksEntity bookEdit) {
		if (bookEdit.getQuantity() < bookGetById.getInventoryEntity().getStock_quantity()) {
			model.addAttribute("errortotal_quantity", "Cannot update Total quantity to be less than Stock quantity!");
			return false;
		}
		
		return true;
	}
}
