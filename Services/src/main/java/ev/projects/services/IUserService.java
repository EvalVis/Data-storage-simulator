package ev.projects.services;

import ev.projects.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic component for User entity.
 */
@Service
public interface IUserService {

    /**
     * Mostly used by security components.
     * @return If exists, user with a given username.
     */
    Optional<User> getByUsername(String username);

    /**
     * Create a new user.
     * @param user to be created.
     * @return User entity with filled ID.
     */
    User add(User user);

    /**
     * Update old user by given data of new user.
     * @param user user containing new data.
     */
    void update(User user);

    /**
     * Delete a user.
     */
    void remove();

}
