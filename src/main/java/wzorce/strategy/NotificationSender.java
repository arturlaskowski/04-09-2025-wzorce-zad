package wzorce.strategy;

interface NotificationSender {

    void sendNotification(Notification notification);

    NotificationChannel notificationChannel();
}
