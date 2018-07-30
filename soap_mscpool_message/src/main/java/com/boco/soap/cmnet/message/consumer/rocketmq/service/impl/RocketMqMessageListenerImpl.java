package com.boco.soap.cmnet.message.consumer.rocketmq.service.impl;


import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.boco.soap.cmnet.check.excutor.CheckDataMainFactory;
import com.boco.soap.cmnet.message.consumer.rocketmq.service.RocketMqMessageListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 消息处理实现类
 */
@Service
public class RocketMqMessageListenerImpl implements RocketMqMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqMessageListenerImpl.class);

    @Override
    public boolean onMessage(List<MessageExt> messages, ConsumeConcurrentlyContext Context) {
        logger.info("接收到消息==============");
        //messages.forEach(messageExt -> logger.info(StringUtils.toEncodedString(messageExt.getBody(), Charset.defaultCharset())));
        //返回false则意味着消息消费失败 消息会一直通知
        //return false;
        for (MessageExt messageExt:messages){
            try {
                String messageBody=StringUtils.toEncodedString(messageExt.getBody(), Charset.defaultCharset());
                logger.info("messageBody========:{}",messageBody);
                CheckDataMainFactory.getFactory().getCheckDataMain().soapCheckDataInvokeResultByOrderId(messageBody);
            }catch (Throwable e){
                logger.error(e.getMessage(),e);
            }
        }
        return true;
    }
}
