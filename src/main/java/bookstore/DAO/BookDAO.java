package bookstore.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.Book;
import bookstore.Entity.Category;

@Repository
@Transactional
public class BookDAO {
	 @Autowired
     private SessionFactory sessionFactory;
	 
	 public List<Book> listBookOfSubCategory(String danhMuc, String danhMucCon, int pageNumber, int pageSize){
		Session session = sessionFactory.getCurrentSession();
		// HQL query để lọc sách theo tên danh mục con
		String hql = "FROM Book b WHERE b.subcategory.name = :subcategoryName";
		Query query = session.createQuery(hql);
		query.setParameter("subcategoryName", danhMucCon);
		
		 // Thiết lập phân trang
	    query.setFirstResult((pageNumber - 1) * pageSize); // Vị trí bản ghi bắt đầu
	    query.setMaxResults(pageSize); // Số lượng bản ghi trên mỗi trang
	    
		// Lấy danh sách sách thuộc danh mục con
		List<Book> bookList = query.list();
		
		// In thông tin kiểm tra
		for (Book book : bookList) {
		    System.out.println("Sách: " + book.getTitle() + ", Danh mục con: " + book.getSubcategory().getName());
		}
		return bookList;
	 }
	 
	 
	 public List<Book> listBookOfCategory(Long id, int pageNumber, int pageSize) {
		    Session session = sessionFactory.getCurrentSession();

		    // HQL query để lấy sách theo danh mục
		    String hql = "SELECT b " +
		                 "FROM Book b " +
		                 "JOIN b.subcategory sc " +
		                 "JOIN sc.category c " +
		                 "WHERE c.id = :categoryId " +
		                 "ORDER BY b.title ASC"; // Sắp xếp theo tên sách tăng dần

		    Query query = session.createQuery(hql);
		    query.setParameter("categoryId", id);

		    // Thiết lập phân trang
		    if (pageNumber < 1) pageNumber = 1;
		    if (pageSize < 1) pageSize = 8; // Giá trị mặc định
		    query.setFirstResult((pageNumber - 1) * pageSize);
		    query.setMaxResults(pageSize);
		    return query.list();
		}

	 
	 public int getTotalPagesOfSubBook(String danhMucCon, int pageSize) {
		    Session session = sessionFactory.getCurrentSession();
		    String hql = "SELECT COUNT(b.id) FROM Book b WHERE b.subcategory.name = :subcategoryName";
		    Query query = session.createQuery(hql);
		    query.setParameter("subcategoryName", danhMucCon);

		    // Tổng số sách
		    Long totalElements = (Long) query.uniqueResult();

		    // Tổng số trang
		    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
		    return totalPages;
		}
	 
	 public int getTotalPagesOfCateBook(Long idCategory, int pageSize) {
		    Session session = sessionFactory.getCurrentSession();
		    String hql = "SELECT COUNT(b.id) FROM Book b JOIN b.subcategory sc JOIN sc.category c WHERE c.id = :categoryId";
		    Query query = session.createQuery(hql);
		    query.setParameter("categoryId", idCategory);

		    // Tổng số sách
		    Long totalElements = (Long) query.uniqueResult();

		    // Tổng số trang
		    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
		    return totalPages;
		}

	 
	 public List<Object[]> countBookEachCategory() {
		    Session session = sessionFactory.getCurrentSession();
		    String hql = "SELECT c.id, c.name AS categoryName, COALESCE(COUNT(b.id), 0) AS totalBooks " +
		                 "FROM Category c " +
		                 "LEFT JOIN c.subcategories sc " +
		                 "LEFT JOIN sc.books b " +
		                 "GROUP BY c.id, c.name " +
		                 "ORDER BY c.id"; // Sắp xếp theo id của Category

		    Query query = session.createQuery(hql);
		    List<Object[]> results = query.list();

		    // Tạo danh sách mới để lưu kết quả kèm slug
		    List<Object[]> resultsWithSlug = new ArrayList<>();

		    for (Object[] result : results) {
		    	Long id = (Long) result[0];
		        String categoryName = (String) result[1];
		        Long totalBooks = (Long) result[2];
		        
		        // Tạo đối tượng Category tạm để tính slug
		        Category category = new Category();
		        category.setId(id);
		        category.setName(categoryName);
		        String slug = category.getSlug();
		        // Thêm slug vào danh sách kết quả
		        resultsWithSlug.add(new Object[]{categoryName, slug, totalBooks, id});
		    }

		    return resultsWithSlug;
		}

	 public List<Book> search(String searchQuery, int pageNumber, int pageSize ) {
		    // Khởi tạo phiên làm việc (Hibernate Session)
		    Session session = sessionFactory.getCurrentSession();
		    
		    BigDecimal minPrice = null;
		    BigDecimal maxPrice = null;
		    if (searchQuery.contains("-")) {
		        String[] parts = searchQuery.split("-");
		        try {
		            // Chuyển đổi các giá trị thành BigDecimal (nếu hợp lệ)
		            minPrice = new BigDecimal(parts[0].trim());
		            maxPrice = new BigDecimal(parts[1].trim());
		        } catch (NumberFormatException e) {
		            // Không phải là khoảng giá hợp lệ, tiếp tục tìm kiếm theo text
		            minPrice = null;
		            maxPrice = null;
		        }
		    }
		    
		    
		    // HQL query để tìm kiếm dữ liệu phù hợp với searchQuery
		    String hql = "FROM Book b " +
		                 "LEFT JOIN FETCH b.supplier s " +
		                 "WHERE LOWER(b.title) LIKE :searchQuery " + // Tìm theo tên sách
		                 "   OR LOWER(b.author) LIKE :searchQuery " + // Tìm theo tên tác giả
		                 "   OR LOWER(s.name) LIKE :searchQuery";    // Tìm theo tên nhà cung cấp
		    
		    // Nếu là khoảng giá hợp lệ, thêm điều kiện tìm kiếm theo giá
		    if (minPrice != null && maxPrice != null) {
		        hql += " OR b.price BETWEEN :minPrice AND :maxPrice ";
		    }
		    
		    
		    Query query = session.createQuery(hql);
		    // Thêm tham số cho query với giá trị tìm kiếm
		    query.setParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%");
		    
		    if (minPrice != null && maxPrice != null) {
		        query.setParameter("minPrice", minPrice);
		        query.setParameter("maxPrice", maxPrice);
		    }
		    
		    
		    
		    // Thiết lập phân trang
		    query.setFirstResult((pageNumber - 1) * pageSize); // Vị trí bản ghi bắt đầu
		    query.setMaxResults(pageSize); // Số lượng bản ghi trên mỗi trang
		    List<Book> bookList = query.list();
		   
		    return bookList;
		}
	 
	 public int getTotalPagesOfSearch(String searchQuery, int pageSize) {
		    // Lấy phiên làm việc (Hibernate Session)
		    Session session = sessionFactory.getCurrentSession();

		    BigDecimal minPrice = null;
		    BigDecimal maxPrice = null;

		    // Kiểm tra nếu searchQuery là khoảng giá
		    if (searchQuery.contains("-")) {
		        String[] parts = searchQuery.split("-");
		        try {
		            // Chuyển đổi các giá trị thành BigDecimal (nếu hợp lệ)
		            minPrice = new BigDecimal(parts[0].trim());
		            maxPrice = new BigDecimal(parts[1].trim());
		        } catch (NumberFormatException e) {
		            // Không phải là khoảng giá hợp lệ
		            minPrice = null;
		            maxPrice = null;
		        }
		    }

		    // HQL để đếm tổng số sách phù hợp với searchQuery
		    String hql = "SELECT COUNT(b.id) " +
		                 "FROM Book b " +
		                 "LEFT JOIN b.supplier s " +
		                 "WHERE LOWER(b.title) LIKE :searchQuery " +
		                 "   OR LOWER(b.author) LIKE :searchQuery " +
		                 "   OR LOWER(s.name) LIKE :searchQuery";

		    // Nếu là khoảng giá hợp lệ, thêm điều kiện tìm kiếm theo giá
		    if (minPrice != null && maxPrice != null) {
		        hql += " OR b.price BETWEEN :minPrice AND :maxPrice";
		    }

		    // Tạo query
		    Query query = session.createQuery(hql);

		    // Thiết lập tham số cho query
		    query.setParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%");

		    // Thiết lập tham số cho khoảng giá nếu hợp lệ
		    if (minPrice != null && maxPrice != null) {
		        query.setParameter("minPrice", minPrice);
		        query.setParameter("maxPrice", maxPrice);
		    }

		    // Tổng số sách phù hợp
		    Long totalElements = (Long) query.uniqueResult();

		    // Tính tổng số trang
		    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
		    return totalPages;
		}


}
