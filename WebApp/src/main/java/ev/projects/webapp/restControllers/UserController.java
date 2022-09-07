package ev.projects.webapp.restControllers;

import ev.projects.models.User;
import ev.projects.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users/")
@RestController
public class UserController {

    private IUserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public HttpStatus registerUser(@RequestBody User user) {
        String username = user.getUsername();
        if(userService.usernameExists(username)) {
            return HttpStatus.CONFLICT;
        }
        userService.add(user);
        return HttpStatus.OK;
    }
}
