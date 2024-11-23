package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class HRManagementProjectListener {

    private final HRService hrService;

    @EventListener
    public void handleEvent(ProjectInitializedEvent projectInitializedEvent) {
        hrService.assignProjectTeam(projectInitializedEvent.projectId());
    }

    @EventListener
    public void handleEvent(ProjectCompletedEvent projectCompletedEvent) {
        hrService.reallocateResources(projectCompletedEvent.projectId());
    }
}