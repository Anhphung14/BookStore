package bookstore.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.SubcategoriesEntity;

@Service
@Transactional
public class SubcategoriesService {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	SubcategoriesDAO subcategoriesDAO;
	
	public List<SubcategoriesEntity> getAllSubcategories() {
		return subcategoriesDAO.findAll();
	}
	
	public List<SubcategoriesEntity> getSubcategoriesByCategoryId(Long categoryId) {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM SubcategoriesEntity WHERE categoriesEntity.id = :categoryId";
		Query query = session.createQuery(hql);
		query.setParameter("categoryId", categoryId);
		
		return query.list();
	}
	
	public CategoriesEntity getCategoryBySubcategoryId(Long id) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT s.categoriesEntity FROM SubcategoriesEntity s WHERE s.id = :subcategoryId";
	    Query query = session.createQuery(hql);
	    query.setParameter("subcategoryId", id);

	    // Trả về Category, hoặc null nếu không tìm thấy
	    return (CategoriesEntity) query.uniqueResult();
	}
	
    public SubcategoriesEntity getSubcategoryBySubcategoryId(Long id) {
    	return subcategoriesDAO.getSubcategoryBySubcategoryId(id);
    }
}
