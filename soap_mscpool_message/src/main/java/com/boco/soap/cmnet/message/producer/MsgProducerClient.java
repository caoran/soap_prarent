package com.boco.soap.cmnet.message.producer;

import com.boco.soap.cmnet.util.SpringContextHolder;
import com.boco.soap.cmnet.message.msg.exception.MsgException;

import java.util.HashMap;
import java.util.Map;

public class MsgProducerClient {

    private static final Map<String, IMsgForProducer> PRODUCER_MAP = new HashMap();

    public MsgProducerClient() {
    }

    public static IMsgForProducer getRocketMQProducer() throws MsgException {
        if (null == PRODUCER_MAP.get("ROCKETMQ_MQ_IP_PORT")) {
            Class var0 = MsgRocketMQProducer.class;
            synchronized(MsgRocketMQProducer.class) {
                if (null == PRODUCER_MAP.get("ROCKETMQ_MQ_IP_PORT")) {
                    IMsgForProducer producer = SpringContextHolder.getBean("msgRocketMQProducer");
                    PRODUCER_MAP.put("ROCKETMQ_MQ_IP_PORT", producer);
                }
            }
        }

        return (IMsgForProducer)PRODUCER_MAP.get("ROCKETMQ_MQ_IP_PORT");
    }
}
