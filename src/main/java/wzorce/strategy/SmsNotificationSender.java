package wzorce.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class SmsNotificationSender {

    public void sendNotification(Notification notification) {
        log.info("Wysyłam notyfikacje sms");
    }

}
