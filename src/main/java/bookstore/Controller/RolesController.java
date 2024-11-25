package bookstore.Controller;

import java.util.List;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;

@Transactional
@Controller
public class RolesController {
	@Autowired
	private SessionFactory factory;

	@SuppressWarnings("unchecked")
	private List<RolesEntity> ListRoles() {
		Session session = factory.getCurrentSession();
		String hql = "FROM RolesEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String roles(ModelMap model) {
	    List<RolesEntity> roles = ListRoles();

	    for (RolesEntity role : roles) {
	        long userCount = countUsersByRoleId(role.getId());
	        role.setUserCount(userCount); 
	    }

	    model.addAttribute("roles", roles);
	    return "roles/index";
	}

	private long countUsersByRoleId(Long role_id) {
	    Session session = factory.getCurrentSession();
	    Query query = session.createQuery("SELECT COUNT(u) \r\n"
	    		+ "FROM bookstore.Entity.UsersEntity u \r\n"
	    		+ "JOIN u.roles r \r\n"
	    		+ "WHERE r.id = :role_id\r\n"
	    		+ "");
	    query.setParameter("role_id", role_id);
	    return (long) query.uniqueResult(); 
	}


	private RolesEntity getRoleById(Long id) {
		Session session = factory.getCurrentSession();
		return (RolesEntity) session.get(RolesEntity.class, id);
	}

	@RequestMapping(value = "/role/edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable("id") Long id, ModelMap model) {
		RolesEntity role = getRoleById(id);
		model.addAttribute("role", role);
		model.addAttribute("task", "edit");
		return "roles/edit";
	}

	@RequestMapping(value = "role/new", method = RequestMethod.GET)
	public String newRole(ModelMap model) {
		model.addAttribute("role", new RolesEntity());
		model.addAttribute("task", "new");
		return "roles/edit";
	}

	@RequestMapping(value = "/role/save.htm", method = RequestMethod.POST)
	public String saveRole(@ModelAttribute("role") RolesEntity role, @RequestParam("task") String task,
	        @RequestParam(value = "id", required = false) Long id, ModelMap model) {
	    Session session = factory.getCurrentSession();

	    try {
	        if ("new".equals(task)) {
	            RolesEntity existingRole = getRoleByName(role.getName());
	            if (existingRole != null) {
	                model.addAttribute("message", "Role name already exists.");
	                return "roles/edit";
	            }

	            try {
	                LocalDateTime now = LocalDateTime.now();
	                Timestamp currentDate = Timestamp.valueOf(now);
	                role.setCreatedAt(currentDate);
	                role.setUpdatedAt(currentDate);

	                session.save(role);

	            } catch (Exception e) {
	                e.printStackTrace();
	                model.addAttribute("message", "Failed to save new role: " + e.getMessage());
	                return "roles/edit";
	            }
	        }
	        else if ("edit".equals(task)) {
	            if (id == null) {
	                model.addAttribute("message", "Role ID is required to edit.");
	                return "redirect:/roles.htm"; 
	            }

	            RolesEntity existingRole = getRoleById(id);

	            if (existingRole == null) {
	                model.addAttribute("message", "Role not found.");
	                return "redirect:/roles.htm";
	            }

	            existingRole.setName(role.getName());
	            existingRole.setDescription(role.getDescription());  
	            Date currentDate = new Date(System.currentTimeMillis());
	            existingRole.setUpdatedAt(currentDate);

	            session.merge(existingRole);
	            return "redirect:/roles.htm";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "An error occurred: " + e.getMessage());
	        return "redirect:/roles.htm";
	    }

	    return "redirect:/roles.htm";
	}

	@RequestMapping(value = "/role/delete/{id}.htm", method = RequestMethod.GET)
	public String deleteRole(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		RolesEntity role = (RolesEntity) session.get(RolesEntity.class, id);
		if (role != null) {
			session.delete(role);
		}
		return "redirect:/roles.htm";
	}

	private RolesEntity getRoleByName(String name) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("from RolesEntity where name = :name");
		query.setParameter("name", name);
		return (RolesEntity) query.uniqueResult();
	}
}
