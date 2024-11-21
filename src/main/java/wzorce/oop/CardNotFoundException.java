package wzorce.oop;

class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long cardId) {
        super("Card not found, id: " + cardId);
    }
}