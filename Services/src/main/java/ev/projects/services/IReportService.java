package ev.projects.services;

import ev.projects.models.Report;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public interface IReportService {

    List<Report> getAll();

    Optional<Report> getById(long ID);
    void add(Report report);

    void getLog(long ID, PrintWriter writer);

}
