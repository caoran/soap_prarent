package com.boco.soap.cmnet.message.consumer;

import com.boco.soap.cmnet.message.producer.MsgProducerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TocketMessage implements Runnable{

    private static final Logger LOGGER= LoggerFactory.getLogger(TocketMessage.class);

    private String messageBody;

    public TocketMessage(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("多线程开始核查======，接受到message:{}",messageBody);
            MsgProducerClient.getRocketMQProducer().send(messageBody);
        }catch (Throwable e){
            LOGGER.error("",e);
        }
    }
}
