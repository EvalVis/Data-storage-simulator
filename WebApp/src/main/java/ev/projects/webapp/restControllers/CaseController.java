package ev.projects.webapp.restControllers;

import ev.projects.models.Case;
import ev.projects.services.ICaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void createCase(@RequestBody Case aCase) {
        caseService.add(aCase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCase(@RequestBody Case aCase, @PathVariable("id") long ID) {
        aCase.setID(ID);
        return caseService.update(aCase) ? new ResponseEntity<>(null, HttpStatus.OK) :
        new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteCase(@PathVariable("id") long ID) {
        caseService.removeById(ID);
    }

}
