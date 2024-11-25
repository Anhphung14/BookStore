package bookstore.DAO;

import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.Category;
import bookstore.Entity.Subcategory;

@Repository
@Transactional
public class CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Lấy danh sách tất cả danh mục
    public List<Category> findAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Category";
        Query query = session.createQuery(hql);
        List<Category> list = query.list();
        

		/* join bảng bằng liên kết xong lấy giá trị 
		 * for (Category category : list) { System.out.println("Category: " +
		 * category.getName()); for (Subcategory subcategory :
		 * category.getSubcategories()) { System.out.println("    Subcategory: " +
		 * subcategory.getName()); } }
		 */
        return list;
    }
    
    public Category findCategoryById(Long id) {
	    Session session = sessionFactory.getCurrentSession();
	    String hql = "FROM Category WHERE id = :id";
	    Query query = session.createQuery(hql);
	    query.setParameter("id", id);
	    return (Category) query.uniqueResult();
	}

}
