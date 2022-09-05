package ev.projects.webapp.services;

import ev.projects.Case;

import java.util.List;
import java.util.Optional;

public interface ICaseService {

    List<Case> getAll();
    Optional<Case> getAllById(Long ID);

}
