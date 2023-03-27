package com.project.drbot.common.config.exception;

public class ServiceException extends RuntimeException{
    private ExceptionCode errorCode;

    public ServiceException(String message, ExceptionCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ExceptionCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public ServiceException(){
        super();
    }

    public ServiceException(String message){
        super(message);
    }
}
