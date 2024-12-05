package bookstore.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import bookstore.security.CustomAuthenticationSuccessHandler;
import bookstore.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Sử dụng custom UserDetailsService
        auth.userDetailsService(userDetailsService()) 
        	.passwordEncoder(passwordEncoder());
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);  // Cấu hình DataSource cho JdbcDaoImpl
        jdbcDao.setUsersByUsernameQuery("SELECT EMAIL, PASSWORD, ENABLED FROM Users WHERE EMAIL = ?");
        jdbcDao.setAuthoritiesByUsernameQuery("SELECT u.email AS username, r.name AS ROLE FROM User_Roles ur JOIN Users u ON ur.user_id = u.id JOIN Roles r ON ur.role_id = r.id WHERE u.email = ?");
        return jdbcDao; // Trả về UserDetailsService để sử dụng trong authentication
    }
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//			.usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM user_test WHERE USERNAME = ?")
//			.authoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM user_test WHERE USERNAME = ?");
//	}
//	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .authorizeRequests()
	            .antMatchers("/signup.htm","/index.htm", "/categories/**", "/productdetail/**", "/cart/add.htm/**", "/verify-email.htm/**", "/resend-link.htm/**", "/saveSignup.htm/**", "/search.htm/**", "/allProduct.htm/**").permitAll()
	            .antMatchers("/admin1337/**").hasRole("ADMIN")
	            .antMatchers("/**").hasAnyRole("USER", "ADMIN")
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/signin.htm")
	            .loginProcessingUrl("/perform_login")
	            .usernameParameter("email")
	            .passwordParameter("password")
	            .successHandler(successHandler)
	            .permitAll()
	            .and()
	        .sessionManagement()
	        	.sessionFixation().newSession()
                .and()
	        .logout()
		        .logoutUrl("/signout.htm")            // Đặt URL logout cho phương thức POST
//	            .logoutSuccessUrl("/signin.htm")     // Chuyển hướng sau khi đăng xuất thành công
		        .logoutSuccessHandler(customLogoutSuccessHandler)
	            .permitAll()
	            .and()
	        .exceptionHandling()
	            .accessDeniedPage("/signup.htm");
	}
	

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/admin1337/resources/**");
	}
	
}
