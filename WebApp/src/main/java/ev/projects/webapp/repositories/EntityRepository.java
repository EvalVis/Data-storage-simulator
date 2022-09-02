package ev.projects.webapp.repositories;

import ev.projects.Case;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class EntityRepository<T> {

    public List<Case> cases = new ArrayList<>();
    public abstract List<T> getAll();
    public abstract Optional<T> getByID(long ID);

    public abstract void save(T t);

}
