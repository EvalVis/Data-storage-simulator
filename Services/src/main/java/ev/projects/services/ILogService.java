package ev.projects.services;

import ev.projects.models.Log;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public interface ILogService {

    List<Log> getAll();

    Optional<Log> getById(long ID);
    void add(Log log);

    void getLog(long ID, PrintWriter writer);

}
