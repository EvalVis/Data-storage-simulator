package ev.projects.services;

import ev.projects.models.User;
import ev.projects.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    private IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.findByName(user.getName()).map(u -> {
            u.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(u);
            return null;
        });
    }

    @Override
    public void remove() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRepository.
                findByName(auth.getName()).map(u -> {
                    userRepository.delete(u);
                    return null;
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found!"));
    }

}
