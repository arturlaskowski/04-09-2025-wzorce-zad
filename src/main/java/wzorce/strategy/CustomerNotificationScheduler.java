package wzorce.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class CustomerNotificationScheduler {

    private final NotificationRepository notificationRepository;
    private final CustomerNotificationService customerNotificationService;

    /**
     * To pewnie by był jakiś scheduler i trzeba by zabezpieczyć przed możliwością pobrania
     * zbyt wielu wiadomości z bazki i race condition w przypadku więcej niż jednej
     * instancji tej aplikacji. Na tę chwilę tym się nie przejmujemy :)
     */
    public void sendPendingNotifications() {
        notificationRepository.findAllIdsByStatus(NotificationStatus.PENDING)
                .forEach(customerNotificationService::sendNotificationToCustomer);
    }
}
