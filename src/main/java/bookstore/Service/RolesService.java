package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.RolesDAO;
import bookstore.Entity.RolesEntity;

@Service
public class RolesService {
	
	@Autowired
	RolesDAO rolesDAO;
	
	public List<RolesEntity> getListRole() {
		 return rolesDAO.getListRole();
	}
	
	public RolesEntity getRoleById(long id) {
		return rolesDAO.getRoleById(id);
	}
	
	public boolean updateRole(RolesEntity role) {
		return rolesDAO.updateRole(role);
	}
}
