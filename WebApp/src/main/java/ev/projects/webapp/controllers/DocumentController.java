package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.models.Document;
import ev.projects.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "add_document";
    }

    @PostMapping("/add-document")
    public String createCase(@ModelAttribute Document document) {
        long ID = documentService.add(document).getID();
        return "redirect:/documents/" + ID;
    }

}
