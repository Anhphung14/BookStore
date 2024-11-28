package bookstore.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.CategoriesDAO;
import bookstore.Entity.CategoriesEntity;

@Service
@Transactional
public class CategoriesService {

    @Autowired
    private CategoriesDAO categoriesDAO;

    public List<CategoriesEntity> getAllCategoriesWithSubcategories() {
        List<CategoriesEntity> categories = categoriesDAO.getAllCategories();
        
        for (CategoriesEntity category : categories) {
            category.getSubcategoriesEntity().size();
        }
        
        return categories;
    }
    
    public List<CategoriesEntity> getAllCategories() {
        return categoriesDAO.getAllCategories();
    }
}