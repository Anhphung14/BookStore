package bookstore.security;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	for (var authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                System.out.println("Current Authority: " + role);

                if ("ROLE_ADMIN".equals(role)) {
                    System.out.println("Using admin DataSource");
                    return "admin";
                } else if ("ROLE_STAFF".equals(role)) {
                    System.out.println("Using staff DataSource");
                    return "staff";
                } else if ("ROLE_MANAGER".equals(role)) {
                	System.out.println("Using manager DataSource");
                	return "manager";
                } else if ("ROLE_USER".equals(role)) {
                	System.out.println("Using user DataSource");
                	return "user";
                }
            }
        }
        
        System.out.println("No matching role found. Returning default DataSource.");
        return null; // Mặc định sử dụng DataSource mặc định
    }
}
