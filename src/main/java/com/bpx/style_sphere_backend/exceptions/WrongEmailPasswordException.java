package com.bpx.style_sphere_backend.exceptions;

public class WrongEmailPasswordException extends RuntimeException{
    public WrongEmailPasswordException(String message) {
        super(message);
    }
}
