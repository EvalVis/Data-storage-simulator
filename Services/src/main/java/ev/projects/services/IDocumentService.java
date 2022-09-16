package ev.projects.services;

import ev.projects.models.Document;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Business logic component for Document entity.
 */
@Service
public interface IDocumentService {

    /**
     *
     * @param ID PK of document to get.
     * @return If exists, document by given ID.
     */
    Optional<Document> getById(long ID);

    /**
     * List of documents which are children of a given case.
     * @param caseID FK of document.
     * @return list of documents which have a Case parent with provided caseID.
     */
    List<Document> getAllDocumentsByCase(long caseID);

    /**
     * List of attachments which are children of a given document.
     * @param documentID FK of document.
     * @return list of attachments which have a Document parent with provided document ID.
     */
    List<Document> getAllAttachmentsByDocument(long documentID);

    /**
     * @param ID PK of document.
     * @return document file data.
     * @throws Exception if document file retrieval fails.
     */
    Resource getDocumentFile(long ID) throws Exception;

    /**
     * @param documentID PK of document.
     * @param file file data to upload.
     */
    void uploadDocument(long documentID, MultipartFile file);

    /**
     * Create a new document.
     * @param document document to be created in DB.
     * @return document which now has ID filled.
     */
    Document add(Document document);

    /**
     * Update a document in db, with a new document data provided.
     * @param document document with updated data.
     */
    void update(Document document);

    /**
     * Delete a document from DB.
     * @param ID PK of document to delete.
     */
    void removeById(long ID);

}
