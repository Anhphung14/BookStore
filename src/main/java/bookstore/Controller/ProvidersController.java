package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProvidersController {
	@RequestMapping(value = "/providers")
	public String providers() {
		return "providers/index";
	}
}
