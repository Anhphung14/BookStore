package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.BooksDAO;
import bookstore.Entity.BooksEntity;

@Service
public class BooksService {
	@Autowired
	BooksDAO booksDAO;
	
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
}
