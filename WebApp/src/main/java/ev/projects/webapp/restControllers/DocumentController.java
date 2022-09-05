package ev.projects.webapp.restControllers;

import ev.projects.models.Document;
import ev.projects.webapp.responseModels.DownloadDocumentResponse;
import ev.projects.webapp.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DownloadDocumentResponse> downloadDocument(@PathVariable("document_id") long documentID) {
        try {
            byte[] downloadData = documentService.downloadDocument(documentID);
            return ResponseEntity.status(HttpStatus.OK).body(new DownloadDocumentResponse(downloadData));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
