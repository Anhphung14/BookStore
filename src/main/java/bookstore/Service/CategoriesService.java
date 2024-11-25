package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.CategoriesDAO;
import bookstore.Entity.CategoriesEntity;

@Service
public class CategoriesService {
	@Autowired
	CategoriesDAO categoriesDAO;
	
	public List<CategoriesEntity> getAllCategories() {
		return categoriesDAO.getAllCategories();
		
	}
}
