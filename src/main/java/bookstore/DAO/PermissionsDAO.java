package bookstore.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.PermissionsEntity;

@Repository
@Transactional
public class PermissionsDAO {
	@Autowired
    private SessionFactory sessionFactory;
	
	public List<PermissionsEntity> getListPermission() {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "FROM PermissionsEntity";
		Query query = session.createQuery(hql);
		
		List<PermissionsEntity> permissions = query.list();
		permissions.forEach(permission -> Hibernate.initialize(permission.getRoles()));
		
	    return permissions;
	}
	
	public PermissionsEntity getPermissionById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		
		return (PermissionsEntity) session.get(PermissionsEntity.class, id);
	}
}
