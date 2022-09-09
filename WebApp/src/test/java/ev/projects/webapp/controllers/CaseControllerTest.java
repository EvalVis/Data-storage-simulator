package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.models.User;
import ev.projects.repositories.ICaseRepository;
import ev.projects.services.ICaseService;
import ev.projects.services.IUserService;
import ev.projects.webapp.WebAppApplication;
import org.apache.coyote.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CaseControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private IUserService userService;
    private ICaseRepository caseRepository;

    String username1 = "Smith";
    String username2 = "Jane";
    String password1 = "pass";
    String password2 = "smith";

    @BeforeAll
    public void startUp() {
        loadData();
    }

    @Autowired
    public CaseControllerTest(IUserService userService, ICaseRepository caseRepository) {
        this.userService = userService;
        this.caseRepository = caseRepository;
    }

    public void loadData() {
        userService.add(new User(username1, password1));
        userService.add(new User(username2, password2));
        caseRepository.save(createCase("English", "English lessons", userService.getByUsername(username1).get()));
        caseRepository.save(createCase("Math", "Math horrors", userService.getByUsername(username2).get()));
    }

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port + "/api/cases/";
    }

    private Case createCase(String title, String description, User user) {
        Case aCase = new Case();
        aCase.setTitle(title);
        aCase.setDescription(description);
        aCase.setCreatorUser(user);
        aCase.setCreationDate(new Date());
        return aCase;
    }

    @Test
    public void getCasesTest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        assertCase(restTemplate, "Math", "Math horrors", username2,1);
    }

    @Test
    public void getCaseTest() {
        String url = baseUrl + "/1/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        ResponseEntity<Optional<Case>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        Optional<Case> responseCase = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseCase);
        assertTrue(responseCase.isPresent());
        Case aCase = responseCase.get();
        assertEquals( "English", aCase.getTitle());
        assertEquals( "English lessons", aCase.getDescription());
        assertEquals( 1, aCase.getID());
        assertNotNull(aCase.getCreatorUser());
        assertEquals( 1, aCase.getCreatorUser().getID());
        assertEquals(username1, aCase.getCreatorUser().getName());
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
        assertCase(restTemplate, title, description, username1,2);
    }

    @Test
    @Order(2)
    public void updateCaseTest() {
        String url = baseUrl + "/3/";
        String title = "Future";
        String description = "Legendary tales";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        HttpEntity<Case> updateRequest = new HttpEntity<>(createCase(title, description, null));
        ResponseEntity<Case> createResponse = restTemplate.exchange(url, HttpMethod.PUT,
                updateRequest, new ParameterizedTypeReference<>() {});
        assertEquals(createResponse.getStatusCode(), HttpStatus.OK);
        assertCase(restTemplate, title, description, username1, 2);
    }

    private void assertCase(TestRestTemplate restTemplate, String title, String description, String username, int index) {
        ResponseEntity<List<Case>> getResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<Case> cases = getResponse.getBody();
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertNotNull(cases);
        assertEquals(index + 1, cases.size());
        assertNotNull(cases.get(index));
        assertNotNull(cases.get(index).getCreatorUser());
        assertEquals(username, cases.get(index).getCreatorUser().getName());
        assertEquals(index + 1, cases.get(index).getID());
        assertEquals(title, cases.get(index).getTitle());
        assertEquals(description, cases.get(index).getDescription());
    }

    @Test
    public void deleteCaseTest() {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username1, password1);
        String deleteUrl = baseUrl + "/3/";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(deleteUrl, HttpMethod.DELETE,
                null, new ParameterizedTypeReference<>() {});
        assertEquals(deleteResponse.getStatusCode(), HttpStatus.OK);
        String getAfterDeleteUrl = baseUrl + "/3/";
        ResponseEntity<Optional<Case>> getResponse =
                restTemplate.exchange(getAfterDeleteUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        Optional<Case> deletedCase = getResponse.getBody();
        assertNotNull(deletedCase);
        assertFalse(deletedCase.isPresent());
    }

    @AfterEach
    public void cleanUp() {
    }

}
