package bookstore.Controller;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@Autowired
	SessionFactory factory;
	
	@Transactional
	@RequestMapping(value = "/home")
	public String home(ModelMap model) {
//		model.addAttribute("list", this.getUsers());
		return "home";
	}

//	public List<UsersEntity> getUsers() {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM UsersEntity";
//		Query query = session.createQuery(hql);
//
//		List<UsersEntity> list = query.list();
//
//		return list;
//	}
//
//	public UsersEntity getUserByEmailPass(String email, String password) {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM UsersEntity WHERE email = " + email + " AND password = " + password;
//		Query query = session.createQuery(hql);
//		UsersEntity user = (UsersEntity) query.uniqueResult();
//
//		return user;
//	}
	
}
