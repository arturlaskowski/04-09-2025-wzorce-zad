package wzorce.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class SmsNotificationSender implements NotificationSender {

    public void sendNotification(Notification notification) {
        log.info("Wysyłam notyfikacje sms");
    }

    @Override
    public NotificationChannel notificationChannel() {
        return NotificationChannel.SMS;
    }

}
