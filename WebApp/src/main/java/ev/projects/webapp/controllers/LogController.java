package ev.projects.webapp.controllers;

import ev.projects.services.ILogService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logs")
public class LogController {

    private ILogService logService;

    @Autowired
    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("logs", logService.getAll());
        model.addAttribute("searchFormData", new SearchFormData());
        return "logs";
    }

}
