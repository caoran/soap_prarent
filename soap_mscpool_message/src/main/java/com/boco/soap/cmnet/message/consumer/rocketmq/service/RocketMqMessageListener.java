package com.boco.soap.cmnet.message.consumer.rocketmq.service;


import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息接口
 */
public interface RocketMqMessageListener {
    boolean onMessage(List<MessageExt> messages,
                      ConsumeConcurrentlyContext Context);
}
