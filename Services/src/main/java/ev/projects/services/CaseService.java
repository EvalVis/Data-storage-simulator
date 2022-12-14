package ev.projects.services;

import ev.projects.models.Case;
import ev.projects.repositories.ICaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CaseService implements ICaseService {

    private ICaseRepository caseRepository;
    private IUserService userService;

    @Autowired
    public CaseService(ICaseRepository caseRepository, IUserService userService) {
        this.caseRepository = caseRepository;
        this.userService = userService;
    }

    @Override
    public List<Case> getAllWithDocuments() {
        return caseRepository.findAllWithDocuments();
    }

    @Override
    public List<Case> getAll() {
        return caseRepository.findAll();
    }

    @Override
    public Optional<Case> getById(Long ID) {
        return caseRepository.findById(ID);
    }

    @Override
    public Case add(Case aCase) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(auth.getName()).map(user -> {
            aCase.setCreatorUser(user);
            return caseRepository.save(aCase);
        }).orElse(null);
    }

    @Override
    public void update(Case aCase) {
        try {
            Case dbCase = caseRepository.getOne(aCase.getID());
            dbCase.copy(aCase);
            caseRepository.save(dbCase);
        } catch(EntityNotFoundException e) {
            caseRepository.save(aCase);
        }
    }

    @Override
    public void removeById(long ID) {
        caseRepository.deleteById(ID);
    }

}
