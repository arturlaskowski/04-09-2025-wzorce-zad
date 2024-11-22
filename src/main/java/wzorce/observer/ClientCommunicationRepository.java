package wzorce.observer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ClientCommunicationRepository extends JpaRepository<ClientCommunication, Long> {

    Optional<ClientCommunication> findByProjectId(Long projectId);
}
