package bookstore.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import bookstore.Entity.SubcategoriesEntity;

@Repository
@Transactional
public class SubcategoryDAO {
	@Autowired
    private SessionFactory sessionFactory;

    // Lấy danh sách tất cả danh mục
    
    public List<SubcategoriesEntity> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM SubcategoriesEntity";
        Query query = session.createQuery(hql);
        List<SubcategoriesEntity> list = query.list();
        return list;
    }
}
