package Mapper;

import bookstore.DTO.BooksDTO;
import bookstore.Entity.BooksEntity;

public class BooksMapper {
	public BooksDTO EntitytoDTO(BooksEntity bookEntity) {
		BooksDTO bookDTO = new BooksDTO();
		
		bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setPrice(bookEntity.getPrice());
        bookDTO.setDescription(bookEntity.getDescription());
        bookDTO.setPublication_year(bookEntity.getPublication_year());
        bookDTO.setPage_count(bookEntity.getPage_count());
        bookDTO.setLanguage(bookEntity.getLanguage());
        bookDTO.setStatus(bookEntity.getStatus());
        bookDTO.setQuantity(bookEntity.getQuantity());
        bookDTO.setCreatedAt(bookEntity.getCreatedAt());
        bookDTO.setUpdatedAt(bookEntity.getUpdatedAt());
        bookDTO.setSubcategory_id(bookEntity.getSubcategoriesEntity() != null ? bookEntity.getSubcategoriesEntity().getId() : null);
        bookDTO.setSupplier_id(bookEntity.getSupplier() != null ? bookEntity.getSupplier().getId() : null);
        
        return bookDTO;
	}
	
	public BooksEntity DTOtoEntity(BooksDTO bookDTO) {
		BooksEntity bookEntity = new BooksEntity();
		
		bookEntity.setId(bookDTO.getId());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPrice(bookDTO.getPrice());
        bookEntity.setDescription(bookDTO.getDescription());
        bookEntity.setPublication_year(bookDTO.getPublication_year());
        bookEntity.setPage_count(bookDTO.getPage_count());
        bookEntity.setLanguage(bookDTO.getLanguage());
        bookEntity.setStatus(bookDTO.getStatus());
        bookEntity.setQuantity(bookDTO.getQuantity());
        bookEntity.setCreatedAt(bookDTO.getCreatedAt());
        bookEntity.setUpdatedAt(bookDTO.getUpdatedAt());
        // Thiết lập các thuộc tính liên quan đến thực thể khác
        // entity.setSubcategoriesEntity(getSubcategoriesEntity(bookDTO.getSubcategoryId()));
        // entity.setSupplier(getSuppliersEntity(bookDTO.getSupplierId()));
		
		return bookEntity;
	}
}
