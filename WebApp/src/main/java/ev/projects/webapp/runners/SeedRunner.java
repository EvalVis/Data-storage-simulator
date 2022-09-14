package ev.projects.webapp.runners;

import ev.projects.models.User;
import ev.projects.repositories.ICaseRepository;
import ev.projects.services.IDocumentService;
import ev.projects.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static ev.projects.models.CaseFactory.createCase;
import static ev.projects.models.DocumentFactory.createAttachmentOfDocument;
import static ev.projects.models.DocumentFactory.createDocumentOfCase;

@Component
@Profile("!test")
public class SeedRunner implements CommandLineRunner {

    private IUserService userService;
    private ICaseRepository caseRepository;
    private IDocumentService documentService;

    @Autowired
    public SeedRunner(IUserService userService, ICaseRepository caseRepository, IDocumentService documentService) {
        this.userService = userService;
        this.caseRepository = caseRepository;
        this.documentService = documentService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("tester", "tester");
        userService.add(user);
        caseRepository.save(createCase("English", "English lessons", user));
        documentService.add(createDocumentOfCase("First English lesson", "Difficult lesson", 1));
        documentService.add(createAttachmentOfDocument("English homework", "Why? On the first day of school?", 1));
    }

}
