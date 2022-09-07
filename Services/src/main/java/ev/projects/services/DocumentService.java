package ev.projects.services;

import ev.projects.models.Document;
import ev.projects.repositories.IDocumentRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
    public Optional<Document> getById(long ID) {
        return documentRepository.findById(ID);
    }

    @Override
    public List<Document> getAllDocumentsByCase(long caseID) {
        return documentRepository.findDocumentsByOwningCase_ID(caseID);
    }

    @Override
    public List<Document> getAllAttachmentsByDocument(long documentID) {
        return documentRepository.findDocumentsByOwningDocument_ID(documentID);
    }

    @Value("document_storage.location")
    private String storageDirPath;
    @Override
    public void uploadDocument(long documentID, MultipartFile file) {
        try {
            Document document = documentRepository.getOne(documentID);
            deletePreviousDocument(documentID);
            String filePath = file.getName() + "_" + documentID;
            File newFile = new File("/" + storageDirPath + "/" + filePath);
            newFile.createNewFile();
            file.transferTo(newFile);
            document.setFilePath(filePath);
            document.setFileSize(file.getSize());
            String mimeType = FilenameUtils.getExtension(file.getOriginalFilename());
            document.setMimeType(mimeType);
            update(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePreviousDocument(long documentID) {
        File storageDir = new File("/" + storageDirPath + "/");
        Arrays.stream(
                Objects.requireNonNull(
                        storageDir.listFiles((dir, name)
                -> name.endsWith("_" + documentID))))
                .findFirst()
                .map(File::delete);
    }

    @Override
    public byte[] getDocumentFile(long ID) {
        return documentRepository.findById(ID).
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
