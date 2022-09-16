package ev.projects.repositories;

import ev.projects.models.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Persistence layer class responsible for dealing with CRUD operations on Case entity.
 */
@Repository
public interface ICaseRepository extends JpaRepository<Case, Long> {
    /**
     * Eager loading of cases.
     * @return - list of cases whose also have list of loaded documents.
     */
    @Query(value = "SELECT c FROM Case c JOIN FETCH c.documents JOIN FETCH c.creatorUser")
    List<Case> findAllWithDocuments();
}
