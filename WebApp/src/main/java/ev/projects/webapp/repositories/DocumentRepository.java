package ev.projects.webapp.repositories;

import ev.projects.Document;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Repository
public class DocumentRepository implements IDocumentRepository {

    private HashMap<Long, List<Document>> documents = new HashMap<>();
    private HashMap<Long, List<Document>> attachments = new HashMap<>();

    @Override
    public List<Document> getAllDocumentsByCase(long caseID) {
        return getKeyValue(documents, caseID);
    }

    @Override
    public List<Document> getAllAttachmentsByDocument(long documentID) {
        return getKeyValue(attachments, documentID);
    }

    private List<Document> getKeyValue(HashMap<Long, List<Document>> hashMap, long key) {
        List<Document> values = hashMap.get(key);
        return values != null ? values : new ArrayList<>();
    }

}
