package ev.projects.webapp.requestModels;

import ev.projects.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {

    private String username;
    private String password;

    public User convertToUser(String hashedPassword) {
        User user = new User();
        user.setName(username);
        user.setHashedPassword(hashedPassword);
        return user;
    }

}
