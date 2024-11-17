package bookstore.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bookstore.Entity.UsersEntity;

@Transactional
@Controller
public class UsersController {
	
	@Autowired
	SessionFactory factory;

    @RequestMapping(value = "/users")
    public String users(ModelMap model) {
    	List<UsersEntity> users = ListUsers();
    	
    	model.addAttribute("users", users);
        return "users/index";  
    }


    @RequestMapping(value = "/user/edit/{id}")
    public String userEdit(@PathVariable("id") Long id) {
      
        // userService.getUserById(id) để lấy dữ liệu người dùng từ DB
        return "users/userEdit";  
    }

    @RequestMapping(value = "/user/new")
    public String userNew() {
        return "users/userEdit";  
    }
    
    
    public List<UsersEntity> ListUsers() {
    	Session session = factory.getCurrentSession();
    	String hql = "FROM UsersEntity";
    	Query query = session.createQuery(hql);
    	List<UsersEntity> listUsers = query.list();
    	
    	return listUsers;
    }
}
