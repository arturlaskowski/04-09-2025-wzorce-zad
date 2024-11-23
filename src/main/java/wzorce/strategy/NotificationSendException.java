package wzorce.strategy;

class NotificationSendException extends RuntimeException {

    public NotificationSendException(String message) {
        super(message);
    }
}