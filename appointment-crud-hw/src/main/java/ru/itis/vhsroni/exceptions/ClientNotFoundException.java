package ru.itis.vhsroni.exceptions;

public class ClientNotFoundException extends NotFoundServiceException{

    public ClientNotFoundException() {
        super("Client not found");
    }
}
