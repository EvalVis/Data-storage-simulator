package ev.projects.webapp.restControllers;

import ev.projects.models.Case;
import ev.projects.services.ICaseService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return caseService.getAllById(ID);
    }

    @PostMapping("/")
    public void createCase(@RequestBody Case aCase) {
        caseService.add(aCase);
    }

    @PutMapping("/")
    public void updateCase(@RequestBody Case aCase) {
        caseService.update(aCase);
    }

    @DeleteMapping("/{id}")
    public void deleteCase(@PathVariable("id") long ID) {
        caseService.removeById(ID);
    }

}
