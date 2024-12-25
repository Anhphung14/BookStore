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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Utils.EscapeHtmlUtil;

@Transactional
@Controller
@RequestMapping("/admin1337/")
public class RolesController {
	@Autowired
	private SessionFactory factory;

	@SuppressWarnings("unchecked")
	private List<RolesEntity> ListRoles(int page, int size, String search) {
	    Session session = factory.getCurrentSession();
	    String hql = "FROM RolesEntity r";
	    
	    if (search != null && !search.isEmpty()) {
	        hql += " WHERE r.name LIKE :search";
	    }
	    
	    Query query = session.createQuery(hql);
	    
	    if (search != null && !search.isEmpty()) {
	        query.setParameter("search", "%" + search + "%");
	    }
	    
	    query.setFirstResult((page - 1) * size);
	    query.setMaxResults(size);
	    
	    return query.list();
	}
	
	@PreAuthorize("hasAuthority('VIEW_ROLE')")
	@RequestMapping(value = "/roles")
	public String roles(ModelMap model, 
	                    @RequestParam(value = "page", defaultValue = "1") int page,
	                    @RequestParam(value = "size", defaultValue = "10") int size,
	                    @RequestParam(value = "search", required = false) String search) {
	    
	    List<RolesEntity> roles = ListRoles(page, size, search);
	    
	    Session session = factory.getCurrentSession();
	    String countQuery = "SELECT count(r) FROM RolesEntity r";
	    
	    if (search != null && !search.isEmpty()) {
	        countQuery += " WHERE r.name LIKE :search";
	    }

	    Query queryCount = session.createQuery(countQuery);
	    
	    if (search != null && !search.isEmpty()) {
	        queryCount.setParameter("search", "%" + search + "%");
	    }
	    
	    Long count = (Long) queryCount.uniqueResult();
	    int totalPages = (int) Math.ceil((double) count / size);
	    
	    for (RolesEntity role : roles) {
	        long userCount = countUsersByRoleId(role.getId());
	        role.setUserCount(userCount); 
	    }
	    
	    model.addAttribute("roles", roles);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("size", size);
	    model.addAttribute("search", search); 

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
	
	@PreAuthorize("hasAuthority('UPDATE_ROLE')")
	@RequestMapping(value = "/role/edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable("id") String idd, ModelMap model) {
		if (!idd.matches("\\d+")) {
	    	return "redirect:/admin1337/roles";
	    }
	    
		Long id = Long.parseLong(idd);
		RolesEntity role = getRoleById(id);
		if(role == null) {
			return "redirect:/admin1337/roles";
		}
		
		model.addAttribute("role", role);
		model.addAttribute("task", "edit");
		return "roles/edit";
	}

	@PreAuthorize("hasAuthority('ADD_ROLE')")
	@RequestMapping(value = "role/new", method = RequestMethod.GET)
	public String newRole(ModelMap model) {
		model.addAttribute("role", new RolesEntity());
		model.addAttribute("task", "new");
		return "roles/edit";
	}
	
	@PreAuthorize("hasAuthority('UPDATE_ROLE') or hasAuthority('ADD_ROLE')")
	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public String saveRole(@ModelAttribute("role") RolesEntity role, @RequestParam("task") String task,
	        @RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAttributes, ModelMap model) {
	    Session session = factory.getCurrentSession();
	    role.setDescription(EscapeHtmlUtil.encodeHtml(role.getDescription()));
	    role.setName(EscapeHtmlUtil.encodeHtml(role.getName()));
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean hasUpdateAuthority = auth.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("UPDATE_SUPPLIER"));
	    boolean hasAddAuthority = auth.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADD_SUPPLIER"));
	    
	    
	    try {
	        if ("new".equals(task) && hasAddAuthority) {
	            RolesEntity existingRole = getRoleByName(role.getName());
	            if (existingRole != null) {
	                redirectAttributes.addFlashAttribute("alertMessage", "Role name already exists.");
	    	        redirectAttributes.addFlashAttribute("alertType", "error");
	                return "roles/edit";
	            }

	            try {
	                LocalDateTime now = LocalDateTime.now();
	                Timestamp currentDate = Timestamp.valueOf(now);
	                role.setCreatedAt(currentDate);
	                role.setUpdatedAt(currentDate);

	                session.save(role);
	                redirectAttributes.addFlashAttribute("alertMessage", "Role saved successfully!");
		            redirectAttributes.addFlashAttribute("alertType", "success");

	            } catch (Exception e) {
	                e.printStackTrace();
	                model.addAttribute("message", "Failed to save new role: " + e.getMessage());
	                return "roles/edit";
	            }
	        }
	        else if ("edit".equals(task) && hasUpdateAuthority) {
	            if (id == null) {
	                model.addAttribute("message", "Role ID is required to edit.");
	                return "redirect:/admin1337/roles"; 
	            }

	            RolesEntity existingRole = getRoleById(id);

	            if (existingRole == null) {
	                model.addAttribute("message", "Role not found.");
	                return "redirect:/admin1337/roles";
	            }

	            existingRole.setName(role.getName());
	            existingRole.setDescription(role.getDescription());  
	            Date currentDate = new Date(System.currentTimeMillis());
	            existingRole.setUpdatedAt(currentDate);

	            session.merge(existingRole);
                redirectAttributes.addFlashAttribute("alertMessage", "Role saved successfully!");
	            redirectAttributes.addFlashAttribute("alertType", "success");
	            return "redirect:/admin1337/roles";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("alertMessage", "Error occurred while saving the Role.");
	        redirectAttributes.addFlashAttribute("alertType", "error");
	        return "redirect:/admin1337/roles";
	    }

	    return "redirect:/admin1337/roles";
	}

	@RequestMapping(value = "/role/delete/{id}", method = RequestMethod.GET)
	public String deleteRole(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		RolesEntity role = (RolesEntity) session.get(RolesEntity.class, id);
		if (role != null) {
			session.delete(role);
		}
		return "redirect:/admin1337/roles";
	}

	private RolesEntity getRoleByName(String name) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("from RolesEntity where name = :name");
		query.setParameter("name", name);
		return (RolesEntity) query.uniqueResult();
	}
}
