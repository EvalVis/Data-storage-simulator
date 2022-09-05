package ev.projects.webapp.repositories;

import ev.projects.Case;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Repository
public class CaseRepository  implements ICaseRepository {

    private List<Case> cases = new ArrayList<>();

    @Override
    public List<Case> getAll() {
        return cases;
    }

    @Override
    public Optional<Case> getById(long ID) {
        return cases.stream().filter(c -> c.getID() == ID).findFirst();
    }
}
