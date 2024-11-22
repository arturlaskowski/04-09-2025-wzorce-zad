package wzorce.observer;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private HRManagementRepository hrManagementRepository;

    @Autowired
    private FinanceManagementRepository financeManagementRepository;

    @Autowired
    private ClientCommunicationRepository clientCommunicationRepository;

    @AfterEach
    void cleanUp() {
        projectRepository.deleteAll();
        hrManagementRepository.deleteAll();
        financeManagementRepository.deleteAll();
        clientCommunicationRepository.deleteAll();
    }

    @Test
    void initProject() {
        //when
        var projectId = projectService.initProject("Projekcik!");

        //then
        assertThat(hrManagementRepository.findByProjectId(projectId)).isPresent();
        assertThat(financeManagementRepository.findByProjectId(projectId)).isPresent();
        assertThat(clientCommunicationRepository.findByProjectId(projectId)).isPresent();
    }

    @Test
    void startProject() {
        //given
        var projectId = projectService.initProject("Projekcik!");

        //when
        projectService.startProject(projectId);

        //then
        assertThat(clientCommunicationRepository.findByProjectId(projectId))
                .isPresent()
                .get()
                .extracting(ClientCommunication::isClientNotifiedOfProjectStart)
                .isEqualTo(true);
    }

    @Test
    void suspendProject() {
        //given
        var projectId = projectService.initProject("Projekcik!");
        projectService.startProject(projectId);

        //when
        projectService.suspendProject(projectId);

        //then
        assertThat(financeManagementRepository.findByProjectId(projectId))
                .isPresent()
                .get()
                .extracting(FinanceManagement::isFreezeBudget)
                .isEqualTo(true);
    }

    @Test
    void completeProject() {
        //given
        var projectId = projectService.initProject("Projekcik!");
        projectService.startProject(projectId);

        //when
        projectService.completeProject(projectId);

        //then
        assertThat(hrManagementRepository.findByProjectId(projectId))
                .isPresent()
                .get()
                .extracting(HRManagement::isReallocateResources)
                .isEqualTo(true);

        assertThat(financeManagementRepository.findByProjectId(projectId))
                .isPresent()
                .get()
                .extracting(FinanceManagement::isFinalizeProjectAccounts)
                .isEqualTo(true);

        assertThat(clientCommunicationRepository.findByProjectId(projectId))
                .isPresent()
                .get()
                .extracting(ClientCommunication::isClientNotifiedOfProjectEnd)
                .isEqualTo(true);
    }

    @Test
    void projectServiceShouldBeIndependentFromClientFinanceAndHRServices() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.observer");

        var rule = noClasses()
                .that().areAssignableTo(ProjectService.class)
                .should().dependOnClassesThat().areAssignableTo(ClientService.class)
                .orShould().dependOnClassesThat().areAssignableTo(FinanceService.class)
                .orShould().dependOnClassesThat().areAssignableTo(HRService.class)
                .because("Jeśli zastosujemy wzorzec Obserwator, nie będziemy mieli bezpośredniej zależności między obiektami ProjectService," +
                        " a klasami nasłuchującymi zmian w tych obiektach.");

        rule.check(importedClasses);
    }
}