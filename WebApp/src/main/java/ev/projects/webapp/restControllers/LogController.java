package ev.projects.webapp.restControllers;

import ev.projects.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/logs")
@RestController
public class LogController {

    private ILogService logService;

    @Autowired
    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getLog(@PathVariable("id") long ID, HttpServletResponse servletResponse) {
        try {
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=\"log_" + ID + ".csv\"");
            logService.getLog(ID, servletResponse.getWriter());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
