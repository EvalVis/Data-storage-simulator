package ev.projects.repositories;

import ev.projects.models.Case;

import java.util.List;
import java.util.Optional;

public interface ICaseRepository {

    List<Case> getAll();
    Optional<Case> getById(long ID);

}
