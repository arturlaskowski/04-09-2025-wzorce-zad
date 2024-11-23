package wzorce.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class PushNotificationSender {

    public void configureAPNS(String certificatePath, String certificatePassword) {
        log.info("Konfiguruje Apple Push Notification Service dla urządzeń iOS");
    }

    public void configureFCM(String apiKey) {
        log.info("Konfiguruje Firebase Cloud Messaging dla urządzeń Android");
    }

    public void sendNotification(Notification notification) {
        log.info("Wysyłam notyfikacje push");
    }
}
