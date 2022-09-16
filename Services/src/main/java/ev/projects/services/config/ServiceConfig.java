package ev.projects.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main configuration class for Services module.
 */
@Configuration
@ComponentScan({"ev.projects.repositories"})
public class ServiceConfig {

    /**
     * Encoder used for security.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
