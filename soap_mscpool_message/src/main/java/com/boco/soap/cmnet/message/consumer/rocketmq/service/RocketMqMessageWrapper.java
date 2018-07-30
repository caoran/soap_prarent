package com.boco.soap.cmnet.message.consumer.rocketmq.service;


import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 封装通用逻辑
 */
@Service
public class RocketMqMessageWrapper implements MessageListenerConcurrently {

    @Autowired
    private RocketMqMessageListener rocketMqMessageListener;

    /**
     * 返回消费结果
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (rocketMqMessageListener.onMessage(list, consumeConcurrentlyContext)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}