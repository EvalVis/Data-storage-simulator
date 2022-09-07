package ev.projects.services.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ev.projects.repositories"})
public class ServiceConfig {
}
