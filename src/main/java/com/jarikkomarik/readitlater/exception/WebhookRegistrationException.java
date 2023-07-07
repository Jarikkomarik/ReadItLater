package com.jarikkomarik.readitlater.exception;

public class WebhookRegistrationException extends RuntimeException {
    public WebhookRegistrationException() {
        super("Unable to register webhook.");
    }
}
