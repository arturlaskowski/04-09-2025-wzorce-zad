package wzorce.strategy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String messageTemplate;
    private NotificationChannel channel;
    private NotificationStatus status;
    private LocalDateTime sendAt;

    public void send() {
        sendAt = LocalDateTime.now();
        status = NotificationStatus.SENT;
    }
}
