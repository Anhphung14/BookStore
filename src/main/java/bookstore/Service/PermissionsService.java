package bookstore.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.DAO.BooksDAO;
import bookstore.DAO.PermissionsDAO;
import bookstore.Entity.PermissionsEntity;

@Service
public class PermissionsService {
	@Autowired
	PermissionsDAO permissionDAO;
	
	public List<PermissionsEntity> getListPermission() {
		List<PermissionsEntity> permissions = permissionDAO.getListPermission();
		
		return permissions;
	}
	
	public PermissionsEntity getPermissionById(Long id) {
		return permissionDAO.getPermissionById(id);
	}
}
