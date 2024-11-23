package wzorce.strategy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findFirstByCustomerIdOrderBySendAtDesc(Long customerId);

    Optional<Notification> findByIdAndStatus(Long id, NotificationStatus status);

    @Query("SELECT n.id FROM Notification n WHERE n.status = :status")
    List<Long> findAllIdsByStatus(NotificationStatus status);
}
