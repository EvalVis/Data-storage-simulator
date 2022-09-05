package ev.projects.webapp.utils;

import ev.projects.models.Case;
import ev.projects.models.Document;

import java.util.*;

public class MockData {

    public static List<Case> generateCases() {
        Case englishCase = new Case(1, "English", new Date(), "Smith");
        Case mathCase = new Case(2, "Math", new Date(), "Mike");
        return new ArrayList<>(Arrays.asList(englishCase, mathCase));
    }

    public static HashMap<Long, List<Document>> generateDocuments() {
        Document englishLesson1 = new Document(1, "English lesson 1", "First lesson",
                "data/image.jpg", 139_191, "jpg");
        Document mathLesson1 = new Document(1, "Math lesson 1", "Hard lesson",
                "data/image.jpg", 139_191, "jpg");
        HashMap<Long, List<Document>> documents = new HashMap<>();
        documents.put(1L, List.of(englishLesson1));
        documents.put(2L, List.of(mathLesson1));
        return documents;
    }

    public static HashMap<Long, List<Document>> generateAttachments() {
        Document englishHomework1 = new Document(1, "English homework 1", "Reading exercise",
                "data/homework.jpg", 16, "txt");
        HashMap<Long, List<Document>> attachments = new HashMap<>();
        attachments.put(1L, List.of(englishHomework1));
        return attachments;
    }

}
