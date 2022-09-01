package ev.projects;

import java.util.Optional;

public class File {

    private long ID;
    private String title;
    private String description;
    private String filePath;
    private long fileSize;
    private String mimeType;
    private Optional<Document> document;
    private Optional<Attachment> attachment;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Optional<Document> getDocument() {
        return document;
    }

    public void setDocument(Optional<Document> document) {
        this.document = document;
    }

    public Optional<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(Optional<Attachment> attachment) {
        this.attachment = attachment;
    }
}
