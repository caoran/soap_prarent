package com.boco.soap.cmnet.message.consumer.rocketmq;

import com.boco.soap.cmnet.message.msg.exception.MsgException;
import com.boco.soap.cmnet.util.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

public class MsgConsumerClient {
    private static final Map<String, IMsgForConsumer> CONSUMER_MAP = new HashMap();

    public MsgConsumerClient() {
    }

    public static IMsgForConsumer getRocketMQConsumer() throws MsgException {
        if (null == CONSUMER_MAP.get("ROCKETMQ_MQ_IP_PORT")) {
            Class var0 = MsgRocketMQConsumer.class;
            synchronized(MsgRocketMQConsumer.class) {
                if (null == CONSUMER_MAP.get("ROCKETMQ_MQ_IP_PORT")) {
                    IMsgForConsumer consumer = SpringContextHolder.getBean("msgRocketMQConsumer");
                    CONSUMER_MAP.put("ROCKETMQ_MQ_IP_PORT", consumer);
                }
            }
        }

        return (IMsgForConsumer)CONSUMER_MAP.get("ROCKETMQ_MQ_IP_PORT");
    }
}
