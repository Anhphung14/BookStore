package bookstore.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import bookstore.DAO.UserDAO;
import bookstore.Entity.UsersEntity;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserDAO userDAO;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Đăng nhập thành công!");
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UsersEntity user = userDAO.getUserByEmai(userDetails.getUsername());
        
        request.getSession().setAttribute("user", user);
        
     // Lấy danh sách các vai trò của người dùng
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // Kiểm tra vai trò của người dùng và chuyển hướng tương ứng
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/bookstore/admin1337/home.htm");
                return;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/bookstore/index.htm");
                return;
            }
        }
        
        // Nếu không có vai trò nào khớp, chuyển hướng đến trang mặc định
        response.sendRedirect("/index.htm");
    }
}
