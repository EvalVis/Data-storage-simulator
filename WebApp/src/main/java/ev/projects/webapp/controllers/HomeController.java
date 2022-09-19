package ev.projects.webapp.controllers;

import ev.projects.services.ICaseService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Index page MVC controller.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private ICaseService caseService;

    @Autowired
    public HomeController(ICaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cases", caseService.getAll());
        model.addAttribute("searchFormData", new SearchFormData());
        return "home";
    }

}
