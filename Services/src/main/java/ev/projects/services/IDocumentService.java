package ev.projects.services;

import ev.projects.models.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDocumentService {
    List<Document> getAllDocumentsByCase(long caseID);
    List<Document> getAllAttachmentsByDocument(long documentID);
    byte[] getDocumentFile(long ID) throws Exception;
    void uploadDocument(long documentID, MultipartFile file);
    void add(Document document);
    void update(Document document);
    void removeById(long ID);

}
