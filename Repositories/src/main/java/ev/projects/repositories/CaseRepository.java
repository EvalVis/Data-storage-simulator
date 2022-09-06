package ev.projects.repositories;

import ev.projects.models.Case;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CaseRepository  implements ICaseRepository {

    private static List<Case> cases = new ArrayList<>();

    @Override
    public List<Case> getAll() {
        return cases;
    }

    @Override
    public Optional<Case> getById(long ID) {
        return cases.stream().filter(c -> c.getID() == ID).findFirst();
    }
}
