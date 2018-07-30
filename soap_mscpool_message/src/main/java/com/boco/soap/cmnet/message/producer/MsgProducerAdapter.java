package com.boco.soap.cmnet.message.producer;

import com.boco.soap.cmnet.message.msg.exception.MsgException;

public class MsgProducerAdapter implements IMsgForProducer {

    @Override
    public boolean send(String var1, String var2) throws MsgException {
        throw new MsgException("当前消息中间件类型不支持此方式API");
    }

    @Override
    public boolean send(String var1, String var2, String var3) throws MsgException {
        throw new MsgException("当前消息中间件类型不支持此方式API");
    }

    @Override
    public boolean send(String var1) throws MsgException {
        throw new MsgException("当前消息中间件类型不支持此方式API");
    }
}
