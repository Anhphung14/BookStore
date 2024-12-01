package bookstore.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.SuppliersEntity;

@Repository
public class SuppliersDAO {
	@Autowired
	SessionFactory factory;
	
	public List<SuppliersEntity> getAllSuppliers() {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM SuppliersEntity";
		Query query = session.createQuery(hql);
		List<SuppliersEntity> listSuppliers = query.list();
		
		return listSuppliers;
	}
	
	public SuppliersEntity getSupplierBySupplierId(Long id) {
		Session session = factory.getCurrentSession();
		
		String hql = "FROM SuppliersEntity WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		
		return (SuppliersEntity) query.uniqueResult();
	}
}
