package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingsController {
	
	@RequestMapping("/ratings")
	public String ratings() {
		return "ratings/index";
	}
}
