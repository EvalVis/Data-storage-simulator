package ev.projects.webapp.controllers;

import ev.projects.models.Document;
import ev.projects.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * MVC controller for Document entity routes.
 */
@Controller
@RequestMapping("/documents")
public class DocumentController {

    private IDocumentService documentService;

    @Autowired
    public DocumentController(IDocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     *
     * @param ID PK of document.
     * @return if document with provided ID exists -> show document page, else redirect to main page.
     */
    @GetMapping("/{id}")
    public String getDocument(@PathVariable("id") long ID, Model model) {
        return documentService.getById(ID).map(d -> {
            model.addAttribute("document", d);
            return "document";
        }).orElse("redirect:/");
    }

    /**
     * Shows a document form which contains hidden fields to identify entity's parent.
     * @param parentID - FK of document.
     * @param isAttachment - if true tells what FK is documentID, else -> FK is caseID.
     * @return view which contains a document form.
     */
    @GetMapping("/add-document-form/{parent_id}/{is_attachment}")
    public String getAddDocumentForm(Model model, @PathVariable("parent_id") Long parentID,
                                     @PathVariable("is_attachment") Boolean isAttachment) {
        model.addAttribute("document", new Document());
        model.addAttribute("parent_id", parentID);
        model.addAttribute("is_attachment", isAttachment);
        return "add_document";
    }

    /**
     * @param document document to be created.
     * @return redirects to a newly created document's page.
     */
    @PostMapping("/add-document")
    public String createDocument(@ModelAttribute Document document) {
        long ID = documentService.add(document).getID();
        return "redirect:/documents/" + ID;
    }

    /**
     * Upload a document file.
     * @param ID - PK of document.
     * @param file - file to be uploaded..
     * @return on success redirects to the current page, else -> main page.
     */
    @PostMapping("/{id}")
    public String uploadDocument(@PathVariable("id") long ID, @RequestParam("file") MultipartFile file) {
        try {
            documentService.uploadDocument(ID, file);
            return "redirect:/documents/" + ID;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    /**
     * Download a document file.
     * @param ID - document's PK.
     * @return - on success -> a file.
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable("id") long ID) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String mimeType = documentService.getById(ID).get().getMimeType();
            httpHeaders.set("Content-Disposition", "attachment; filename=document_file." + mimeType);
            Resource resource = documentService.getDocumentFile(ID);
            return ResponseEntity.ok().headers(httpHeaders).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
