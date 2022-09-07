package ev.projects.repositories;

import ev.projects.models.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaseRepository extends JpaRepository<Case, Long> {

}
