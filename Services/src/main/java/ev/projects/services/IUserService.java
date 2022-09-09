package ev.projects.services;

import ev.projects.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    Optional<User> getByUsername(String username);
    void add(User user);
    void update(User user);
    void remove();

}
