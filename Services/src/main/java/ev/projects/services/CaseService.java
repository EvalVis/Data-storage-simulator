package ev.projects.services;

import ev.projects.models.Case;
import ev.projects.repositories.ICaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public List<Case> getAll() {
        return caseRepository.findAll();
    }

    @Override
    public Optional<Case> getById(Long ID) {
        return caseRepository.findById(ID);
    }

    @Override
    public void add(Case aCase) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.getByUsername(auth.getName()).map(user -> {
            aCase.setCreatorUser(user);
            caseRepository.save(aCase);
            return null;
        });
    }

    @Override
    public boolean update(Case aCase) {
        return caseRepository.findById(aCase.getID()).map(c -> {
            c.copy(aCase);
            caseRepository.save(c);
            return true;
        }).orElse(false);
    }

    @Override
    public void removeById(long ID) {
        caseRepository.deleteById(ID);
    }

}
