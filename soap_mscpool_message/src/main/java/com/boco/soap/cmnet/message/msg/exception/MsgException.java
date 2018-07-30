package com.boco.soap.cmnet.message.msg.exception;

public class MsgException extends Exception{
    private static final long serialVersionUID = -5758410930844185841L;
    private final String errorMessage;

    public MsgException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    public MsgException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
