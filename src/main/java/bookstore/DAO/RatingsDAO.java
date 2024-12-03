package bookstore.DAO;

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
import bookstore.Entity.OrdersEntity;
import bookstore.Entity.RatingsEntity;
import bookstore.Entity.UsersEntity;

@Transactional
@Repository
public class RatingsDAO {

    @Autowired
    private SessionFactory factory;

    // Lấy danh sách đánh giá theo User ID
    public List<RatingsEntity> getRatingsByUserId(Long userId) {
        Session session = factory.getCurrentSession();
        String hql = "FROM RatingsEntity WHERE user.id = :userId";
        return session.createQuery(hql).setParameter("userId", userId).list();
    }

    // Lấy danh sách đánh giá theo Book ID
    public List<RatingsEntity> getRatingsByBookId(Long bookId) {
        Session session = factory.getCurrentSession();
        String hql = "FROM RatingsEntity WHERE book.id = :bookId";
        return session.createQuery(hql).setParameter("bookId", bookId) .list();
    }

    // Lấy tất cả đánh giá
    public List<RatingsEntity> getAllRatings() {
        Session session = factory.getCurrentSession();
        return session.createQuery("FROM RatingsEntity").list();
    }

    // Lấy các đơn hàng đã được đánh giá
    public List<Long> getRatedOrderIdsByUserId(Long userId) {
        Session session = factory.getCurrentSession();
        // Truy vấn lấy tất cả các mã đơn hàng đã có đánh giá từ bảng RatingsEntity
        List<Long> orderIds = session.createQuery("SELECT DISTINCT r.order.id FROM RatingsEntity r WHERE r.user.id = :userId")
                .setParameter("userId", userId)
                .list();
        return orderIds;
    }
    
    // Lấy đánh giá theo ID
    public RatingsEntity getRatingById(Long ratingId) {
        Session session = factory.getCurrentSession();
        return (RatingsEntity) session.get(RatingsEntity.class, ratingId);
    }
    
    public Double getAverageRatingByBookId(Long bookId) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT AVG(r.number) FROM RatingsEntity r WHERE r.book.id = :bookId";
        Double averageRating = (Double) session.createQuery(hql)
                .setParameter("bookId", bookId)
                .uniqueResult();
        return averageRating != null ? averageRating : 0.0;
    }
    
    public int addRating(UsersEntity user, BooksEntity book, OrdersEntity order, int ratingNumber, String reviewContent) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        int isAdded = 0;
        try {
            // Tạo đối tượng RatingsEntity mới
            RatingsEntity newRating = new RatingsEntity();
            newRating.setUser(user);
            newRating.setBook(book);
            newRating.setOrder(order);
            newRating.setNumber(ratingNumber);
            newRating.setStatus(0); // Trạng thái là 0 (đánh giá chờ duyệt)
            newRating.setContent(reviewContent);
            newRating.setCreatedAt(new Date());
            newRating.setUpdatedAt(new Date());
            
            // Lưu RatingEntity vào cơ sở dữ liệu
            session.save(newRating);
            t.commit();
            isAdded = 1;
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
        } finally {
            session.close();
        }
        return isAdded;
    }

    
    public List<RatingsEntity> getRatingsByStatus0() {
        Session session = factory.getCurrentSession();
        String hql = "FROM RatingsEntity WHERE status = 0";
        return session.createQuery(hql).list();
    }

    // Phương thức lấy tất cả đánh giá với status = 1
    public List<RatingsEntity> getRatingsByStatus1() {
        Session session = factory.getCurrentSession();
        String hql = "FROM RatingsEntity WHERE status = 1";
        return session.createQuery(hql).list();
    }

    // Phương thức lấy tất cả đánh giá với status = 2
    public List<RatingsEntity> getRatingsByStatus2() {
        Session session = factory.getCurrentSession();
        String hql = "FROM RatingsEntity WHERE status = 2";
        return session.createQuery(hql).list();
    }

    public int updateStatus(Long ratingId, int status) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        int isUpdated = 0;
        
        try {
            String hql = "UPDATE RatingsEntity r SET r.status = :status WHERE r.id = :ratingId";
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            query.setParameter("ratingId", ratingId);
            
            int result = query.executeUpdate();
            if (result > 0) {
                isUpdated = 1;
            }
            
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
        } finally {
            session.close();
        }
        
        return isUpdated;
    }
    
    public List<RatingsEntity> searchRatingsByUser(String fullname, Integer status) {
        Session session = factory.openSession();
        List<RatingsEntity> ratings = null;
        
        try {
            // Chuẩn bị câu truy vấn HQL với điều kiện fullname và status (nếu có)
            String hql = "FROM RatingsEntity r WHERE r.user.fullname LIKE :fullname";
            
            if (status != null) {
                hql += " AND r.status = :status";  // Thêm điều kiện trạng thái nếu status khác null
            }
            
            Query query = session.createQuery(hql);
            query.setParameter("fullname", "%" + fullname + "%");
            
            if (status != null) {
                query.setParameter("status", status);
            }
            
            ratings = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return ratings;
    }
    
    public List<RatingsEntity> searchRatingsByBook(String title, Integer status) {
        Session session = factory.openSession();
        List<RatingsEntity> ratings = null;
        
        try {
            // Chuẩn bị câu truy vấn HQL với điều kiện title và status (nếu có)
            String hql = "FROM RatingsEntity r WHERE r.book.title LIKE :title";
            
            if (status != null) {
                hql += " AND r.status = :status";  // Thêm điều kiện trạng thái nếu status khác null
            }
            
            Query query = session.createQuery(hql);
            query.setParameter("title", "%" + title + "%");
            
            if (status != null) {
                query.setParameter("status", status);
            }
            
            ratings = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return ratings;
    }


}