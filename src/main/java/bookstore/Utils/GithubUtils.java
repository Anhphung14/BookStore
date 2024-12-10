package bookstore.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bookstore.Models.auth.GithubPojo;

@Component
public class GithubUtils {
	public static String GITHUB_CLIENT_ID = "Ov23liaEWyhUvSt8WkUC";
	public static String GITHUB_CLIENT_SECRET = "2d62aadc8377998665fe46eab7ab2c2f961e4917";
	public static String GITHUB_REDIRECT_URI = "http://localhost:8080/login-github.htm";
	public static String GITHUB_LINK_GET_TOKEN = "https://github.com/login/oauth/access_token";
	public static String GITHUB_LINK_GET_USER_INFO = "https://api.github.com/user";
	
	public String getToken(final String code) throws ClientProtocolException, IOException {
		try {
			URL url = new URL(GITHUB_LINK_GET_TOKEN);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			
			String data = "client_id=" + GITHUB_CLIENT_ID + "&";
			data += "client_secret=" + GITHUB_CLIENT_SECRET + "&";
			data += "code=" + code;
			
			try(OutputStream os = conn.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);           
            }
			
			int statusCode = conn.getResponseCode();
			System.out.println("Status: " + statusCode);
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response.toString());

                // Phân tích phản hồi URL-encoded để lấy access token
                return parseQueryString(response.toString()).get("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Ném ngoại lệ để kiểm tra log chi tiết hơn
        }
			
	}
	
	public GithubPojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
		try {
			URL url = new URL(GITHUB_LINK_GET_USER_INFO);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			
			int statusCode = conn.getResponseCode();
			System.out.println(statusCode);
			
			// Đọc phản hồi từ server
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response.toString());

                ObjectMapper mapper = new ObjectMapper();
                GithubPojo gitHubPojo = mapper.readValue(response.toString(), GithubPojo.class);

                // Lấy email từ endpoint khác
                String emailsResponse = getEmail(accessToken);
                JsonNode emailNodes = mapper.readTree(emailsResponse);
                for (JsonNode emailNode : emailNodes) {
                    if (emailNode.get("primary").asBoolean()) {
                        gitHubPojo.setEmail(emailNode.get("email").asText());
                        break;
                    }
                }

                return gitHubPojo;
            }
			
		} catch (Exception e) {
            e.printStackTrace();
            throw e; // Ném ngoại lệ để kiểm tra log chi tiết hơn
        }
		
		
	}
	
	private String getEmail(final String accessToken) throws IOException {
        try {
            URL url = new URL("https://api.github.com/user/emails");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Accept", "application/json");

            int statusCode = conn.getResponseCode();
            System.out.println("Status: " + statusCode);

            // Đọc phản hồi từ server
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response.toString());
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Ném ngoại lệ để kiểm tra log chi tiết hơn
        }
    }
	
	private Map<String, String> parseQueryString(String query) {
        Map<String, String> queryPairs = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queryPairs;
    }
	
}
