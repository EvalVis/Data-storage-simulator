package ev.projects.webapp.services;

import ev.projects.models.Case;
import ev.projects.webapp.repositories.ICaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseService implements ICaseService {

    private ICaseRepository caseRepository;

    @Autowired
    public CaseService(ICaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public List<Case> getAll() {
        return caseRepository.getAll();
    }

    @Override
    public Optional<Case> getAllById(Long ID) {
        return caseRepository.getById(ID);
    }
}
