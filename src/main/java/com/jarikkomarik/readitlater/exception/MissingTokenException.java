package com.jarikkomarik.readitlater.exception;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException() {
        super("Unable to start bot, missing token.");
    }
}
