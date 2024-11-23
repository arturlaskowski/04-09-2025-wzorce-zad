package wzorce.strategy;

import lombok.Builder;

@Builder
record CustomerCreateDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String deviceToken,
        NotificationChannel communicationPreference
) {
}
