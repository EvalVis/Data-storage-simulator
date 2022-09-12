package ev.projects.services;

import ev.projects.models.Document;
import ev.projects.repositories.IDocumentRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService implements IDocumentService {

    private String storageDirPath = System.getProperty("user.dir") + "/../data/";

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

    @Override
    public void uploadDocument(long documentID, MultipartFile file) {
        try {
            Document document = documentRepository.getOne(documentID);
            deletePreviousDocument(documentID);
            String originalFileName = file.getOriginalFilename();
            String mimeType = FilenameUtils.getExtension(originalFileName);
            String filePath = documentID + "_" +
                    originalFileName.replaceFirst("[.][^.]+$", "") + "." + mimeType;
            File newFile = new File(storageDirPath + "/" + filePath);
            newFile.createNewFile();
            file.transferTo(newFile);
            document.setFilePath(filePath);
            document.setFileSize(file.getSize());
            document.setMimeType(mimeType);
            update(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePreviousDocument(long documentID) {
        File storageDir = new File(storageDirPath);
        Arrays.stream(
                Objects.requireNonNull(
                        storageDir.listFiles((dir, name)
                -> name.startsWith(documentID + "_"))))
                .findFirst()
                .map(File::delete);
    }

    @Override
    public Resource getDocumentFile(long ID) {
        return documentRepository.findById(ID).
                map(Document::getFilePath).
                map(this::readFileFromStream).
                orElse(null);
    }

    private Resource readFileFromStream(String filePath) {
        String path = storageDirPath + "/" + filePath;
        try {
            return new ByteArrayResource(Files.readAllBytes(Path.of(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document add(Document document) {
        return Optional.ofNullable(document.getOwningCase()).map(c -> documentRepository.save(document))
                .orElse(Optional.ofNullable(document.getOwningDocument()).
                        flatMap(d -> documentRepository.findById(d.getID())
                                .filter(parent -> parent.getOwningDocument() == null)
                                .map(p -> documentRepository.save(document)))
                        .orElse(null));
    }

    @Override
    public boolean update(Document document) {
        return documentRepository.findById(document.getID()).map(d -> {
            d.copy(document);
            documentRepository.save(d);
            return true;
        }).orElse(false);
    }

    @Override
    public void removeById(long ID) {
        documentRepository.deleteById(ID);
    }
}
