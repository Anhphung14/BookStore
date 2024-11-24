package bookstore.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.BooksEntity;

@Repository
public class BooksDAO {
	@Autowired
	SessionFactory factory;
	
	public List<BooksEntity> listBooks() {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM BooksEntity";
		Query query = session.createQuery(hql);
		
		List<BooksEntity> listBooks = query.list();
		
		return listBooks;
	}
	
	public BooksEntity getBookById(Long id) {
		Session session = factory.getCurrentSession();
		BooksEntity book = (BooksEntity) session.get(BooksEntity.class, id);
		
		return book;
	}
	
	public List<Object[]> getBooksWithQuantities(List<Long> ids) {
	    Session session = factory.getCurrentSession();
	    String hql = "SELECT b, i.quantity FROM BooksEntity b, InventoryEntity i WHERE b.id = i.book.id AND b.id IN (:ids)";
	    Query query = session.createQuery(hql);
	    query.setParameterList("ids", ids);
	    
	    List<Object[]> results = query.list();
	    return results;
	}
	
	public Object[] getBookWithQuantityById(Long id) {
	    Session session = factory.getCurrentSession();
	    String hql = "SELECT b, i.quantity FROM BooksEntity b, InventoryEntity i WHERE b.id = i.book.id AND b.id = :id";
	    Query query = session.createQuery(hql);
	    query.setParameter("id", id);
	    
	    Object[] result = (Object[]) query.uniqueResult();
	    return result;
	}
	
	public boolean addNewBook(BooksEntity newBook) {
		Session session = factory.getCurrentSession();
		
		try {
			session.save(newBook);
			System.out.println("Them sach moi thanh cong");
			
			return true;
		} catch (Exception e) {
			System.out.println("Them sach moi that bai");
			
		}
		
		return false;
	}
}
