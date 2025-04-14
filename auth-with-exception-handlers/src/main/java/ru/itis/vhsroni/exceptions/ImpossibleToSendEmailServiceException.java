package ru.itis.vhsroni.exceptions;

public class ImpossibleToSendEmailServiceException extends ServiceException{

    public ImpossibleToSendEmailServiceException() {
        super(500, "Невозможность отправить код по указанному email");
    }
}
