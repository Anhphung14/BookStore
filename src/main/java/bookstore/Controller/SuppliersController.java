package bookstore.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.Service.SuppliersService;
import bookstore.Entity.SuppliersEntity;

@Controller
@Transactional
public class SuppliersController {

	@Autowired
	private SuppliersService suppliersService;

	@Autowired
	private SessionFactory factory;

	@RequestMapping(value = "/suppliers")
	public String providers(Model model, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "search", required = false) String search) {
	    Session session = factory.getCurrentSession();
	    
	    String hql = "FROM SuppliersEntity c";
	    String countQuery = "SELECT count(c) FROM SuppliersEntity c";
	    
	    if (search != null && !search.isEmpty()) {
	        hql += " WHERE c.name LIKE :search";
	        countQuery += " WHERE c.name LIKE :search";
	    }
	    
	    // Tính tổng số bản ghi
	    Query countQ = session.createQuery(countQuery);
	    if (search != null && !search.isEmpty()) {
	        countQ.setParameter("search", "%" + search + "%");
	    }
	    Long count = (Long) countQ.uniqueResult();
	    int totalPages = (int) Math.ceil((double) count / size);
	    
	    // Lấy danh sách theo trang
	    Query query = session.createQuery(hql);
	    if (search != null && !search.isEmpty()) {
	        query.setParameter("search", "%" + search + "%");
	    }
	    query.setFirstResult((page - 1) * size);
	    query.setMaxResults(size);
	    
	    List<SuppliersEntity> suppliers = query.list();

		model.addAttribute("suppliers", suppliers);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("search", search); 
		return "suppliers/index";
	}

	public SuppliersEntity getSupplierById(Long id) {
		Session session = factory.getCurrentSession();
		SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, id);

		return supplier;
	}

	@RequestMapping(value = "supplier/new", method = RequestMethod.GET)
	public String newSupplier(ModelMap model) {
		model.addAttribute("supplier", new SuppliersEntity());
		model.addAttribute("task", "new");
		return "suppliers/edit";
	}

	@RequestMapping(value = "/supplier/edit/{id}", method = RequestMethod.GET)
	public String supplierEdit(@PathVariable("id") Long id, ModelMap model) {
		SuppliersEntity supplier = getSupplierById(id);
		model.addAttribute("supplier", supplier);
		model.addAttribute("task", "edit");
		return "suppliers/edit";
	}

	@RequestMapping(value = "/supplier/save.htm", method = RequestMethod.POST)
	public String saveSupplier(@ModelAttribute("supplier") SuppliersEntity supplier, @RequestParam("task") String task,
			@RequestParam(value = "id", required = false) Long id, ModelMap model) {
		Session session = factory.getCurrentSession();
		try {
			// If task is 'new', create a new supplier
			if ("new".equals(task)) {
				LocalDateTime now = LocalDateTime.now();
				Timestamp currentDate = Timestamp.valueOf(now);

				supplier.setCreatedAt(currentDate);
				supplier.setUpdatedAt(currentDate);
				session.save(supplier);
			}
			// If task is 'edit', update an existing supplier
			else if ("edit".equals(task)) {
				SuppliersEntity existingSupplier = (SuppliersEntity) session.get(SuppliersEntity.class, id);
				if (existingSupplier != null) {
					existingSupplier.setName(supplier.getName());
					existingSupplier.setContactPerson(supplier.getContactPerson());
					existingSupplier.setEmail(supplier.getEmail());
					existingSupplier.setPhone(supplier.getPhone());
					existingSupplier.setAddress(supplier.getAddress());
					Date currentDate = new Date(System.currentTimeMillis());
					existingSupplier.setUpdatedAt(currentDate);
					session.update(existingSupplier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "An error occurred: " + e.getMessage());
		}
		return "redirect:/suppliers.htm";
	}

	@RequestMapping(value = "/supplier/delete/{id}.htm", method = RequestMethod.GET)
	public String deleteRole(@PathVariable("id") Long id) {
		Session session = factory.getCurrentSession();
		SuppliersEntity supplier = (SuppliersEntity) session.get(SuppliersEntity.class, id);
		if (supplier != null) {
			session.delete(supplier);
		}
		return "redirect:/suppliers.htm";
	}

}
