package ev.projects.webapp.schedulers;

import ev.projects.models.Case;
import ev.projects.models.Document;
import ev.projects.models.Log;
import ev.projects.services.ICaseService;
import ev.projects.services.IDocumentService;
import ev.projects.services.ILogService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CaseStatsScheduler {

    private ICaseService caseService;
    private ILogService logService;
    private IDocumentService documentService;

    @Autowired
    public CaseStatsScheduler(ICaseService caseService, ILogService logService, IDocumentService documentService) {
        this.caseService = caseService;
        this.logService = logService;
        this.documentService = documentService;
    }

    @Scheduled(fixedRateString = "${scheduler.rate}")
    public void logStats() {
        CaseStats caseStats = findBiggestCase();
        Case biggestCase = caseStats.getBiggestCase();
        if(biggestCase != null) {
            String dataToLog = "Biggest case has size of (" + caseStats.getSize() +
                    " bytes) and following data:" + biggestCase.toString();
            Log log = new Log(0, new Date(), dataToLog);
            logService.add(log);
        }
    }

    public CaseStats findBiggestCase() {
        List<Case> cases = caseService.getAllWithDocuments();
        long maxSize = 0;
        Case biggestCase = null;
        for(var aCase : cases) {
            long size = 0;
            if(aCase == null) {
                continue;
            }
            for(var document : aCase.getDocuments()) {
                if(document == null) {
                    continue;
                }
                size += document.getFileSize();
                for(var attachment : documentService.getAllAttachmentsByDocument(document.getID())) {
                    if(attachment == null) {
                        continue;
                    }
                    size += attachment.getFileSize();
                }
            }
            if(size > maxSize) {
                maxSize = size;
                biggestCase = aCase;
            }
        }
        return new CaseStats(biggestCase, maxSize);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CaseStats {
        private Case biggestCase;
        private long size;
    }

}
