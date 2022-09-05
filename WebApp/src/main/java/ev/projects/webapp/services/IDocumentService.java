package ev.projects.webapp.services;

import ev.projects.models.Document;

import java.util.List;

public interface IDocumentService {
    List<Document> getAllDocumentsByCase(long caseID);
    List<Document> getAllAttachmentsByDocument(long documentID);
    byte[] downloadDocument(long ID) throws Exception;

}
