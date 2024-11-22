package wzorce.observer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class ClientService {

    private final ClientCommunicationRepository clientCommunicationRepository;

    public void createClientCommunicationForProject(Long projectId) {
        var clientCommunication = new ClientCommunication(projectId);
        clientCommunicationRepository.save(clientCommunication);
        log.info("Stworzono obiekt do zarządzania komunikacją z klientem w ramach projektu: " + projectId);
    }

    @Transactional
    public void notifyClientOfProjectStart(Long projectId) {
        var clientCommunication = clientCommunicationRepository.findByProjectId(projectId).orElseThrow();
        clientCommunication.notifyClientOfProjectStart();
        log.info("Klient został powiadomiony o rozpoczęciu projektu: " + projectId);

    }

    @Transactional
    public void notifyClientOfProjectEnd(Long projectId) {
        var clientCommunication = clientCommunicationRepository.findByProjectId(projectId).orElseThrow();
        clientCommunication.notifyClientOfProjectEnd();
        log.info("Klient został powiadomiony o zakończeniu projektu: " + projectId);
    }
}
