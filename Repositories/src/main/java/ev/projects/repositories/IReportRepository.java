package ev.projects.repositories;

import ev.projects.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer class responsible for dealing with CRUD operations on Report entity.
 */
@Repository
public interface IReportRepository extends JpaRepository<Report, Long> {
}
