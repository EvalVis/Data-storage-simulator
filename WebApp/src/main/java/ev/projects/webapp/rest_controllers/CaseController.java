package ev.projects.webapp.rest_controllers;

import ev.projects.Case;
import ev.projects.webapp.repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class CaseController {

    private EntityRepository<Case> caseRepository;

    @Autowired
    public CaseController(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @GetMapping("/cases")
    public List<Case> getCases() {
        return caseRepository.getAll();
    }

    @GetMapping("/case")
    public Optional<Case> getCase(@RequestParam("id") long ID) {
        return caseRepository.getByID(ID);
    }

    @PostMapping("/cases/create")
    public void saveCase(@RequestBody Case caseToSave) {
        caseRepository.save(caseToSave);
    }


}
