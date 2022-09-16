package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.models.User;
import ev.projects.webapp.WebAppApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserRestControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private String username = "Mr smith";
    private String password = "qwerty";

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port + "/api/users/";
    }

    @Test
    @Order(1)
    public void userTest() {
        User user = new User(username, password);
        HttpEntity<User> request = new HttpEntity<>(user);
        assertEquals(HttpStatus.OK, registerUserRequest(request));
        assertEquals(HttpStatus.CONFLICT, registerUserRequest(request));
        deleteAccountRequest();
        assertEquals(HttpStatus.OK, registerUserRequest(request));
    }

    @Test
    public void updatePasswordTest() {
        String newPassword = "newTest";
        updatePasswordAssertion(password, newPassword, HttpStatus.OK);
        updatePasswordAssertion(password, "password", HttpStatus.UNAUTHORIZED);
        updatePasswordAssertion(newPassword, password, HttpStatus.OK);
        updatePasswordAssertion(password, password, HttpStatus.OK);
    }

    private void updatePasswordAssertion(String password, String newPassword, HttpStatus expectedStatus) {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-HTTP-Method-Override", "PATCH");
        HttpEntity<String> updatePasswordRequest = new HttpEntity<>(newPassword, headers);
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PATCH,
                updatePasswordRequest, new ParameterizedTypeReference<>() {});
        assertEquals(expectedStatus, response.getStatusCode());
    }

    @Test
    public void deleteAccountTest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        updatePasswordAssertion(password, password, HttpStatus.OK);
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE,
                null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        updatePasswordAssertion(password, password, HttpStatus.UNAUTHORIZED);
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
    }


}
