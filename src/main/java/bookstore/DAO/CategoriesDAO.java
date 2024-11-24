package bookstore.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.CategoriesEntity;

@Repository
public class CategoriesDAO {
	@Autowired
	SessionFactory factory;
	
	public List<CategoriesEntity> getAllCategories() {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM CategoriesEntity";
		Query query =  session.createQuery(hql);
		List<CategoriesEntity> listCategories =  query.list();
		
		return listCategories;
		
	}
}
