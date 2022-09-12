package ev.projects.webapp.utils;

import ev.projects.models.Case;
import ev.projects.models.Document;
import ev.projects.models.User;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EntitiesCreator {

    public static Case createCase(String title, String description, User user) {
        Case aCase = new Case();
        aCase.setTitle(title);
        aCase.setDescription(description);
        aCase.setCreatorUser(user);
        aCase.setCreationDate(new Date());
        return aCase;
    }

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
