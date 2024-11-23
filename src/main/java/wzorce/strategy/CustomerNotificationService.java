package wzorce.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class CustomerNotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationSenderFactory notificationSenderFactory;

    public void scheduleNotification(Customer customer) {
        var notification = Notification.builder()
                .customerId(customer.getId())
                .messageTemplate(CustomerNotificationTemplate.WELCOME_MESSAGE.name())
                .channel(customer.getContact().communicationPreference())
                .status(NotificationStatus.PENDING)
                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public void sendNotificationToCustomer(Long notificationId) {
        var notification = notificationRepository.findByIdAndStatus(notificationId, NotificationStatus.PENDING).orElseThrow(() ->
                new NotificationSendException("Notification with ID " + notificationId + " is not pending and cannot be sent."));

        var notificationSender = notificationSenderFactory.getNotificationSender(notification.getChannel());
        notificationSender.sendNotification(notification);

        notification.send();
    }
}
