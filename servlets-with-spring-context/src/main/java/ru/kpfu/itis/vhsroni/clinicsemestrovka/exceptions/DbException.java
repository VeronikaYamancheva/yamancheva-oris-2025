package ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions;

public class DbException extends RuntimeException{

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
