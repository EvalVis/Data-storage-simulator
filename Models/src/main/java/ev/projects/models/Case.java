package ev.projects.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(scope = Case.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cases") // Case is a keyword in db, so table is named cases instead.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String description;
    @CreationTimestamp
    private Date creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creatorUser;
    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, mappedBy = "owningCase")
    private List<Document> documents;

    public void copy(Case aCase) {
        setTitle(aCase.getTitle());
        setDescription(aCase.getDescription());
    }

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
