package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProjectService {

    private final ProjectRepository projectRepository;
    private final List<ProjectListener> projectListeners;

    public Long initProject(String projectName) {
        var project = new Project(projectName);
        var projectId = projectRepository.save(project).getId();
        notifyProjectListeners(project);
        return projectId;
    }

    @Transactional
    public void startProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.start();
        notifyProjectListeners(project);
    }

    @Transactional
    public void suspendProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.suspend();
        notifyProjectListeners(project);
    }

    @Transactional
    public void completeProject(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        project.complete();
        notifyProjectListeners(project);
    }

    private void notifyProjectListeners(Project project) {
        projectListeners.forEach(projectListener ->
                projectListener.update(new ProjectDto(project.getId(), project.getName(), project.getStatus())));
    }
}