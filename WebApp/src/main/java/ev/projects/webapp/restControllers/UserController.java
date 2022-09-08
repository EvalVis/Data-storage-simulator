package ev.projects.webapp.restControllers;

import ev.projects.models.User;
import ev.projects.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users/")
@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public HttpStatus registerUser(@RequestBody User user) {
        String username = user.getUsername();
        if(userService.usernameExists(username)) {
            return HttpStatus.CONFLICT;
        }
        userService.add(user);
        return HttpStatus.OK;
    }

    @PatchMapping("/")
    public void updatePassword(@RequestBody String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User(auth.getName(), newPassword);
        userService.update(user);
    }

    @DeleteMapping("/")
    public void deleteAccount() {
        userService.remove();
    }

}
