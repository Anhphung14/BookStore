package bookstore.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Thiết lập mã phản hồi HTTP là 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Thêm thông báo lỗi vào request
        request.setAttribute("errorMessage", "Bạn không có quyền truy cập vào trang này.");

        String refererUrl = request.getHeader("Referer");
        // Chuyển tiếp tới một trang lỗi tùy chỉnh
        request.getRequestDispatcher("/WEB-INF/views/error/403.jsp").forward(request, response);
    }
}
