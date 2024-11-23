package wzorce.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class EmailNotificationSender {

    public void sendNotificationImmediately(NotificationDto notificationDto) {
        log.info("Wysyłame email natychcmiastowo!");
    }

    public void sendNotification(Notification notification) {
        log.info("Wysyłam notyfikacje email");
    }

}
