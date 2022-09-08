package ev.projects.webapp.controllers;

import ev.projects.models.User;
import ev.projects.webapp.WebAppApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Value("${test.username}")
    private String username;
    @Value("${test.password}")
    private String password;

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port + "/api/users/";
    }

    @Test
    public void userTest() {
        User user = new User(username, password);
        HttpEntity<User> request = new HttpEntity<>(user);
        assertEquals(HttpStatus.OK, registerUserRequest(request));
        assertEquals(HttpStatus.CONFLICT, registerUserRequest(request));
        deleteAccountRequest();
        assertEquals(HttpStatus.OK, registerUserRequest(request));
    }

    private <T> HttpStatus registerUserRequest(HttpEntity<T> request) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<HttpStatus> response = restTemplate.exchange(baseUrl, HttpMethod.POST,
                request, new ParameterizedTypeReference<>() {});
        return response.getStatusCode();
    }

    private void deleteAccountRequest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        restTemplate.exchange(baseUrl, HttpMethod.DELETE,
                null, new ParameterizedTypeReference<>() {});
    }

    @AfterEach
    public void cleanUp() {
        deleteAccountRequest();
    }


}
