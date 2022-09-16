package ev.projects.webapp.controllers;

import ev.projects.services.IReportService;
import ev.projects.webapp.forms.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC controller for Report entity routes.
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    private IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Show reports page which lists all the reports.
     * @return show reports page.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("reports", reportService.getAll());
        model.addAttribute("searchFormData", new SearchFormData());
        return "reports";
    }

    /**
     * Helper method to download a report.
     * @param searchFormData helper class containing report ID.
     * @return redirection to report download page.
     */
    @PostMapping("/download")
    public String redirectToDownloadReportPage(@ModelAttribute SearchFormData searchFormData) {
        return "redirect:/reports/download/" + searchFormData.getID();
    }

    /**
     * Triggers document download.
     * @param ID - PK of report.
     * @param response - response object which contains a writer to write data on.
     */
    @GetMapping("/download/{id}")
    public void downloadReport(@PathVariable("id") Long ID, HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=report.csv");
        try {
            reportService.getLog(ID, response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
