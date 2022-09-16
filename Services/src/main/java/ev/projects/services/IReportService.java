package ev.projects.services;

import ev.projects.models.Report;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * Business logic component for Report entity.
 */
@Service
public interface IReportService {

    /**
     * List all the reports.
     * @return list of reports.
     */
    List<Report> getAll();

    /**
     * Get a document, given an ID given.
     * @param ID PK of report to retrieve.
     * @return If exists, Report by a given ID.
     */
    Optional<Report> getById(long ID);

    /**
     * Create a new report.
     * @param report report to be created.
     */
    void add(Report report);

    /**
     * Get a report, given an ID.
     * @param ID PK of report to be retrieved.
     */
    void getReport(long ID, PrintWriter writer);

}
