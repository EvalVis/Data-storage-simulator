package ev.projects.webapp.controllers;

import ev.projects.models.Document;
import ev.projects.models.User;
import ev.projects.repositories.ICaseRepository;
import ev.projects.services.IDocumentService;
import ev.projects.services.IUserService;
import ev.projects.webapp.WebAppApplication;
import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import java.io.IOException;
import java.util.List;

import static ev.projects.webapp.utils.EntitiesCreator.createCase;
import static ev.projects.webapp.utils.EntitiesCreator.createDocument;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan({"ev.projects.services"})
public class DocumentControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    String username = "Incognito";
    String password = "Secret";

    private IUserService userService;
    private ICaseRepository caseRepository;
    private IDocumentService documentService;

    @Autowired
    public DocumentControllerTest(IUserService userService, ICaseRepository caseRepository, IDocumentService documentService) {
        this.userService = userService;
        this.caseRepository = caseRepository;
        this.documentService = documentService;
    }

    @BeforeAll
    public void startUp() {
        loadData();
    }

    public void loadData() {
        userService.add(new User(username, password));
        caseRepository.save(createCase("English", "English lessons", userService.getByUsername(username).get()));
        caseRepository.save(createCase("Math", "Math horrors", userService.getByUsername(username).get()));
        documentService.add(createDocument("Math lesson 1", "Amazing", 2, 0));
        documentService.add(createDocument("Math lesson 1 homework", "Boring", 0, 1));
        documentService.add(createDocument("English lesson 1", "Cool", 1, 0));
        documentService.add(createDocument("English 1 homework", "Not so cool", 0, 3));
    }

    @BeforeEach
    public void start() {
        baseUrl = "http://localhost:" + port + "/api/documents/";
    }

    @Test
    public void getDocumentsByCaseTest() {
        String url = baseUrl + "/1/";
        testDocuments(url, 1, 3, "English lesson 1", "Cool", null,
                0, null, 1, 0, 0);
    }

    @Test
    public void getAttachmentsByDocumentTest() {
        String url = baseUrl + "/attachments/3/";
        testDocuments(url, 1, 4, "English 1 homework", "Not so cool", null,
                0, null, 0, 3, 0);
    }

    @Test
    public void createDocumentTest() {
        // Test if you can create doc which is a child of case.
        createRequest(1, 0, HttpStatus.OK);
        // Test if you can create attachment of doc.
        createRequest(0, 1, HttpStatus.OK);
        // Test if attachments can't have attachments.
        createRequest(0, 6, HttpStatus.CONFLICT);
    }

    private void createRequest(long caseID, long documentID, HttpStatus expectedStatusCode) {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        HttpEntity<Document> request = new HttpEntity<>(createDocument("Test", "test", caseID, documentID));
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.POST,
                request, new ParameterizedTypeReference<>() {});
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Test
    public void updateDocumentTest() {
        String url = baseUrl + "/6/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        HttpEntity<Document> request = new HttpEntity<>(
                createDocument("Renamed", "renamed", 0, 0));
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT,
                request, new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        url = baseUrl + "/attachments/1/";
        testDocuments(url, 2, 6, "Renamed", "renamed", null,
                0, null, 0, 1, 1);
    }
    
    @Test
    public void uploadDocumentTest() {
        String url = baseUrl + "/1/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-HTTP-Method-Override", "PATCH");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new org.springframework.core.io.ClassPathResource("data/image.jpg"));
        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PATCH, request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        url = baseUrl + "/2/";
        testDocuments(url, 1, 1, "Math lesson 1",
                "Amazing", "1_image.jpg", 139191,
                "jpg", 2, 0, 0);
    }

    @Test
    public void downloadDocumentTest() throws IOException {
        String url = baseUrl + "/download/1/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<Resource> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Resource resource = response.getBody();
        assertNotNull(resource);
        assertEquals(139191, resource.contentLength());
    }

    @Test
    public void deleteDocumentTest() {
        String url = baseUrl + "/3/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        restTemplate.exchange(url, HttpMethod.DELETE,
                null, new ParameterizedTypeReference<>() {});
        assertThrows(AssertionFailedError.class, this::getDocumentsByCaseTest);
    }

    private void testDocuments(String url, int listSize, long ID, String title, String description, String filePath,
                                long fileSize, String mimeType, long owningCaseID,
                                long owningDocumentID, int documentIndex) {
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<List<Document>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        List<Document> documents = response.getBody();
        Document document = documents.get(documentIndex);
        assertNotNull(documents);
        assertEquals(listSize, documents.size());
        assertNotNull(document);
        assertEquals(ID, document.getID());
        assertEquals(title, document.getTitle());
        assertEquals(description, document.getDescription());
        assertEquals(filePath, document.getFilePath());
        assertEquals(fileSize, document.getFileSize());
        assertEquals(mimeType, document.getMimeType());
        if(owningCaseID > 0) {
            assertNotNull(document.getOwningCase());
            assertNull(document.getOwningDocument());
            assertEquals(owningCaseID, document.getOwningCase().getID());
        }
        if(owningDocumentID > 0) {
            assertNotNull(document.getOwningDocument());
            assertNull(document.getOwningCase());
            assertEquals(owningDocumentID, document.getOwningDocument().getID());
        }
    }

    @AfterEach
    public void cleanUp() {
    }

}
