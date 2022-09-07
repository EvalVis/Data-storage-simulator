package ev.projects.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Case {

    private long ID;
    private String title;
    private String description;
    private Date creationDate;
    private String creatorUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return ID == aCase.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
