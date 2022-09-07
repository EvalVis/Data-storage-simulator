package ev.projects.repositories;

import ev.projects.models.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaseRepository extends JpaRepository<Case, Long> {

}
