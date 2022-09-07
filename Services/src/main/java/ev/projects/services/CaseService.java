package ev.projects.services;

import ev.projects.models.Case;
import ev.projects.repositories.ICaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
        return caseRepository.findAll();
    }

    @Override
    public Optional<Case> getAllById(Long ID) {
        return caseRepository.findById(ID);
    }

    @Override
    public void add(Case aCase) {
        caseRepository.save(aCase);
    }

    @Override
    public void update(Case aCase) {
        caseRepository.save(aCase);
    }

    @Override
    public void removeById(long ID) {
        caseRepository.deleteById(ID);
    }

}
