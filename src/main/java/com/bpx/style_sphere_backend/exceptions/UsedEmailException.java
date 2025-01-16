package com.bpx.style_sphere_backend.exceptions;

public class UsedEmailException extends RuntimeException{
    public UsedEmailException(String message) {
        super(message);
    }
}
