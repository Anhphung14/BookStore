package bookstore.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bookstore.Entity.PermissionsEntity;
import bookstore.Entity.RolesEntity;
import bookstore.Service.PermissionsService;
import bookstore.Service.RolesService;
import bookstore.security.UpdateRoleAuthorities;

@Controller
@RequestMapping("/admin1337")
public class PermissionsController {
	
	@Autowired
	PermissionsService permissionsService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UpdateRoleAuthorities updateRoleAuthorities;

	@PreAuthorize("hasAuthority('VIEW_PERMISSION')")
    @RequestMapping(value = "/permissions")
    public String permissions(ModelMap model) {
    	
    	List<PermissionsEntity> allPermissions = permissionsService.getListPermission();
    	
    	model.addAttribute("roles", rolesService.getListRole());
    	model.addAttribute("permissions", permissionsService.getListPermission());
    	
    	List<PermissionsEntity> supplierPermissions = new ArrayList<>();
    	List<PermissionsEntity> categoryPermissions = new ArrayList<>();
    	List<PermissionsEntity> productyPermissions = new ArrayList<>();
    	List<PermissionsEntity> discountPermissions = new ArrayList<>();
    	List<PermissionsEntity> orderPermissions = new ArrayList<>();
    	List<PermissionsEntity> inventoryPermissions = new ArrayList<>();
    	List<PermissionsEntity> ratingPermissions = new ArrayList<>();
    	List<PermissionsEntity> userPermissions = new ArrayList<>();
    	List<PermissionsEntity> rolePermissions = new ArrayList<>();
    	List<PermissionsEntity> permissionPermissions = new ArrayList<>();
    	
	    for (PermissionsEntity permission : allPermissions) {
	    	if (permission.getName().toUpperCase().contains("SUPPLIER")) {
	    		supplierPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("CATEGORY")) {
	    		categoryPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("PRODUCT")) {
	    		productyPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("DISCOUNT")) {
	    		discountPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("ORDER")) {
	    		orderPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("INVENTORY")) {
	            inventoryPermissions.add(permission);
	        }
	    	
	    	if (permission.getName().toUpperCase().contains("RATING")) {
	    		ratingPermissions.add(permission);
	        }
	    	
	        if (permission.getName().toUpperCase().contains("USER")) {
	        	userPermissions.add(permission);
	        }
	        
	        if (permission.getName().toUpperCase().contains("ROLE")) {
	        	rolePermissions.add(permission);
	        }
	        
	        if (permission.getName().toUpperCase().contains("PERMISSION")) {
	        	permissionPermissions.add(permission);
	        }
	    }
	    
	    model.addAttribute("supplierPermissions", supplierPermissions);
	    model.addAttribute("categoryPermissions", categoryPermissions);
	    model.addAttribute("productyPermissions", productyPermissions);
	    model.addAttribute("discountPermissions", discountPermissions);
	    model.addAttribute("orderPermissions", orderPermissions);
	    model.addAttribute("inventoryPermissions", inventoryPermissions);
	    model.addAttribute("ratingPermissions", ratingPermissions);
	    model.addAttribute("userPermissions", userPermissions);
	    model.addAttribute("rolePermissions", rolePermissions);
	    model.addAttribute("permissionPermissions", permissionPermissions);
	    
    	
        return "permissions/index";  
    }
    
    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    @RequestMapping("/permission/edit/{id}.htm")
    public String editPermission(@PathVariable("id") Long id, ModelMap model) {
    	RolesEntity role = rolesService.getRoleById(id);
    	
    	model.addAttribute("role", role);
    	model.addAttribute("permissions", permissionsService.getListPermission());
    	
//    	System.out.println(role.getPermissions().getClass().getName());
//    	System.out.println(permissionsService.getListPermission().getClass().getName());
    	return "permissions/edit";
    }
    
    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    @RequestMapping(value = "/permission/edit", method = RequestMethod.POST)
    public String edit(@RequestParam("permissionIds") Set<Long> permissionIds, @RequestParam("id") Long roleId,
    		ModelMap model, RedirectAttributes redirectAttributes) {
    	RolesEntity role = rolesService.getRoleById(roleId);
    	
    	if (permissionIds != null) {
    		Set<PermissionsEntity> permissions = new HashSet<>();
    		
    		for (Long perId : permissionIds) {
    			PermissionsEntity permission = permissionsService.getPermissionById(perId);
    			
    			if (permission != null) {
    				permissions.add(permission);
    			}
    		}
    		
    		role.setPermissions(permissions);
    	} else {
    		role.setPermissions(new HashSet<>());
    	}
    	
    	boolean is_update = rolesService.updateRole(role);
    	
    	if (is_update) {
    		updateRoleAuthorities.updateRoleAuthorities(role.getName(), role.getUsers());
    		
    		redirectAttributes.addFlashAttribute("alertMessage", "Successfully updated Permission!");
			redirectAttributes.addFlashAttribute("alertType", "success");
			return "redirect:/admin1337/permissions.htm"; 
    	}
    	
    	model.addAttribute("alertMessage", "An error occurred while updating the Permission");
		model.addAttribute("alertType", "error");
    	
    	return "permissions/edit"; 
    }
}
