package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class HRManagementProjectListener implements ProjectListener {

    private final HRService hrService;

    @Override
    public void update(ProjectDto projectDto) {
        switch (projectDto.status()) {
            case PENDING -> hrService.assignProjectTeam(projectDto.id());
            case COMPLETED -> hrService.reallocateResources(projectDto.id());
        }
    }
}