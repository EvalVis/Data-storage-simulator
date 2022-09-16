package ev.projects.webapp.restControllers;

import ev.projects.services.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for managing Report entity routes.
 */
@RequestMapping("/api/logs")
@RestController
public class ReportRestController {

    @Autowired
    private IReportService reportService;

    /**
     *
     * @param ID PK of report to get.
     * @param servletResponse object to write response data to.
     * @return report data in csv format.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Void> getReport(@PathVariable("id") long ID, HttpServletResponse servletResponse) {
        try {
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=\"log_" + ID + ".csv\"");
            reportService.getReport(ID, servletResponse.getWriter());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
