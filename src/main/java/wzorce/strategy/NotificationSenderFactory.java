package wzorce.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class NotificationSenderFactory {

    private final Map<NotificationChannel, NotificationSender> notificationSenderMap;

    public NotificationSenderFactory(List<NotificationSender> strategies) {
        this.notificationSenderMap = strategies.stream()
                .collect(Collectors.toMap(NotificationSender::notificationChannel, strategy -> strategy));
    }

    public NotificationSender getNotificationSender(NotificationChannel channel) {
        NotificationSender notificationSender = notificationSenderMap.get(channel);
        if (notificationSender == null) {
            throw new IllegalArgumentException("Unsupported notification channel: " + channel);
        }

        return notificationSender;
    }
}
