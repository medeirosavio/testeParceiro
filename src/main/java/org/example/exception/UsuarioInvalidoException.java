package org.example.exception;

public class UsuarioInvalidoException extends RuntimeException {
    public UsuarioInvalidoException(String message) {
        super(message);
    }
}

