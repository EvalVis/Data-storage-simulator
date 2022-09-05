package ev.projects.webapp.repositories;

import ev.projects.Document;
import java.util.List;

public interface IDocumentRepository {

    List<Document> getAllDocumentsByCase(long caseID);
    List<Document> getAllAttachmentsByDocument(long documentID);

}
