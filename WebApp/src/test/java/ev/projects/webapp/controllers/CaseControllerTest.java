package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.repositories.CaseRepository;
import ev.projects.webapp.WebAppApplication;
import ev.projects.webapp.utils.MockData;;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getCasesTest() throws NoSuchFieldException, IllegalAccessException {
        List<Case> cases = MockData.generateCases();
        addCases(cases);
        String url = baseUrl + "/api/cases/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<List<Case>> casesResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Case>>() {});
        List<Case> responseCases = casesResponse.getBody();
        assertNotNull(responseCases);
        assertEquals(2, responseCases.size());
        Case aCase = responseCases.get(1);
        assertNotNull(aCase);
        assertEquals(aCase.getID(), 2);
        assertEquals(aCase.getTitle(), "Math");
    }

    @ParameterizedTest
    @CsvSource({"1,English,Smith", "2,Math,Mike"})
    public void getCaseTest(long ID, String title, String creatorUser) throws NoSuchFieldException, IllegalAccessException {
        Case aCase = new Case(ID, title, new Date(), creatorUser);
        addCases(List.of(aCase));
        String url = baseUrl + "/api/cases/" + ID + "/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<Optional<Case>> caseResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Optional<Case>>() {});
        Optional<Case> responseCase = caseResponse.getBody();
        assertNotNull(responseCase);
        assertTrue(responseCase.isPresent());
        Case rCase = responseCase.get();
        assertEquals(aCase, rCase);
        assertEquals(ID, rCase.getID());
        assertEquals(title, rCase.getTitle());
        assertEquals(creatorUser, rCase.getCreatorUser());
    }

    private void addCases(List<Case> cases) throws NoSuchFieldException, IllegalAccessException {
        Field privateField = CaseRepository.class.getDeclaredField("cases");
        privateField.setAccessible(true);
        privateField.set(null, cases);
    }

}