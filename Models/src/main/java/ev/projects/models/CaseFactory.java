package ev.projects.models;

public class CaseFactory {

    /**
     * Create a DB ready case.
     * @param title - case title.
     * @param description - description title.
     * @param user - owner entity.
     * @return - case ready to be added to DB.
     */
    public static Case createCase(String title, String description, User user) {
        Case aCase = new Case();
        aCase.setTitle(title);
        aCase.setDescription(description);
        aCase.setCreatorUser(user);
        return aCase;
    }

}
