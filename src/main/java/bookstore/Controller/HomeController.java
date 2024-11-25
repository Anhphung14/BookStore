package bookstore.Controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

import bookstore.Entity.UsersEntity;

@Controller
@Transactional
public class HomeController {
	@Autowired
	SessionFactory factory;
	

	@RequestMapping(value = "/home")
	public String home(ModelMap model, HttpSession session) {
//		model.addAttribute("list", this.getUsers());
		UsersEntity user_session = (UsersEntity) session.getAttribute("user");
		
		if (user_session == null) {
			return "redirect:/signin.htm";
		}
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
