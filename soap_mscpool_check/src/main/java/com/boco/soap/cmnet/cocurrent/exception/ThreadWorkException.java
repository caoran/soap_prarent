package com.boco.soap.cmnet.cocurrent.exception;


public class ThreadWorkException extends RuntimeException
{
    private static final long serialVersionUID = -3424338377751023250L;

    public ThreadWorkException()
    {
    }

    public ThreadWorkException(String msg)
    {
        super(msg);
    }

    public ThreadWorkException(Throwable t) {
        super(t);
    }

    public ThreadWorkException(String msg, Throwable t) {
        super(msg, t);
    }
}
