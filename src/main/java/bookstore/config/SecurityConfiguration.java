package bookstore.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.multipart.support.MultipartFilter;

import bookstore.Service.CustomUserDetailsService;
import bookstore.security.CustomAccessDeniedHandler;
import bookstore.security.CustomAuthenticationSuccessHandler;
import bookstore.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	private CustomAccessDeniedHandler customeAccessDeniedHandler;
	
	@Autowired private 
	CustomUserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Sử dụng custom UserDetailsService
        auth.userDetailsService(userDetailsService) 
        	.passwordEncoder(passwordEncoder());
    }
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
		HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfTokenRepository.setHeaderName("X-XSRF-TOKEN");
		
	    http
		    .addFilterBefore(multipartFilter(), CsrfFilter.class)
		    .csrf()
//	    	.csrfTokenRepository(csrfTokenRepository)
	    	.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
	    	.and()
	        .authorizeRequests()
	            .antMatchers("/login-github**", "/login-google**", "/signup","/index", "/categories/**", "/productdetail/**", "/cart/add/**", "/verify-email/**", "/resend-link/**", "/saveSignup/**", "/search/**", "/allProduct/**", "/forgotpassword", "/images/**").permitAll()
//	            .antMatchers("/admin1337/**").hasRole("ADMIN")
//	            .antMatchers("/admin1337/product/new").hasAuthority("UPDATE_BOOK")
	            .antMatchers("/admin1337/**").hasAnyRole("STAFF", "ADMIN")
	            .antMatchers("/**").hasAnyRole("USER", "ADMIN", "STAFF")
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/signin")
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
		        .logoutUrl("/signout")
		        .logoutSuccessHandler(customLogoutSuccessHandler)
	            .permitAll()
	            .and()
	        .exceptionHandling()
	            .accessDeniedHandler(customeAccessDeniedHandler)
	            .and();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/admin1337/resources/**");
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() { 
		return new HttpSessionEventPublisher();
	}
	
	@Bean
    public MultipartFilter multipartFilter() {
        return new MultipartFilter();
    }
	
}
