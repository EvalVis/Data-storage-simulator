package ev.projects.webapp.controllers;

import ev.projects.models.Case;
import ev.projects.services.ICaseService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC controller for Case entity routes.
 */
@Controller
@RequestMapping("/cases")
public class CaseController {

    private ICaseService caseService;

    @Autowired
    public CaseController(ICaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * Helper method for getting to case page.
     * @param searchFormData contains the ID of case.
     * @return redirection to case (with provided ID) page.
     */
    @PostMapping("/get-case")
    public String redirectToCasePage(@ModelAttribute SearchFormData searchFormData) {
        return "redirect:/cases/" + searchFormData.getID();
    }

    /**
     *
     * @param ID PK of Case.
     * @return shows page of Case with provided ID.
     */
    @GetMapping("/{id}")
    public String getCase(@PathVariable("id") long ID, Model model) {
        return caseService.getById(ID).map(c -> {
            model.addAttribute("case", c);
            return "case";
        }).orElse("redirect:/");
    }

    /**
     *
     * @return show page which contains form to add a new case.
     */
    @GetMapping("/add-case-form")
    public String getAddCaseForm(Model model) {
        model.addAttribute("case", new Case());
        return "add_case";
    }

    /**
     * @param newCase case to be created.
     * @return redirects to the newly created case's page.
     */
    @PostMapping("/add-case")
    public String createCase(@ModelAttribute Case newCase) {
        long ID = caseService.add(newCase).getID();
        return "redirect:/cases/" + ID;
    }

}
