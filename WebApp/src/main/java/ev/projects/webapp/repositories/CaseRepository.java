package ev.projects.webapp.repositories;

import ev.projects.Case;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CaseRepository extends EntityRepository<Case> {

    @Override
    public List<Case> getAll() {
        return cases;
    }

    @Override
    public Optional<Case> getByID(long ID) {
        return cases.stream().filter(c -> c.getID() == ID).findFirst();
    }

    @Override
    public void save(Case aCase) {
        cases.add(aCase);
    }

}
