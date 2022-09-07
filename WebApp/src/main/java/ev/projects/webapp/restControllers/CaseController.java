package ev.projects.webapp.restControllers;

import ev.projects.models.Case;
import ev.projects.services.ICaseService;
import ev.projects.webapp.requestModels.CaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/cases")
@RestController
public class CaseController {

    private ICaseService caseService;

    @Autowired
    public CaseController(ICaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping("/")
    public List<Case> getCases() {
        return caseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Case> getCase(@PathVariable("id") long ID) {
        return caseService.getById(ID);
    }

    @PostMapping("/")
    public void createCase(@RequestBody CaseRequest caseRequest) {
        caseService.add(caseRequest.convertToCase());
    }

    @PutMapping("/{id}")
    public HttpStatus updateCase(@RequestBody CaseRequest caseRequest, @PathVariable("id") long ID) {
        return caseService.getById(ID).map(c -> {
            caseService.update(caseRequest.convertToCase(c));
            return HttpStatus.OK;
        }).orElse(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteCase(@PathVariable("id") long ID) {
        caseService.removeById(ID);
    }

}
