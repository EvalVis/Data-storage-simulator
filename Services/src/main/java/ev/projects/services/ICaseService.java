package ev.projects.services;

import ev.projects.models.Case;

import java.util.List;
import java.util.Optional;

public interface ICaseService {

    List<Case> getAll();
    Optional<Case> getAllById(Long ID);

}
