package wzorce.adapter;

class UnsupportedCurrencyException extends RuntimeException {

    public UnsupportedCurrencyException(Currency currency) {
        super("Currency " + currency + " is not supported");
    }

}