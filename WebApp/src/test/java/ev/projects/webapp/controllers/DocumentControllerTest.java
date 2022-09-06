package ev.projects.webapp.controllers;

import ev.projects.models.Document;
import ev.projects.repositories.DocumentRepository;
import ev.projects.webapp.WebAppApplication;
import ev.projects.webapp.responseModels.DownloadDocumentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerTest {

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

    @ParameterizedTest
    @CsvSource({"1,1,English Lesson 1,First lesson,data/image.jpg", "2,2,Math lesson 1,Hard lesson,data/homework.txt"})
    public void getDocumentsByCaseTest(long caseID, long documentID, String title,
                                       String description, String filePath) throws NoSuchFieldException, IllegalAccessException {
        HashMap<Long, List<Document>> documents = new HashMap<>();
        documents.put(caseID, List.of(new Document(documentID, title,
                description, filePath, 0, "")));
        addDocuments(documents);
        String url = baseUrl + "/api/documents/" + caseID + "/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<List<Document>> documentsResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Document>>() {});
        List<Document> responseDocuments = documentsResponse.getBody();
        assertNotNull(responseDocuments);
        assertEquals(1, responseDocuments.size());
        Document document = responseDocuments.get(0);
        assertEquals(documentID, document.getID());
        assertEquals(title, document.getTitle());
        assertEquals(description, document.getDescription());
        assertEquals(filePath, document.getFilePath());
    }

    @ParameterizedTest
    @CsvSource({"1,/data/image.jpg,139_191", "2,/data/homework.txt,16"})
    public void downloadDocumentTest(long documentID, String filePath, long fileSize) throws NoSuchFieldException, IllegalAccessException, IOException {
        HashMap<Long, List<Document>> documents = new HashMap<>();
        documents.put(1L, List.of(new Document(documentID, "",
                "", filePath, 0, "")));
        addDocuments(documents);
        String url = baseUrl + "/api/documents/download/" + documentID + "/";
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth(username, password);
        ResponseEntity<DownloadDocumentResponse> downloadResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<DownloadDocumentResponse>() {});
        DownloadDocumentResponse downloadDocumentResponse = downloadResponse.getBody();
        assertNotNull(downloadDocumentResponse);
        assertNotNull(downloadDocumentResponse.getDownloadData());
        assertEquals(fileSize, downloadDocumentResponse.getDownloadData().length);
        assertArrayEquals(downloadDocumentResponse.getDownloadData(),
                Objects.requireNonNull(getClass().getResourceAsStream(filePath)).readAllBytes());
    }

    private void addDocuments(HashMap<Long, List<Document>> documents) throws NoSuchFieldException, IllegalAccessException {
        Field privateField = DocumentRepository.class.getDeclaredField("documents");
        privateField.setAccessible(true);
        privateField.set(null, documents);
    }



}
