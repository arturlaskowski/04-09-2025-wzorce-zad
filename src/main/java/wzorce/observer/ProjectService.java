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
        sendProjectChangedEvent(project);
        return projectId;
    }

    @Transactional
    public void startProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.start();
        sendProjectChangedEvent(project);
    }

    @Transactional
    public void suspendProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.suspend();
        sendProjectChangedEvent(project);
    }

    @Transactional
    public void completeProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.complete();
        sendProjectChangedEvent(project);
    }

    private void sendProjectChangedEvent(Project project) {
        var projectChangedEvent = new ProjectChangedEvent(project.getId(), project.getName(), project.getStatus());
        applicationEventPublisher.publishEvent(projectChangedEvent);
    }
}