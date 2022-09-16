package ev.projects.repositories;

import ev.projects.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence layer class responsible for dealing with CRUD operations on User entity.
 * @see org.springframework.security.core.userdetails.UserDetailsService -
 * UserDetailsService uses this repository to assist authentication.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Searches for user with username specified. Used by UserDetailsService.
     * @return - user with specified username, if found.
     */
    Optional<User> findByName(String username);

}
