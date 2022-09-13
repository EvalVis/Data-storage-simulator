package ev.projects.models;

public class CaseFactory {

    public static Case createCase(String title, String description, User user) {
        Case aCase = new Case();
        aCase.setTitle(title);
        aCase.setDescription(description);
        aCase.setCreatorUser(user);
        return aCase;
    }

}
