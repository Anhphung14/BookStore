package bookstore.DAO;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.*;


@Repository
@Transactional
public class DiscountsDAO {
	@Autowired
    private SessionFactory sessionFactory;
	@Autowired
    private BooksDAO booksDAO;
	
	public List<DiscountsEntity> getAllDiscounts(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity";
		Query query = session.createQuery(hql);
		List<DiscountsEntity> allDiscounts = query.list();
		return allDiscounts;
	}
	
	public void updateStatusDiscounts() {
		Session session = sessionFactory.getCurrentSession();
	    // Khởi tạo đối tượng Timestamp cho ngày hiện tại
	    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
	    
	    List<DiscountsEntity> allDiscounts = getAllDiscounts();
	    
	    for (DiscountsEntity discount : allDiscounts) {
	        // Kiểm tra nếu ngày kết thúc của discount nhỏ hơn currentDate
	        if (discount.getEndDate() != null && discount.getEndDate().before(currentDate)) {
	            // Cập nhật trạng thái thành 'expired'
	            discount.setStatus("expired");
	            
	            // Lưu lại thay đổi trong session
	            session.update(discount);
	        }
	    }
	    session.flush();
	}
	
	public DiscountsEntity findDiscountById(Long discount_id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity where id = :discount_id";
		Query query = session.createQuery(hql);
		query.setParameter("discount_id", discount_id);
		DiscountsEntity discount = (DiscountsEntity) query.uniqueResult();
		return discount;
	}
	
	public boolean checkDiscountCodeExist(String code) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From DiscountsEntity where code = :code";
		Query query = session.createQuery(hql);
		query.setParameter("code", code);
		DiscountsEntity discount = (DiscountsEntity) query.uniqueResult();
		System.out.println("discount: " + discount);
		if(discount == null) {
			//System.out.println("Mã giảm giá này chưa tồn tại");
			return false;
		}else {
			//System.out.println("Đã tồn tại");
			return true;
		}
	}
	
	public String createNewDiscount(DiscountsEntity newDiscount, List<Long> subcategories_id) {
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        // Kiểm tra nếu subcategory đã có discount active
	        if ("categories".equals(newDiscount.getApplyTo())) {
	            for (Long subcategoryId : subcategories_id) {
	                boolean hasActiveDiscount = checkIfSubcategoryHasActiveDiscount(subcategoryId, newDiscount.getStartDate(), newDiscount.getEndDate());
	                
	                if (hasActiveDiscount) {
	                    // Nếu subcategory đã có discount active trong thời gian này, throw exception hoặc return lỗi
	                    return "This subcategory already has an active discount during this period";
	                }
	                
	                // Lấy danh sách sách của mỗi subcategory
	                List<BooksEntity> books = booksDAO.getBooksBySubcategory(subcategoryId);
	                
	                // Thêm các sách vào bảng Book_Discount
	                for (BooksEntity book : books) {
	                    Book_DiscountsEntity bookDiscount = new Book_DiscountsEntity();
	                    bookDiscount.setDiscount_id(newDiscount);  // Thiết lập discount_id
	                    bookDiscount.setBook_id(book);  // Thiết lập book_id
	                    session.save(bookDiscount);  // Lưu vào bảng Book_Discount
	                }
	            }
	        }

	        // Chỉ khi mọi thứ hợp lệ, mới lưu discount mới
	        session.save(newDiscount);  // Lưu discount mới vào DB

	        return "success"; // Nếu mọi thứ đều ok, trả về thành công
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error"; // Nếu có lỗi, trả về thông báo lỗi
	    }
	}

	// Phương thức kiểm tra nếu subcategory đã có discount active trong thời gian này
	public boolean checkIfSubcategoryHasActiveDiscount(Long subcategoryId, Date startDate, Date endDate) {
	    String hql = "SELECT COUNT(d) FROM Book_DiscountsEntity bde " +
	                 "JOIN bde.discount_id d " +
	                 "WHERE bde.book_id IN (SELECT b.id FROM BooksEntity b WHERE b.subcategoriesEntity.id = :subcategoryId) " +
	                 "AND d.startDate <= :endDate " +
	                 "AND d.endDate >= :startDate " +
	                 "AND d.status = 'active'";

	    Long count = (Long) sessionFactory.getCurrentSession()
	            .createQuery(hql)
	            .setParameter("subcategoryId", subcategoryId)
	            .setParameter("startDate", startDate)
	            .setParameter("endDate", endDate)
	            .uniqueResult();

	    return count > 0;
	}



	
	
	
}
