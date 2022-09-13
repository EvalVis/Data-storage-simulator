package ev.projects.webapp.controllers;

import ev.projects.models.Document;
import ev.projects.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static ev.projects.models.DocumentFactory.createDocumentOfCase;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private IDocumentService documentService;

    @Autowired
    public DocumentController(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}")
    public String showDocument(@PathVariable("id") long ID, Model model) {
        return documentService.getById(ID).map(d -> {
            model.addAttribute("document", d);
            return "document";
        }).orElse("redirect:/");
    }

    @GetMapping("/add-document/{case_id}")
    public String getAddDocumentForm(Model model, @PathVariable("case_id") Long caseID) {
        model.addAttribute("document", new Document());
        model.addAttribute("case_id", caseID);
        return "add_document";
    }

    @PostMapping("/add-document")
    public String createDocument(@ModelAttribute Document document) {
        long ID = documentService.add(document).getID();
        return "redirect:/documents/" + ID;
    }

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

}
