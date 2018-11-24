package me.migachev.accountmanager.exception;

public class NotEnoughAcoountAmountException extends RuntimeException {
    public NotEnoughAcoountAmountException(String message) {
        super(message);
    }
}
