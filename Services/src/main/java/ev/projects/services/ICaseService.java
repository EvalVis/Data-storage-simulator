package ev.projects.services;

import ev.projects.models.Case;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business logic component for Case entity.
 */
@Service
public interface ICaseService {

    /**
     * Eagerly retrieve cases.
     * @return list of cases with each case containing list of documents.
     */
    List<Case> getAllWithDocuments();

    /**
     * Lazily retrieve cases.
     * @return list of cases.
     */
    List<Case> getAll();

    /**
     * @param ID PK of case to be retrieved.
     * @return Case, if exists by given ID.
     */
    Optional<Case> getById(Long ID);

    /**
     * Create a case in DB.
     * @param aCase case to be created.
     * @return case which now has an ID filled.
     */
    Case add(Case aCase);

    /**
     * Update case in DB.
     * @param aCase updated case data.
     */
    void update(Case aCase);

    /**
     * Delete a case by a given ID.
     * @param ID PK of case to be deleted.
     */
    void removeById(long ID);

}
