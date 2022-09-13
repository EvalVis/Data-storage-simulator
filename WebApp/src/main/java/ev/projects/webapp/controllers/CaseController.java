package ev.projects.webapp.controllers;

import ev.projects.services.ICaseService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
