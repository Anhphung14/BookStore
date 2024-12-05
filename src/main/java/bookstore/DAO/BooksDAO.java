package bookstore.DAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;

@Repository
@Transactional
public class BooksDAO {
	 @Autowired
     private SessionFactory sessionFactory;
	 
	 public List<BooksEntity> listBookOfSubCategory(String danhMuc, String danhMucCon, int pageNumber, int pageSize){
			Session session = sessionFactory.getCurrentSession();
			// HQL query để lọc sách theo tên danh mục con
			String hql = "FROM BooksEntity b WHERE b.subcategoriesEntity.name = :subcategoryName";
			Query query = session.createQuery(hql);
			query.setParameter("subcategoryName", danhMucCon);
			
			 // Thiết lập phân trang
		    query.setFirstResult((pageNumber - 1) * pageSize); // Vị trí bản ghi bắt đầu
		    query.setMaxResults(pageSize); // Số lượng bản ghi trên mỗi trang
		    
			// Lấy danh sách sách thuộc danh mục con
			List<BooksEntity> bookList = query.list();
			
			// In thông tin kiểm tra
			for (BooksEntity booksEntity : bookList) {
			    System.out.println("Sách: " + booksEntity.getTitle() + ", Danh mục con: " + booksEntity.getSubcategoriesEntity().getName());
			}
			return bookList;
		 }
	 
	 public List<BooksEntity> listBookOfSubCategorySorted(String danhMuc, String danhMucCon, int pageNumber, int pageSize, String sortBy) {
		    Session session = sessionFactory.getCurrentSession();
		    
		    // Câu truy vấn cơ bản để lọc sách theo danh mục con
		    String hql = "FROM BooksEntity b WHERE b.subcategoriesEntity.name = :subcategoryName";
		    System.out.println("sortBy: " + sortBy);
		    // Thêm phần sắp xếp (ORDER BY) nếu sortBy không rỗng
		    if (sortBy != null && !sortBy.isEmpty()) {
		        switch (sortBy) {
		            case "nameAsc":
		                hql += " ORDER BY b.title ASC";  // Sắp xếp theo tên sách từ A - Z
		                break;
		            case "nameDesc":
		                hql += " ORDER BY b.title DESC"; // Sắp xếp theo tên sách từ Z - A
		                break;
		            case "priceAsc":
		                hql += " ORDER BY b.price ASC";  // Sắp xếp theo giá tăng dần
		                break;
		            case "priceDesc":
		                hql += " ORDER BY b.price DESC"; // Sắp xếp theo giá giảm dần
		                break;
		            case "newest":
		                hql += " ORDER BY b.createdAt DESC";  // Sắp xếp theo ngày tạo mới nhất
		                break;
		            case "oldest":
		                hql += " ORDER BY b.createdAt ASC";   // Sắp xếp theo ngày tạo cũ nhất
		                break;
		            default:
		            	hql += "ORDER BY b.createdAt DESC"; // Mặc định sắp xếp theo tên sách
		                break;
		        }
		    }
		    
		    // Tạo query và thiết lập tham số
		    Query query = session.createQuery(hql);
		    query.setParameter("subcategoryName", danhMucCon);
		    
		    // Thiết lập phân trang
		    query.setFirstResult((pageNumber - 1) * pageSize); // Vị trí bản ghi bắt đầu
		    query.setMaxResults(pageSize); // Số lượng bản ghi trên mỗi trang
		    
		    // Lấy danh sách sách thuộc danh mục con và đã sắp xếp
		    List<BooksEntity> bookList = query.list();
		    
		    // In thông tin kiểm tra
		    for (BooksEntity booksEntity : bookList) {
		        System.out.println("Sách: " + booksEntity.getTitle() + ", Danh mục con: " + booksEntity.getSubcategoriesEntity().getName());
		    }
		    
		    return bookList;
		}

		 
		 
		 public List<BooksEntity> listBookOfCategory(Long id, int pageNumber, int pageSize) {
			    Session session = sessionFactory.getCurrentSession();

			    // HQL query để lấy sách theo danh mục
			    String hql = "SELECT b " +
		                 "FROM BooksEntity b " +
		                 "JOIN b.subcategoriesEntity sc " +
		                 "JOIN sc.categoriesEntity c " +
		                 "WHERE c.id = :categoryId " +
		                 "ORDER BY b.title ASC"; // Sắp xếp theo tên sách tăng dần

			    Query query = session.createQuery(hql);
			    query.setParameter("categoryId", id);

			    // Thiết lập phân trang
			    if (pageNumber < 1) pageNumber = 1;
			    if (pageSize < 1) pageSize = 16; // Giá trị mặc định
			    query.setFirstResult((pageNumber - 1) * pageSize);
			    query.setMaxResults(pageSize);
			    return query.list();
			}
		 
		 public List<BooksEntity> listBookOfCategorySorted(Long id, int pageNumber, int pageSize, String sortBy) {
			    Session session = sessionFactory.getCurrentSession();

			    // Câu truy vấn cơ bản để lấy sách theo danh mục
			    String hql = "SELECT b " +
			                 "FROM BooksEntity b " +
			                 "JOIN b.subcategoriesEntity sc " +
			                 "JOIN sc.categoriesEntity c " +
			                 "WHERE c.id = :categoryId ";

			    // Thêm phần sắp xếp dựa trên tham số sortBy
			    if (sortBy != null && !sortBy.isEmpty()) {
			        switch (sortBy) {
			            case "nameAsc":
			                hql += "ORDER BY b.title ASC"; // Sắp xếp theo tên sách A-Z
			                break;
			            case "nameDesc":
			                hql += "ORDER BY b.title DESC"; // Sắp xếp theo tên sách Z-A
			                break;
			            case "priceAsc":
			                hql += "ORDER BY b.price ASC"; // Sắp xếp theo giá tăng dần
			                break;
			            case "priceDesc":
			                hql += "ORDER BY b.price DESC"; // Sắp xếp theo giá giảm dần
			                break;
			            case "newest":
			                hql += "ORDER BY b.createdAt DESC"; // Sắp xếp theo ngày thêm mới nhất
			                break;
			            case "oldest":
			                hql += "ORDER BY b.createdAt ASC"; // Sắp xếp theo ngày thêm cũ nhất
			                break;
			            default:
			            	hql += "ORDER BY b.createdAt DESC"; // Mặc định sắp xếp theo tên sách
			                break;
			        }
			    } else {
			        // Mặc định nếu không có sortBy, sắp xếp theo tên sách A-Z
			        hql += "ORDER BY b.title ASC";
			    }

			    Query query = session.createQuery(hql);
			    query.setParameter("categoryId", id);

			    // Thiết lập phân trang
			    if (pageNumber < 1) pageNumber = 1;
			    if (pageSize < 1) pageSize = 16; // Giá trị mặc định
			    query.setFirstResult((pageNumber - 1) * pageSize);
			    query.setMaxResults(pageSize);

			    // Trả về danh sách sách đã được lọc và sắp xếp
			    return query.list();
			}


		 
		 public int getTotalPagesOfSubBook(String danhMucCon, int pageSize) {
			    Session session = sessionFactory.getCurrentSession();
			    String hql = "SELECT COUNT(b.id) FROM BooksEntity b WHERE b.subcategoriesEntity.name = :subcategoryName";
			    Query query = session.createQuery(hql);
			    query.setParameter("subcategoryName", danhMucCon);

			    // Tổng số sách
			    Long totalElements = (Long) query.uniqueResult();

			    // Tổng số trang
			    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
			    return totalPages;
			}
		 
		 public int getTotalPagesOfCateBook(Long idCategoriesEntity, int pageSize) {
			    Session session = sessionFactory.getCurrentSession();
			    String hql = "SELECT COUNT(b.id) FROM BooksEntity b JOIN b.subcategoriesEntity sc JOIN sc.categoriesEntity c WHERE c.id = :categoryId";
			    Query query = session.createQuery(hql);
			    query.setParameter("categoryId", idCategoriesEntity);

			    // Tổng số sách
			    Long totalElements = (Long) query.uniqueResult();

			    // Tổng số trang
			    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
			    return totalPages;
			}

		 
		 public List<Object[]> countBookEachCategory() {
			    Session session = sessionFactory.getCurrentSession();
			    String hql = "SELECT c.id, c.name AS categoryName, COALESCE(COUNT(b.id), 0) AS totalBooks " +
		                 "FROM CategoriesEntity c " +
		                 "LEFT JOIN c.subcategoriesEntity sc " +
		                 "LEFT JOIN sc.books b " +
		                 "GROUP BY c.id, c.name " +
		                 "ORDER BY c.id"; // Sắp xếp theo id của CategoriesEntity

			    Query query = session.createQuery(hql);
			    List<Object[]> results = query.list();

			    // Tạo danh sách mới để lưu kết quả kèm slug
			    List<Object[]> resultsWithSlug = new ArrayList<>();

			    for (Object[] result : results) {
			    	Long id = (Long) result[0];
			        String categoryName = (String) result[1];
			        Long totalBooks = (Long) result[2];
			        
			        // Tạo đối tượng CategoriesEntity tạm để tính slug
			        CategoriesEntity category = new CategoriesEntity();
			        category.setId(id);
			        category.setName(categoryName);
			        String slug = category.getSlug();
			        // Thêm slug vào danh sách kết quả
			        resultsWithSlug.add(new Object[]{categoryName, slug, totalBooks, id});
			    }

			    return resultsWithSlug;
			}

		/* public List<BooksEntity> search(String searchQuery, int pageNumber, int pageSize ) {
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
			    String hql = "FROM BooksEntity b " +
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
			    List<BooksEntity> bookList = query.list();
			   
			    return bookList;
			} */
		 
		 public List<BooksEntity> search(String searchQuery, int pageNumber, int pageSize, String sortBy) {
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

			    // Bắt đầu câu truy vấn HQL cơ bản
			    String hql = "FROM BooksEntity b " +
			                 "LEFT JOIN FETCH b.supplier s " +
			                 "WHERE LOWER(b.title) LIKE :searchQuery " +  // Tìm theo tên sách
			                 "   OR LOWER(b.author) LIKE :searchQuery ";  // Tìm theo tên tác giả

			    // Thêm điều kiện lọc theo giá (nếu có khoảng giá)
			    if (minPrice != null && maxPrice != null) {
			        hql += "AND b.price BETWEEN :minPrice AND :maxPrice ";
			    }

			    // Thêm phần sắp xếp tùy thuộc vào giá trị sortBy
			    if (sortBy != null && !sortBy.isEmpty()) {
			        switch (sortBy) {
			            case "newest":
			                hql += "ORDER BY b.createdAt DESC"; // Sắp xếp theo ngày tạo (mới nhất)
			                break;
			            case "oldest":
			                hql += "ORDER BY b.createdAt ASC";  // Sắp xếp theo ngày tạo (cũ nhất)
			                break;
			            case "priceAsc":
			                hql += "ORDER BY b.price ASC";  // Sắp xếp theo giá tăng dần
			                break;
			            case "priceDesc":
			                hql += "ORDER BY b.price DESC"; // Sắp xếp theo giá giảm dần
			                break;
			            case "nameAsc":
			                hql += "ORDER BY b.title ASC";  // Sắp xếp theo tên sách (A-Z)
			                break;
			            case "nameDesc":
			                hql += "ORDER BY b.title DESC"; // Sắp xếp theo tên sách (Z-A)
			                break;
			            default:
			            	hql += "ORDER BY b.createdAt DESC"; // Mặc định sắp xếp theo tên sách
			                break;
			        }
			    }

			    // Tạo và thực thi câu truy vấn
			    Query query = session.createQuery(hql);
			    query.setParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%");

			    // Nếu có khoảng giá, thêm tham số cho giá
			    if (minPrice != null && maxPrice != null) {
			        query.setParameter("minPrice", minPrice);
			        query.setParameter("maxPrice", maxPrice);
			    }

			    // Thiết lập phân trang
			    query.setFirstResult((pageNumber - 1) * pageSize);  // Vị trí bản ghi bắt đầu
			    query.setMaxResults(pageSize);  // Số lượng bản ghi trên mỗi trang

			    // Trả về danh sách kết quả tìm kiếm
			    return query.list();
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
			                 "FROM BooksEntity b " +
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
		 
		 public List<BooksEntity> getBooksBySubcategory(Long subcategory_id) {
			 Session session = sessionFactory.getCurrentSession();
			 String hql = "From BooksEntity where subcategoriesEntity.id = :subcategory_id";
			 Query query = session.createQuery(hql);
			 query.setParameter("subcategory_id", subcategory_id);
			 return query.list();
		 }
		 
		 public List<BooksEntity> listBooks() {
				Session session = sessionFactory.getCurrentSession();
				
				String hql = "FROM BooksEntity";
				Query query = session.createQuery(hql);
				
				List<BooksEntity> listBooks = query.list();
				
				return listBooks;
			}
			
			public BooksEntity getBookById(Long id) {
				Session session = sessionFactory.getCurrentSession();
				BooksEntity book = (BooksEntity) session.get(BooksEntity.class, id);
				
				return book;
			}
			
			public List<Object[]> getBooksWithQuantities(List<Long> ids) {
			    if (ids == null || ids.isEmpty()) {
			        return new ArrayList<>(); // Trả về danh sách rỗng nếu ids rỗng hoặc null
			    }

			    Session session = sessionFactory.getCurrentSession();
			    String hql = "SELECT b, i.quantity FROM BooksEntity b, InventoryEntity i WHERE b.id = i.book.id AND b.id IN (:ids)";
			    Query query = session.createQuery(hql);
			    query.setParameterList("ids", ids);
			    
			    List<Object[]> results = query.list();
			    return results;
			}
			
			public Object[] getBookWithQuantityById(Long id) {
			    Session session = sessionFactory.getCurrentSession();
			    String hql = "SELECT b, i.quantity FROM BooksEntity b, InventoryEntity i WHERE b.id = i.book.id AND b.id = :id";
			    Query query = session.createQuery(hql);
			    query.setParameter("id", id);
			    
			    Object[] result = (Object[]) query.uniqueResult();
			    return result;
			}
			
			public boolean addNewBook(BooksEntity newBook) {
				Session session = sessionFactory.getCurrentSession();
				
				try {
					session.save(newBook);
					System.out.println("Them sach moi thanh cong");
					
					return true;
				} catch (Exception e) {
					System.out.println("Them sach moi that bai");
					
				}
				
				return false;
			}
		
		// Cach 1
		public boolean deleteBookById(Long bookId) {
			Session session = sessionFactory.getCurrentSession();
			
			BooksEntity book = getBookById(bookId);
			
			if (book != null) {
				if (book.getSubcategoriesEntity() != null) {
		            book.getSubcategoriesEntity().getBooks().remove(book);
		            book.setSubcategoriesEntity(null);
		        }

		        if (book.getSupplier() != null) {
		            book.getSupplier().getBooks().remove(book);
		            book.setSupplier(null);
		        }
		        
				session.delete(book);
				
				return true;
			}
			
			return false;
		}
		
		// Cach 2
		public boolean deleteBookById2(Long bookId) {
		    Session session = sessionFactory.openSession();
		    Transaction t = session.beginTransaction();
		    
		    try {
		    	String hql = "DELETE FROM BooksEntity WHERE id = :bookId";
		    	int result = session.createQuery(hql)
		    			.setParameter("bookId", bookId)
		    			.executeUpdate();
		    	
		    	t.commit();
		    	
		    	if (result > 0) {
		    		return true;
		    	}
		    	
		    } catch (Exception e) {
				t.rollback();
			} finally {
				session.close();
			}
		    
		    return false;
		}

		public BooksEntity getBookByIdHQL(Long id) {
	        Session session = sessionFactory.getCurrentSession();
	        BooksEntity book = (BooksEntity) session.createQuery("FROM BooksEntity WHERE id = :id").setParameter("id", id).uniqueResult();
	        return book;
	    }
		
		
		public List<BooksEntity> getRandBooksBySubcategory(Long subcategoryId) {
		    Session session = sessionFactory.getCurrentSession();
		    
		    String hql = "FROM BooksEntity WHERE subcategoriesEntity.id = :subcategoryId ORDER BY rand()";
		    
		    List<BooksEntity> books = session.createQuery(hql).setParameter("subcategoryId", subcategoryId).setMaxResults(7).list();

		    return books;
		}
		
		public boolean updateBook(BooksEntity book) {
			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			
			try {
				session.update(book);
				t.commit();
				
				return true;
			} catch (Exception e) {
				t.rollback();
				
			} finally {
				session.close();
			}
			
			return false;
		}
		
		public boolean saveBook(BooksEntity book) {
			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			
			try {
				session.save(book);
				t.commit();
				
				return true;
			} catch (Exception e) {
				t.rollback();
				
			} finally {
				session.close();
			}
			
			return false;
		}
		
		public boolean changeStatus(Long bookId, int newStatus) {
			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			boolean isUpdated = false;
			
			try {
				String hql = "UPDATE BooksEntity SET status = :newStatus WHERE id = :bookId";
				
				int result = session.createQuery(hql)
							.setParameter("newStatus", newStatus)
							.setParameter("bookId", bookId)
							.executeUpdate();
				
				if (result > 0) {
					isUpdated = true;
				}
				
				t.commit();
			} catch (Exception e) {
				t.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			
			return isUpdated;
		}
		
		
		public List<BooksEntity> getListBooksHaveSearchParam(int page, int size, String book, Double minPrice, Double maxPrice,
				Integer minQuantity, Integer maxQuantity, Integer bookStatus, 
                String fromDate, String toDate) {

			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM BooksEntity c WHERE 1=1";
			
			// Thêm điều kiện tìm kiếm
			if (book != null && !book.isEmpty()) {
			hql += " AND (c.title LIKE :book OR c.subcategoriesEntity.name LIKE :book)";
			}
			if (minPrice != null) {
			hql += " AND c.price >= :minPrice";
			}
			if (maxPrice != null) {
			hql += " AND c.price <= :maxPrice";
			}
			if (minQuantity != null) {
			hql += " AND c.quantity >= :minQuantity";
			}
			if (maxQuantity != null) {
			hql += " AND c.quantity <= :maxQuantity";
			}
			if (bookStatus != null) {
			hql += " AND c.status = :bookStatus";
			}
			if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
	            hql += " AND CONVERT(date, c.updatedAt) BETWEEN :fromDate AND :toDate";
	        }
			
			// Thực hiện truy vấn với phân trang
			Query query = session.createQuery(hql);
			
			// Set các tham số vào query
			if (book != null && !book.isEmpty()) {
			query.setParameter("book", "%" + book + "%");
			}
			if (minPrice != null) {
				query.setParameter("minPrice", Double.valueOf(minPrice));
			}
			if (maxPrice != null) {
				query.setParameter("maxPrice", Double.valueOf(maxPrice));
			}
			if (minQuantity != null) {
				query.setParameter("minQuantity", Integer.valueOf(minQuantity));
			}
			if (maxQuantity != null) {
				query.setParameter("maxQuantity", Integer.valueOf(maxQuantity));
			}
			if (bookStatus != null) {
				query.setParameter("bookStatus", bookStatus);
			}
			if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
	            try {
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Định dạng ngày
	                Date startDate = sdf.parse(fromDate);
	                Date endDate = sdf.parse(toDate);
	                query.setParameter("fromDate", startDate);
	                query.setParameter("toDate", endDate);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
			
			// Thiết lập phân trang
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			
			return query.list();
		}
		
		
		public long countProducts(String book, Double minPrice, Double maxPrice,
				Integer minQuantity, Integer maxQuantity, Integer bookStatus, 
                String fromDate, String toDate) {

				Session session = sessionFactory.getCurrentSession();
				String countQuery = "SELECT count(c) FROM BooksEntity c WHERE 1=1";
				
				// Thêm các điều kiện giống như truy vấn lấy danh sách sản phẩm
				if (book != null && !book.isEmpty()) {
					countQuery += " AND (c.title LIKE :book OR c.subcategoriesEntity.name LIKE :book)";
				}
				if (minPrice != null) {
					countQuery += " AND c.price >= :minPrice";
				}
				if (maxPrice != null) {
					countQuery += " AND c.price <= :maxPrice";
				}
				if (minQuantity != null) {
					countQuery += " AND c.quantity >= :minQuantity";
				}
				if (maxQuantity != null) {
					countQuery += " AND c.quantity <= :maxQuantity";
				}
				if (bookStatus != null) {
						countQuery += " AND c.status = :bookStatus";
				}
				if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
					countQuery += " AND CONVERT(date, c.updatedAt) BETWEEN :fromDate AND :toDate";
		        }
				
				// Thực hiện truy vấn đếm
				Query query = session.createQuery(countQuery);
				
				// Set các tham số vào query
				if (book != null && !book.isEmpty()) {
					query.setParameter("book", "%" + book + "%");
				}
				if (minPrice != null) {
					query.setParameter("minPrice", Double.valueOf(minPrice));
				}
				if (maxPrice != null ) {
					query.setParameter("maxPrice", Double.valueOf(maxPrice));
				}
				if (minQuantity != null) {
					query.setParameter("minQuantity", Integer.valueOf(minQuantity));
				}
				if (maxQuantity != null) {
					query.setParameter("maxQuantity", Integer.valueOf(maxQuantity));
				}
				if (bookStatus != null) {
					query.setParameter("bookStatus", bookStatus);
				}
				if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
		            try {
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Định dạng ngày
		                Date startDate = sdf.parse(fromDate);
		                Date endDate = sdf.parse(toDate);
		                query.setParameter("fromDate", startDate);
		                query.setParameter("toDate", endDate);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
				
				return (long) query.uniqueResult();
		}
		
		public long countAllBook() {
			Session session = sessionFactory.getCurrentSession();
			String hql = "SELECT COUNT(*) FROM BooksEntity";
			Query query = session.createQuery(hql);
			long count = (long) query.uniqueResult();
		    return count;
		}
		
		public int getTotalPagesOfAllBooks(int pageSize) {
		    Session session = sessionFactory.getCurrentSession();
		    
		    String hql = "SELECT COUNT(b.id) FROM BooksEntity b";  
		    Query query = session.createQuery(hql);

		    Long totalElements = (Long) query.uniqueResult();

		    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
		    return totalPages;
		}
		
		public List<BooksEntity> getAllBook(int pageNumber, int pageSize, String sortBy) {
		    Session session = sessionFactory.getCurrentSession();

		    // Câu truy vấn cơ bản để lấy tất cả sách
		    String hql = "FROM BooksEntity b ";

		    // Thêm phần sắp xếp dựa trên tham số sortBy
		    if (sortBy != null && !sortBy.isEmpty()) {
		        switch (sortBy) {
		            case "nameAsc":
		                hql += "ORDER BY b.title ASC"; // Sắp xếp theo tên sách A-Z
		                break;
		            case "nameDesc":
		                hql += "ORDER BY b.title DESC"; // Sắp xếp theo tên sách Z-A
		                break;
		            case "priceAsc":
		                hql += "ORDER BY b.price ASC"; // Sắp xếp theo giá tăng dần
		                break;
		            case "priceDesc":
		                hql += "ORDER BY b.price DESC"; // Sắp xếp theo giá giảm dần
		                break;
		            case "newest":
		                hql += "ORDER BY b.createdAt DESC"; // Sắp xếp theo ngày thêm mới nhất
		                break;
		            case "oldest":
		                hql += "ORDER BY b.createdAt ASC"; // Sắp xếp theo ngày thêm cũ nhất
		                break;
		            default:
		                hql += "ORDER BY b.createdAt DESC"; // Mặc định sắp xếp theo ngày thêm mới nhất
		                break;
		        }
		    } else {
		        // Mặc định nếu không có sortBy, sắp xếp theo ngày thêm mới nhất
		        hql += "ORDER BY b.createdAt DESC";
		    }

		    Query query = session.createQuery(hql);

		    // Thiết lập phân trang
		    if (pageNumber < 1) pageNumber = 1;
		    if (pageSize < 1) pageSize = 16; // Giá trị mặc định
		    query.setFirstResult((pageNumber - 1) * pageSize);
		    query.setMaxResults(pageSize);

		    // Trả về danh sách sách đã được sắp xếp và phân trang
		    return query.list();
		}


}

