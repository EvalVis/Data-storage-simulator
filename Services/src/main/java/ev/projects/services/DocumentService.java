package ev.projects.services;

import ev.projects.models.Document;
import ev.projects.repositories.IDocumentRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class DocumentService implements IDocumentService {

    private IDocumentRepository documentRepository;

    @Autowired
    public DocumentService(IDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    public List<Document> getAllDocumentsByCase(long caseID) {
        return documentRepository.findDocumentsByOwningCase_ID(caseID);
    }

    @Override
    public List<Document> getAllAttachmentsByDocument(long documentID) {
        return documentRepository.findDocumentsByOwningDocument_ID(documentID);
    }

    @Override
    public byte[] getDocumentFile(long ID) {
        return documentRepository.findById(ID).
                map(Document::getFilePath).
                map(this::readFileFromStream).
                orElse(null);
    }

    @Override
    public void uploadDocument() {
        throw new NotYetImplementedException();
    }

    private byte[] readFileFromStream(String filePath) {
        try {
            return Objects.requireNonNull(getClass().getResourceAsStream(filePath)).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Document document) {
        documentRepository.save(document);
    }

    @Override
    public void update(Document document) {
        documentRepository.save(document);
    }

    @Override
    public void removeById(long ID) {
        documentRepository.deleteById(ID);
    }
}
