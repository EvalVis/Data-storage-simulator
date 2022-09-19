package ev.projects.repositories;

import ev.projects.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Persistence layer class responsible for dealing with CRUD operations on Document entity.
 */
@Repository
public interface IDocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Find all children of the case by providing case's ID.
     * @param caseID - document's parent case ID (FK).
     * @return - list of documents which are children of case.
     */
    List<Document> findDocumentsByOwningCase_ID(long caseID);

    /**
     * Find all children of the document by providing document's ID.
     * @param documentID - document's parent document ID (FK).
     * @return - list of attachments which are children of documents.
     */
    List<Document> findDocumentsByOwningDocument_ID(long documentID);

}
