package ev.projects.webapp.repositories;

import ev.projects.Document;
import java.util.List;
import java.util.Optional;

public interface IDocumentRepository {

    Optional<Document> getById(long ID);
    List<Document> getAllDocumentsByCase(long caseID);
    List<Document> getAllAttachmentsByDocument(long documentID);

}
