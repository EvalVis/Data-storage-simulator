package ev.projects.services;

import ev.projects.models.Document;
import ev.projects.repositories.IDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService implements IDocumentService {

    private IDocumentRepository documentRepository;

    @Autowired
    public DocumentService(IDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    public List<Document> getAllDocumentsByCase(long caseID) {
        return documentRepository.getAllDocumentsByCase(caseID);
    }

    @Override
    public List<Document> getAllAttachmentsByDocument(long documentID) {
        return documentRepository.getAllAttachmentsByDocument(documentID);
    }

    @Override
    public byte[] getDocumentFile(long ID) {
        return documentRepository.getById(ID).
                map(Document::getFilePath).
                map(this::readFileFromStream).
                orElse(null);
    }

    private byte[] readFileFromStream(String filePath) {
        try {
            return Objects.requireNonNull(getClass().getResourceAsStream(filePath)).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
