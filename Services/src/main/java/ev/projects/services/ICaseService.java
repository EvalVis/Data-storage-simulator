package ev.projects.services;

import ev.projects.models.Case;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICaseService {

    List<Case> getAll();
    Optional<Case> getById(Long ID);
    Case add(Case aCase);
    boolean update(Case aCase);
    void removeById(long ID);

}
