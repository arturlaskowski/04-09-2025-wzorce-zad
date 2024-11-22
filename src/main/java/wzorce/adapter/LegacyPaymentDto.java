package wzorce.adapter;

record LegacyPaymentDto(
        String accountNumber,
        String transactionId,
        int amount) {
}
