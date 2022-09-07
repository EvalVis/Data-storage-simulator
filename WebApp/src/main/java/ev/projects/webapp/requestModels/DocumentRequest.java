package ev.projects.webapp.requestModels;

import ev.projects.models.Case;
import ev.projects.models.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {

    private String title;
    private String description;

    public Document convertToDocument(Document document) {
        document.setTitle(title);
        document.setDescription(description);
        return document;
    }

    public Document convertToDocument() {
        return convertToDocument(new Document());
    }

}
