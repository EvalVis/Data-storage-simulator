package ev.projects.models;

import java.util.List;

public class DocumentFactory {

    public static Document createDocument(String title, String description, long caseID, long documentID) {
        Document document = new Document();
        document.setTitle(title);
        document.setDescription(description);
        if(caseID > 0) {
            Case owningCase = new Case();
            owningCase.setDocuments(List.of(document));
            owningCase.setID(caseID);
            document.setOwningCase(owningCase);
        }
        else if(documentID > 0) {
            Document parentDocument = new Document();
            parentDocument.setAttachments(List.of(document));
            parentDocument.setID(documentID);
            document.setOwningDocument(parentDocument);
        }
        return document;
    }

}
