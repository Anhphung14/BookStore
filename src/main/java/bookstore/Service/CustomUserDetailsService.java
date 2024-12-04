//package bookstore.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import bookstore.DAO.UserDAO;
//import bookstore.Entity.UsersEntity;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService{
//
//	@Autowired
//	UserDAO userDao;
//	
//	@Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UsersEntity user = userDao.getUserByEmailPass(username, username)
//    }
//	
//}
