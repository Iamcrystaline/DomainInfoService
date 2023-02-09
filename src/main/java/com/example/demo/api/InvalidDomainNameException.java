package com.example.demo.api;

public class InvalidDomainNameException extends RuntimeException {

    public InvalidDomainNameException(String message) {
        super(message);
    }
}
