package ev.projects.services;

import ev.projects.models.Report;
import ev.projects.repositories.IReportRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService implements IReportService {

    @Autowired
    private IReportRepository reportRepository;

    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getById(long ID) {
        return reportRepository.findById(ID);
    }

    @Override
    public void add(Report report) {
        reportRepository.save(report);
    }

    @Override
    public void getLog(long ID, PrintWriter writer) {
        reportRepository.findById(ID).map(l -> {
            extractDataAsCsv(List.of(l), writer);
            return null;
        });
    }

    private void extractDataAsCsv(List<Report> reports, PrintWriter writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (var log : reports) {
                csvPrinter.printRecord(log.getID(), log.getDate(), log.getInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
