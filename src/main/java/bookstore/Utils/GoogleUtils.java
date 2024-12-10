package bookstore.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bookstore.Models.auth.GooglePojo;

@Component
public class GoogleUtils {
	private RestTemplate restTemplate = new RestTemplate();
	
	public static String GOOGLE_CLIENT_ID = "644611492314-nck1gv86bbn2a8jc97ivgf3viuaged1p.apps.googleusercontent.com";
	public static String GOOGLE_CLIENT_SECRET = "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";
	public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/bookstore/login-google.htm";
	public static String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
	public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=";
	public static String GOOGLE_GRANT_TYPE = "authorization_code";
	
	public String getToken(final String code) throws ClientProtocolException, IOException {
        try {
            String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                    .bodyForm(Form.form().add("client_id", GOOGLE_CLIENT_ID)
                            .add("client_secret", GOOGLE_CLIENT_SECRET)
                            .add("redirect_uri", GOOGLE_REDIRECT_URI)
                            .add("code", code)
                            .add("grant_type", GOOGLE_GRANT_TYPE).build())
                    .execute().returnContent().asString();
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response).get("access_token");
            System.out.println("ACCESS_TOKEN " + node.textValue());
            
            return node.textValue();
        } catch (Exception e) {
        	System.err.println("Failed to get token: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném ngoại lệ để kiểm tra log chi tiết hơn
        }
    }
	
	public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
		String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
		String response = Request.Get(link).execute().returnContent().asString();
		ObjectMapper mapper = new ObjectMapper();
		GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
		
		return googlePojo;
	}
	
	public UserDetails buildUser(GooglePojo googlePojo) {
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialNonExpired = true;
		boolean accountNonLoked = true;
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails userDetail = new User(googlePojo.getEmail(),
					"", enabled, accountNonExpired, credentialNonExpired, accountNonLoked, authorities);
		
		return userDetail;
	}
	
	
}
