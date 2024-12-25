package bookstore.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	
	public List<RolesEntity> getListRole() {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "FROM RolesEntity";
		Query query = session.createQuery(hql);
		
		return query.list(); 
	}
	
	public RolesEntity getRoleById(long id) {
		Session session = sessionFactory.getCurrentSession();

		return (RolesEntity) session.get(RolesEntity.class, id);
	}
	
	public boolean updateRole(RolesEntity role) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.merge(role);
			t.commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
		}
		
		return false;
	}
}
