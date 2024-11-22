package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ProjectService {

    private final HRService hrService;
    private final ClientService clientService;
    private final FinanceService financeService;

    private final ProjectRepository projectRepository;

    public Long initProject(String projectName) {
        var project = new Project(projectName);
        var projectId = projectRepository.save(project).getId();

        hrService.assignProjectTeam(projectId);
        clientService.createClientCommunicationForProject(projectId);
        financeService.allocateInitialBudget(projectId);

        return projectId;
    }

    @Transactional
    public void startProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.start();

        clientService.notifyClientOfProjectStart(projectId);
    }

    @Transactional
    public void suspendProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.suspend();

        financeService.freezeBudget(projectId);
    }

    @Transactional
    public void completeProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.complete();

        hrService.reallocateResources(projectId);
        clientService.notifyClientOfProjectEnd(projectId);
        financeService.finalizeProjectAccounts(projectId);
    }
}
