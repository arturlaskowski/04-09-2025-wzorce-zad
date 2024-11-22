package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientCommunicationProjectListener {

    private final ClientService clientService;

    @EventListener
    public void handleEvent(ProjectChangedEvent projectChangedEvent) {
        switch (projectChangedEvent.status()) {
            case PENDING -> clientService.createClientCommunicationForProject(projectChangedEvent.id());
            case IN_PROGRESS -> clientService.notifyClientOfProjectStart(projectChangedEvent.id());
            case COMPLETED -> clientService.notifyClientOfProjectEnd(projectChangedEvent.id());
        }
    }
}