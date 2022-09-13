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

@RequestMapping("/api/documents")
@RestController
public class DocumentController {

    private IDocumentService documentService;

    @Autowired
    public DocumentController(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{case_id}")
    public List<Document> getDocumentsByCase(@PathVariable("case_id") long caseID) {
        return documentService.getAllDocumentsByCase(caseID);
    }

    @GetMapping("/attachments/{document_id}")
    public List<Document> getAttachmentsByDocument(@PathVariable("document_id") long documentID) {
        return documentService.getAllAttachmentsByDocument(documentID);
    }

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

    @PostMapping("/")
    public ResponseEntity<Void> createDocument(@RequestBody Document document) {
        return documentService.add(document) != null ? new ResponseEntity<>(null, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

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

    @PutMapping("/{id}")
    public void updateDocument(@RequestBody Document document, @PathVariable("id") long ID) {
        document.setID(ID);
        documentService.update(document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") long ID) {
        documentService.removeById(ID);
    }

}
