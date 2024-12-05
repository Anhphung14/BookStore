package bookstore.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Kiểm tra nếu người dùng đã đăng nhập và có thông tin trong SecurityContext
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String role = authentication.getAuthorities().toString(); // Lấy vai trò của người dùng
            // Nếu người dùng có vai trò USER, chuyển hướng về trang index.htm
            if (role.contains("ROLE_USER")) {
                response.sendRedirect("/bookstore/index.htm");
            } else {
                // Nếu không phải USER (ví dụ ADMIN), chuyển hướng về signin.htm hoặc trang khác tùy thích
                response.sendRedirect("/bookstore/signin.htm");
            }
        } else {
            // Nếu không có người dùng (đăng xuất chưa có session), chuyển về signin.htm
            response.sendRedirect("/bookstore/signin.htm");
        }
    }
}
