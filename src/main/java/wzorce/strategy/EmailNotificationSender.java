package wzorce.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class EmailNotificationSender implements NotificationSender {

    public void sendNotificationImmediately(NotificationDto notificationDto) {
        log.info("Wysyłame email natychcmiastowo!");
    }

    @Override
    public void sendNotification(Notification notification) {
        log.info("Wysyłam notyfikacje email");
    }

    @Override
    public NotificationChannel notificationChannel() {
        return NotificationChannel.EMAIL;
    }
}
