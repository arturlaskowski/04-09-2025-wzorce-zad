package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientCommunicationProjectListener {

    private final ClientService clientService;

    @EventListener
    public void handleEvent(ProjectInitializedEvent projectInitializedEvent) {
        clientService.createClientCommunicationForProject(projectInitializedEvent.projectId());
    }

    @EventListener
    public void handleEvent(ProjectStartedEvent projectStartedEvent) {
        clientService.notifyClientOfProjectStart(projectStartedEvent.projectId());
    }

    @EventListener
    public void handleEvent(ProjectCompletedEvent projectCompletedEvent) {
        clientService.notifyClientOfProjectEnd(projectCompletedEvent.projectId());
    }
}