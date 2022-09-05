package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.webapp.WebAppApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CaseControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;
    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void getCases() {
        String url = baseUrl + "/api/cases/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        List<Case> response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
    }

}
