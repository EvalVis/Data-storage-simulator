package ev.projects.services;

import ev.projects.models.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getByUsername(String username);
    boolean usernameExists(String username);
    void add(User user);
    void update(User user);
    void remove();

}
