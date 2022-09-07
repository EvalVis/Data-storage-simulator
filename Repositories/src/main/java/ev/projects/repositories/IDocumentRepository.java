package ev.projects.repositories;

import ev.projects.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findDocumentsByOwningCase_ID(long caseID);
    List<Document> findDocumentsByOwningDocument_ID(long documentID);

}
