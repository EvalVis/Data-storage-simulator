package ev.projects.models;

public class DocumentFactory {

    /**
     * Used then document is a child of case.
     * @param title - document title.
     * @param description - document description.
     * @param caseID - parent case ID (document FK).
     * @return - document ready to be created on DB.
     */
    public static Document createDocumentOfCase(String title, String description, long caseID) {
        Document document = new Document();
        document.setTitle(title);
        document.setDescription(description);

        Case owningCase = new Case();
        owningCase.setID(caseID);
        document.setOwningCase(owningCase);
        return document;
    }

    /**
     * Used then document is an attachment (when it's parent is another document).
     * @param title - attachment title.
     * @param description - attachment description.
     * @param documentID - parent document ID (attachment FK).
     * @return - attachment ready to be added to DB.
     */
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
