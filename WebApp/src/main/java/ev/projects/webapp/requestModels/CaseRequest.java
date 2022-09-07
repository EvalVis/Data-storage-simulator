package ev.projects.webapp.requestModels;

import ev.projects.models.Case;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseRequest {

    private String title;
    private String description;

    public Case convertToCase(Case aCase) {
        aCase.setTitle(title);
        aCase.setDescription(description);
        return aCase;
    }

    public Case convertToCase() {
        return convertToCase(new Case());
    }

}
