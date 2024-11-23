package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ProjectService {

    private final ProjectRepository projectRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Long initProject(String projectName) {
        var project = new Project(projectName);
        var projectId = projectRepository.save(project).getId();
        applicationEventPublisher.publishEvent(new ProjectInitializedEvent(projectId, projectName));
        return projectId;
    }

    @Transactional
    public void startProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.start();
        applicationEventPublisher.publishEvent(new ProjectStartedEvent(projectId));
    }

    @Transactional
    public void suspendProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.suspend();
        applicationEventPublisher.publishEvent(new ProjectSuspendedEvent(projectId));
    }

    @Transactional
    public void completeProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.complete();
        applicationEventPublisher.publishEvent(new ProjectCompletedEvent(projectId));
    }
}