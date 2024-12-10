package bookstore.DAO;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.RolesEntity;

@Repository
@Transactional
public class RolesDAO {
	 @Autowired
	 private SessionFactory sessionFactory;
	
	public RolesEntity getRoleByName(String roleName) {
		String hql = "FROM RolesEntity r WHERE r.name = :roleName";
	    return (RolesEntity) sessionFactory.getCurrentSession()
	                                       .createQuery(hql)
	                                       .setParameter("roleName", roleName)
	                                       .uniqueResult();
	}
}
