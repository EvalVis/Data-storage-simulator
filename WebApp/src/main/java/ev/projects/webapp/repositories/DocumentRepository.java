package ev.projects.webapp.repositories;

import ev.projects.models.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentRepository implements IDocumentRepository {

    private static HashMap<Long, List<Document>> documents = new HashMap<>();
    private HashMap<Long, List<Document>> attachments = new HashMap<>();

    @Override
    public Optional<Document> getById(long ID) {
        Optional<Document> document = searchForDocument(documents, ID);
        if(document.isPresent()) {
            return document;
        }
        return searchForDocument(attachments, ID);
    }

    @Override
    public List<Document> getAllDocumentsByCase(long caseID) {
        return getKeyValue(documents, caseID);
    }

    @Override
    public List<Document> getAllAttachmentsByDocument(long documentID) {
        return getKeyValue(attachments, documentID);
    }

    private Optional<Document> searchForDocument(HashMap<Long, List<Document>> hashMap, long ID) {
        for(List<Document> documentList : hashMap.values()) {
            if(documentList != null) {
                return documentList.stream().filter(d-> d.getID() == ID).findFirst();
            }
        }
        return Optional.empty();
    }

    private List<Document> getKeyValue(HashMap<Long, List<Document>> hashMap, long key) {
        List<Document> values = hashMap.get(key);
        return values != null ? values : new ArrayList<>();
    }

}
