package ev.projects.webapp.controllers;

import ev.projects.services.ILogService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("/download")
    public String redirectToCasePage(@ModelAttribute SearchFormData searchFormData) {
        return "redirect:/logs/download/" + searchFormData.getID();
    }

    @GetMapping("/download/{id}")
    public void downloadReport(@PathVariable("id") Long ID, HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=report.csv");
        try {
            logService.getLog(ID, response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
