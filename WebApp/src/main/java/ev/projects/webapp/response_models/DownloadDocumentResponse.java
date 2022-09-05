package ev.projects.webapp.response_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DownloadDocumentResponse {

    private byte[] downloadData;

}
