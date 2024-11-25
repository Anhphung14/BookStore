package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PermissionsController {
    @RequestMapping(value = "/permissions")
    public String permissions() {
        return "permissions/index";  
    }
}
