package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RolesController {
    @RequestMapping(value = "/roles")
    public String roles() {
        return "roles/index";  
    }
}
