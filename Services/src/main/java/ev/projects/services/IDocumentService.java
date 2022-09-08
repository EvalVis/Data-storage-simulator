package ev.projects.services;

import ev.projects.models.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IDocumentService {

    Optional<Document> getById(long ID);
    List<Document> getAllDocumentsByCase(long caseID);
    List<Document> getAllAttachmentsByDocument(long documentID);
    byte[] getDocumentFile(long ID) throws Exception;
    void uploadDocument(long documentID, MultipartFile file);
    void add(Document document);
    boolean update(Document document);
    void removeById(long ID);

}
