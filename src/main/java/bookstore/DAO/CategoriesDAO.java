package bookstore.DAO;

import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.SubcategoriesEntity;

@Repository
@Transactional
public class CategoriesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Lấy danh sách tất cả danh mục
    public List<CategoriesEntity> findAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM CategoriesEntity";
        Query query = session.createQuery(hql);
        List<CategoriesEntity> list = query.list();
        

		/* join bảng bằng liên kết xong lấy giá trị 
		 * for (Category category : list) { System.out.println("Category: " +
		 * category.getName()); for (Subcategory subcategory :
		 * category.getSubcategories()) { System.out.println("    Subcategory: " +
		 * subcategory.getName()); } }
		 */
        return list;
    }
    
    public CategoriesEntity findCategoryById(Long id) {
	    Session session = sessionFactory.getCurrentSession();
	    String hql = "FROM CategoriesEntity WHERE id = :id";
	    Query query = session.createQuery(hql);
	    query.setParameter("id", id);
	    return (CategoriesEntity) query.uniqueResult();
	}
    
    public List<CategoriesEntity> getAllCategories() {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "FROM CategoriesEntity";
		Query query =  session.createQuery(hql);
		List<CategoriesEntity> listCategories =  query.list();
		
		return listCategories;
		
	}

}
