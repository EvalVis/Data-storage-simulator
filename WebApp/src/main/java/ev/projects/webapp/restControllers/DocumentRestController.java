package ev.projects.webapp.restControllers;

import ev.projects.models.Document;
import ev.projects.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for managing Document entity routes.
 */
@RequestMapping("/api/documents")
@RestController
public class DocumentRestController {

    private IDocumentService documentService;

    @Autowired
    public DocumentRestController(IDocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Get document list of a case, specified by its ID.
     * @param caseID FK of documents.
     * @return document list, which are children of specified case.
     */
    @GetMapping("/{case_id}")
    public List<Document> getDocumentsByCase(@PathVariable("case_id") long caseID) {
        return documentService.getAllDocumentsByCase(caseID);
    }

    @GetMapping("/attachments/{document_id}")
    public List<Document> getAttachmentsByDocument(@PathVariable("document_id") long documentID) {
        return documentService.getAllAttachmentsByDocument(documentID);
    }

    /**
     * Get attachment list of a document, specified by its ID.
     * @param documentID FK of documents.
     * @return attachment list, which are children of specified document.
     */
    @GetMapping("/download/{document_id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable("document_id") long documentID) {
        try {
            Resource resource = documentService.getDocumentFile(documentID);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Create a new document.
     * @param document document entity acting like DTO.
     * @return ResponseEntity containing HttpStatus of the action.
     */
    @PostMapping("/")
    public ResponseEntity<Void> createDocument(@RequestBody Document document) {
        return documentService.add(document) != null ? new ResponseEntity<>(null, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    /**
     * Upload a document file.
     * @param documentID PK of document.
     * @param file file to be uploaded.
     * @return ResponseEntity which contains HttpStatus of the action.
     */
    @PatchMapping("/{document_id}")
    public ResponseEntity<Void> uploadDocument(@PathVariable("document_id") long documentID,
                                               @RequestParam("file") MultipartFile file) {
        try {
            documentService.uploadDocument(documentID, file);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update document of given ID by given DTO.
     * @param document entity document, acting like DTO.
     * @param ID ID of document to be updated.
     */
    @PutMapping("/{id}")
    public void updateDocument(@RequestBody Document document, @PathVariable("id") long ID) {
        document.setID(ID);
        documentService.update(document);
    }

    /**
     * Delete document of given ID.
     * @param ID PK of document to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") long ID) {
        documentService.removeById(ID);
    }

}
