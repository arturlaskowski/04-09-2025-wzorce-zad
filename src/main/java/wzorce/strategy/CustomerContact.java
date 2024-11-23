package wzorce.strategy;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;

@Embeddable
record CustomerContact(String email, String phoneNumber, String deviceToken,
                       NotificationChannel communicationPreference) {

    public CustomerContact {
        if (communicationPreference == null) {
            throw new CommunicationPreferenceException("Communication preference is null");
        }
        switch (communicationPreference) {
            case EMAIL -> validate(StringUtils.isBlank(email), "Email is required for email communication preference.");
            case SMS ->
                    validate(StringUtils.isBlank(phoneNumber), "Phone number is required for SMS communication preference.");
            case PUSH_NOTIFICATION ->
                    validate(StringUtils.isBlank(deviceToken), "Device token is required for push notification preference.");
        }
    }

    private void validate(boolean condition, String message) {
        if (condition) {
            throw new CommunicationPreferenceException(message);
        }
    }
}

