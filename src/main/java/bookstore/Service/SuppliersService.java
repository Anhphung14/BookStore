package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.SuppliersDAO;
import bookstore.Entity.SuppliersEntity;

@Service
public class SuppliersService {
	@Autowired
	SuppliersDAO suppliersDAO;
	
	public List<SuppliersEntity> getAllSuppliers() {
		return suppliersDAO.getAllSuppliers();
	}
}
