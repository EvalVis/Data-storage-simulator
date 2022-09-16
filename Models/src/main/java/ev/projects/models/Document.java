package ev.projects.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Entity for storing documents. Documents can have case as a parent or another document as a parent.
 * A document which has another document as a parent is called an attachment.
 */
@JsonIdentityInfo(scope = Document.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String description;
    private String filePath;
    private long fileSize;
    private String mimeType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case owningCase;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document owningDocument;
    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "owningDocument")
    private List<Document> attachments;

    /**
     * Used on update then document entity acts like dto, to transfer data from this to DB tracked entity.
     * @param document - document entity acting like dto.
     */
    public void copy(Document document) {
        setTitle(document.getTitle());
        setDescription(document.getDescription());
    }

}
