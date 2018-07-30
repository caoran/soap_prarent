package com.boco.soap.cmnet.message.producer;

import com.boco.soap.cmnet.message.msg.exception.MsgException;

public interface IMsgForProducer {
    boolean send(String var1, String var2) throws MsgException;

    boolean send(String var1) throws MsgException;

    boolean send(String var1, String var2, String var3) throws MsgException;
}
