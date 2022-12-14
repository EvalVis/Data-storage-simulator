package ev.projects.webapp.restControllers;

import ev.projects.models.User;
import ev.projects.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing User entity routes.
 */
@RequestMapping("/api/users/")
@RestController
public class UserRestController {

    private IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Create a user in the DB.
     * @param user - User entity which acts like DTO.
     * @return ResponseEntity which contains HttpStatus.
     */
    @PostMapping("/")
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        try {
            userService.add(user);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Set a new user password.
     * @param newPassword replacement password.
     */
    @PatchMapping("/")
    public void updatePassword(@RequestBody String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User(auth.getName(), newPassword);
        userService.update(user);
    }

    /**
     * Delete user account.
     */
    @DeleteMapping("/")
    public void deleteAccount() {
        userService.remove();
    }

}
