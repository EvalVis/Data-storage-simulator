package ev.projects.models;

import java.util.List;

public class DocumentFactory {

    public static Document createDocumentOfCase(String title, String description, long caseID) {
        Document document = new Document();
        document.setTitle(title);
        document.setDescription(description);

        Case owningCase = new Case();
        owningCase.setID(caseID);
        document.setOwningCase(owningCase);
        return document;
    }

    public static Document createAttachmentOfDocument(String title, String description, long documentID) {
        Document document = new Document();
        document.setTitle(title);
        document.setDescription(description);

        Document parentDocument = new Document();
        parentDocument.setID(documentID);
        document.setOwningDocument(parentDocument);
        return document;
    }

}
