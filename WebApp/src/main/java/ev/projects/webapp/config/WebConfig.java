package ev.projects.webapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ev.projects.services", "ev.projects.webapp"})
@EntityScan({"ev.projects.models"})
public class WebConfig {
}
