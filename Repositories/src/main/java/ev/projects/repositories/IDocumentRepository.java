package ev.projects.repositories;

import ev.projects.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findDocumentsByOwningCase_ID(long caseID);
    List<Document> findDocumentsByOwningDocument_ID(long documentID);

}
