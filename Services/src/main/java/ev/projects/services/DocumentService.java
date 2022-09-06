package ev.projects.services;

import ev.projects.models.Document;
import ev.projects.repositories.IDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@ComponentScan({"ev.projects.repositories"})
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
    public byte[] getDocumentFile(long ID) throws Exception {
        Optional<Document> document = documentRepository.getById(ID);
        if(document.isPresent()) {
            String filePath = document.get().getFilePath();
            if(filePath != null) {
                return Objects.requireNonNull(getClass().getResourceAsStream(filePath)).readAllBytes();
            }
        }
        return new byte[0];
    }
}
