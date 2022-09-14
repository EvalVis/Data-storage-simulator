package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.models.User;
import ev.projects.repositories.ICaseRepository;
import ev.projects.repositories.IUserRepository;
import ev.projects.services.UserService;
import ev.projects.webapp.WebAppApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static ev.projects.models.CaseFactory.createCase;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CaseRestControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private IUserRepository userRepository;
    private UserService userService;
    private ICaseRepository caseRepository;

    String username1 = "Smith";
    String username2 = "Jane";
    String password1 = "pass";
    String password2 = "smith";

    private long userCountOffset;
    private long caseCountOffset;

    @BeforeAll
    public void startUp() {
        loadData();
    }

    @Autowired
    public CaseRestControllerTest(IUserRepository userRepository, UserService userService, ICaseRepository caseRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.caseRepository = caseRepository;
    }

    public void loadData() {
        userCountOffset = userService.add(new User(username1, password1)).getID() - 1;
        userService.add(new User(username2, password2));
        caseCountOffset = caseRepository.save(createCase("English", "English lessons",
                userService.getByUsername(username1).get())).getID() - 1;
        caseRepository.save(createCase("Math", "Math horrors",
                userService.getByUsername(username2).get()));
    }

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port + "/api/cases/";
    }

    @Test
    public void shouldReturnAllCasesTest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        assertCase(restTemplate, "Math", "Math horrors", username2, (int) (caseCountOffset + 1));
    }

    @Test
    public void getCaseTest() {
        String url = baseUrl + "/" + (caseCountOffset + 1) + "/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        ResponseEntity<Optional<Case>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        Optional<Case> responseCase = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseCase);
        assertTrue(responseCase.isPresent());
        Case aCase = responseCase.get();
        assertEquals( userCountOffset + 1, aCase.getCreatorUser().getID());
        assertCaseProperties(aCase, username1, "English", "English lessons", caseCountOffset + 1);
    }

    @Test
    @Order(1)
    public void createCaseTest() {
        String title = "History";
        String description = "Legendary stories";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        HttpEntity<Case> createRequest = new HttpEntity<>(createCase(title, description, null));
        ResponseEntity<Case> createResponse = restTemplate.exchange(baseUrl, HttpMethod.POST,
                createRequest, new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertCase(restTemplate, title, description, username1, (int) (caseCountOffset + 2));
    }

    @Test
    @Order(2)
    public void updateCaseTest() {
        String url = baseUrl + "/" + (caseCountOffset + 3) + "/";
        String title = "Future";
        String description = "Legendary tales";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        HttpEntity<Case> updateRequest = new HttpEntity<>(createCase(title, description, null));
        ResponseEntity<Case> createResponse = restTemplate.exchange(url, HttpMethod.PUT,
                updateRequest, new ParameterizedTypeReference<>() {});
        assertEquals(createResponse.getStatusCode(), HttpStatus.OK);
        assertCase(restTemplate, title, description, username1, (int) (caseCountOffset + 2));
    }

    private void assertCase(TestRestTemplate restTemplate, String title, String description, String username, int index) {
        ResponseEntity<List<Case>> getResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<Case> cases = getResponse.getBody();
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertNotNull(cases);
        Case aCase = cases.get(index);
        assertCaseProperties(aCase, username, title, description, index + 1);
    }

    private void assertCaseProperties(Case aCase, String username, String title,
                                      String description, long caseID) {
        assertNotNull(aCase);
        assertNotNull(aCase.getCreatorUser());
        assertEquals(caseID, aCase.getID());
        assertEquals(username, aCase.getCreatorUser().getName());
        assertEquals(title, aCase.getTitle());
        assertEquals(description, aCase.getDescription());
    }

    @Test
    public void deleteCaseTest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        String url = baseUrl + "/" + (caseCountOffset + 3) + "/";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(url, HttpMethod.DELETE,
                null, new ParameterizedTypeReference<>() {});
        assertEquals(deleteResponse.getStatusCode(), HttpStatus.OK);
        ResponseEntity<Optional<Case>> getResponse =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        Optional<Case> deletedCase = getResponse.getBody();
        assertNotNull(deletedCase);
        assertFalse(deletedCase.isPresent());
    }

    @AfterEach
    public void cleanUp() {
    }

}
