package ev.projects.webapp.repositories;

import ev.projects.Case;
import ev.projects.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentRepository extends EntityRepository<Document> {

    @Override
    public List<Document> getAll() {
        List<Document> documents = new ArrayList<>();
        for(Case aCase : cases) {
            if(aCase != null) {
                documents.addAll(aCase.getDocuments());
            }
        }
        return documents;
    }

    public List<Document> getAllOfCase(long caseID) {
        for(Case aCase : cases) {
            if(aCase != null && aCase.getID() == caseID) {
                return aCase.getDocuments();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Document> getByID(long ID) {
        for(Case aCase : cases) {
            if(aCase != null) {
                for(Document document : aCase.getDocuments()) {
                    if(document != null) {
                        File file = document.getFile();
                        if(file != null && file.getID() == ID) {
                            return Optional.of(document);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Document document) {
        if(document != null) {
            for (Case aCase : cases) {
                Case parentCase = document.getParentCase();
                if(parentCase != null && parentCase.equals(aCase)) {
                    aCase.getDocuments().add(document);
                }
            }
        }
    }

}
