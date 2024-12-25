package bookstore.security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import bookstore.Entity.UsersEntity;

@Component
public class UpdateRoleAuthorities {
    @Autowired
    UserDetailsService userDetailsService;

    public void updateRoleAuthorities(String roleName, Set<UsersEntity> users) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth == null || !currentAuth.isAuthenticated()) {
            return; // Không có người dùng hiện tại
        }

        String currentEmail = currentAuth.getName();
        UsersEntity currentUser = users.stream()
            .filter(user -> user.getEmail().equals(currentEmail))
            .findFirst()
            .orElse(null);

        if (currentUser != null) {
            // Lấy quyền từ permissions
            List<SimpleGrantedAuthority> permissionAuthorities = currentUser.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName())))
                .collect(Collectors.toList());

            // Lấy quyền từ roles
            List<SimpleGrantedAuthority> roleAuthorities = currentUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

            // Gộp cả permissions và roles
            List<SimpleGrantedAuthority> updatedAuthorities = Stream.concat(
                permissionAuthorities.stream(),
                roleAuthorities.stream()
            ).distinct().collect(Collectors.toList());

            // Tạo đối tượng xác thực mới
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                currentAuth.getPrincipal(), currentAuth.getCredentials(), updatedAuthorities);

            // Cập nhật SecurityContext
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}

