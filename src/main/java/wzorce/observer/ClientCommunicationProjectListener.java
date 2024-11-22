package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientCommunicationProjectListener implements ProjectListener {

    private final ClientService clientService;

    @Override
    public void update(ProjectDto projectDto) {
        switch (projectDto.status()) {
            case PENDING -> clientService.createClientCommunicationForProject(projectDto.id());
            case IN_PROGRESS -> clientService.notifyClientOfProjectStart(projectDto.id());
            case COMPLETED -> clientService.notifyClientOfProjectEnd(projectDto.id());
        }
    }
}