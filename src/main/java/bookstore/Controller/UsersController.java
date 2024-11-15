package bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

    @RequestMapping(value = "/users")
    public String users() {
        return "users/index";  
    }


    @RequestMapping(value = "/user/edit/{id}")
    public String userEdit(@PathVariable("id") Long id) {
      
        // userService.getUserById(id) để lấy dữ liệu người dùng từ DB
        return "users/userEdit";  
    }

    @RequestMapping(value = "/user/new")
    public String userNew() {
        return "users/userEdit";  
    }
}
