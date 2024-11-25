import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
    	return new Cloudinary(ObjectUtils.asMap(
    			"cloud_name", "dsqhfz3xt",
                "api_key", "338957172242134",
                "api_secret", "dRSr_lNqopyzM4u3rrRD-VnYvmc"
    	));
    }
}
