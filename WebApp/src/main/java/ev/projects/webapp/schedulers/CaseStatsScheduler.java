package ev.projects.webapp.schedulers;

import ev.projects.models.Case;
import ev.projects.models.Document;
import ev.projects.models.Report;
import ev.projects.services.ICaseService;
import ev.projects.services.IDocumentService;
import ev.projects.services.IReportService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Class for collecting statistics about case using scheduler.
 */
@Component
public class CaseStatsScheduler {

    @Autowired
    private ICaseService caseService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IDocumentService documentService;

    /**
     * Collects report data and adds a report to DB.
     */
    @Scheduled(fixedRateString = "${scheduler.rate}")
    public void logStats() {
        CaseStats caseStats = findBiggestCase();
        Case biggestCase = caseStats.getBiggestCase();
        if(biggestCase != null) {
            String dataToLog = "Biggest case has size of (" + caseStats.getSize() +
                    " bytes) and following data:" + biggestCase.toString();
            Report report = new Report(0, new Date(), dataToLog);
            reportService.add(report);
        }
    }

    private CaseStats findBiggestCase() {
        List<Case> cases = caseService.getAllWithDocuments();
        long maxSize = 0;
        Case biggestCase = null;
        for(var aCase : cases) {
            if(aCase != null) {
                long size = calculateDocumentsSize(aCase.getDocuments());
                if (size > maxSize) {
                    maxSize = size;
                    biggestCase = aCase;
                }
            }
        }
        return new CaseStats(biggestCase, maxSize);
    }

    private long calculateDocumentsSize(List<Document> documents) {
        long size = 0;
        for(Document document : documents) {
            if(document != null) {
                size += document.getFileSize();
                size += calculateDocumentsSize(documentService.getAllAttachmentsByDocument(document.getID()));
            }
        }
        return size;
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
