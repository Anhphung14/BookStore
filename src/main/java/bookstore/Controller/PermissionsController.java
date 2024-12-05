package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin1337")
public class PermissionsController {
    @RequestMapping(value = "/permissions")
    public String permissions() {
        return "permissions/index";  
    }
}
