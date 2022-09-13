package ev.projects.webapp.controllers;

import ev.projects.services.ICaseService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cases")
public class CaseController {

    private ICaseService caseService;

    @Autowired
    public CaseController(ICaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping("/get-case")
    public String redirectToCasePage(@ModelAttribute SearchFormData searchFormData) {
        return "redirect:/cases/" + searchFormData.getID();
    }

    @GetMapping("/{id}")
    public String showCase(@PathVariable("id") long ID, Model model) {
        return caseService.getById(ID).map(c -> {
            model.addAttribute("case", c);
            return "case";
        }).orElse("redirect:/");
    }

}
