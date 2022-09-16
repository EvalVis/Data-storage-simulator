package ev.projects.webapp.restControllers;

import ev.projects.models.Case;
import ev.projects.services.ICaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Case entity routes.
 */
@RequestMapping("/api/cases")
@RestController
public class CaseRestController {

    private ICaseService caseService;

    @Autowired
    public CaseRestController(ICaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * @return all Cases.
     */
    @GetMapping("/")
    public List<Case> getCases() {
        return caseService.getAll();
    }

    /**
     * @param ID - PK of case.
     * @return Case entity, if exists by the provided ID.
     */
    @GetMapping("/{id}")
    public Optional<Case> getCase(@PathVariable("id") long ID) {
        return caseService.getById(ID);
    }

    /**
     * Medium endpoint for case creation.
     * @param aCase - case to be created.
     */
    @PostMapping("/")
    public void createCase(@RequestBody Case aCase) {
        caseService.add(aCase);
    }

    /**
     * Medium endpoint for updating a case.
     * @param aCase - case entity which acts like DTO.
     * @param ID - PK of case to be updated.
     */
    @PutMapping("/{id}")
    public void updateCase(@RequestBody Case aCase, @PathVariable("id") long ID) {
        aCase.setID(ID);
        caseService.update(aCase);
    }

    /**
     * Medium endpoint for deleting a case, by the ID provided.
     * @param ID - PK of case.
     */
    @DeleteMapping("/{id}")
    public void deleteCase(@PathVariable("id") long ID) {
        caseService.removeById(ID);
    }

}
