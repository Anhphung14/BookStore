package bookstore.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import bookstore.DAO.UserDAO;
import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserDAO userDao;
	
//	@Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		UsersEntity user = userDao.getUserByEmai(email);
//		
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with email: " + email);
//		} 
//		
//		// Lấy quyền hạn từ cơ sở dữ liệu
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//            .map(role -> new SimpleGrantedAuthority(role.getName()))
//            .collect(Collectors.toList());
//        
//        return new User(user.getEmail(), user.getPassword(), user.getEnabled() == 1, true, true, true, authorities);
//    }
	
//	@Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UsersEntity user = userDao.getUserByEmai(email);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        // Lấy quyền từ cơ sở dữ liệu, bao gồm quyền UPDATE_BOOK
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//            .flatMap(role -> role.getPermissions().stream()) // Giả sử mỗi role có nhiều permission
//            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
//            .collect(Collectors.toList());
//
//        return new User(user.getEmail(), user.getPassword(), user.getEnabled() == 1, true, true, true, authorities);
//    }
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    UsersEntity user = userDao.getUserByEmai(email);

	    if (user == null) {
	        throw new UsernameNotFoundException("User not found with email: " + email);
	    }

	    // Lấy quyền từ cơ sở dữ liệu, bao gồm vai trò và quyền hạn
	    List<GrantedAuthority> authorities = new ArrayList<>();

	    // Thêm các vai trò vào danh sách authorities
	    user.getRoles().forEach(role -> {
	        authorities.add(new SimpleGrantedAuthority(role.getName())); // Không thêm "ROLE_" vào đây
	        
	        // Thêm các quyền hạn của mỗi vai trò vào danh sách authorities
	        role.getPermissions().forEach(permission -> {
	            authorities.add(new SimpleGrantedAuthority(permission.getName()));
	        });
	    });

	    return new User(user.getEmail(), user.getPassword(), user.getEnabled() == 1, true, true, true, authorities);
	}

	
}
