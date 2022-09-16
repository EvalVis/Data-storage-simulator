package ev.projects.webapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan({"ev.projects.services", "ev.projects.webapp"})
@EntityScan({"ev.projects.models"})
@EnableScheduling
@PropertySource(value = { "classpath:application.properties" })
public class WebConfig {
}
