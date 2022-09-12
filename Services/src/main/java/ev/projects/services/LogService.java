package ev.projects.services;

import ev.projects.models.Log;
import ev.projects.repositories.ILogRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class LogService implements ILogService {

    private ILogRepository logRepository;

    @Autowired
    public LogService(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> getAll() {
        return logRepository.findAll();
    }

    @Override
    public Optional<Log> getById(long ID) {
        return logRepository.findById(ID);
    }

    @Override
    public void add(Log log) {
        logRepository.save(log);
    }

    @Override
    public void getLog(long ID, PrintWriter writer) {
        logRepository.findById(ID).map(l -> {
            extractDataAsCsv(List.of(l), writer);
            return null;
        });
    }

    private void extractDataAsCsv(List<Log> logs, PrintWriter writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (var log : logs) {
                csvPrinter.printRecord(log.getID(), log.getDate(), log.getInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
