package ev.projects.webapp.repositories;

import ev.projects.Case;

import java.util.List;
import java.util.Optional;

public interface ICaseRepository {

    List<Case> getAll();
    Optional<Case> getById(long ID);

}
