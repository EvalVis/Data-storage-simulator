package ev.projects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private long ID;
    private String title;
    private String description;
    private String filePath;
    private long fileSize;
    private String mimeType;

}
