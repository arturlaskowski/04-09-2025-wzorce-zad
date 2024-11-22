package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class HRManagementProjectListener {

    private final HRService hrService;

    @EventListener
    public void handleEvent(ProjectChangedEvent projectChangedEvent) {
        switch (projectChangedEvent.status()) {
            case PENDING -> hrService.assignProjectTeam(projectChangedEvent.id());
            case COMPLETED -> hrService.reallocateResources(projectChangedEvent.id());
        }
    }
}