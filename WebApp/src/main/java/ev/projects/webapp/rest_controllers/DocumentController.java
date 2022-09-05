package ev.projects.webapp.rest_controllers;

import ev.projects.Case;
import ev.projects.Document;
import ev.projects.webapp.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DocumentController {

    private EntityRepository<Document> documentRepository;
    private EntityRepository<Case> caseRepository;

    @Autowired
    public DocumentController(EntityRepository<Document> documentRepository, EntityRepository<Case> caseRepository) {
        this.documentRepository = documentRepository;
        this.caseRepository = caseRepository;
    }

    @GetMapping("/case/documents")
    public List<Document> getDocuments(@RequestParam("id") long caseID) {
        Optional<Case> foundCase = caseRepository.getByID(caseID);
        if(foundCase.isPresent()) {
            return foundCase.get().getDocuments();
        }
        return new ArrayList<>();
    }

    @GetMapping("/case/documents/attachments")
    public List<Attachment> getAttachments(@RequestParam("id") long documentID) {
        Optional<Document> foundDocument = documentRepository.getByID(documentID);
        if(foundDocument.isPresent()) {
            return foundDocument.get().getAttachments();
        }
        return new ArrayList<>();
    }

}
