package com.boco.soap.cmnet.message.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.boco.soap.cmnet.message.config.MessageConfiguration;
import com.boco.soap.cmnet.message.consumer.rocketmq.service.RocketMqMessageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MsgRocketMQConsumer implements IMsgForConsumer{
    private final Logger logger = LoggerFactory.getLogger(MsgRocketMQConsumer.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    private MessageConfiguration config;

    @Autowired
    private RocketMqMessageWrapper rocketMqMessageWrapper;

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer(config.getGroupName());
        defaultMQPushConsumer.setNamesrvAddr(config.getNamesrvAddr());
        defaultMQPushConsumer.setInstanceName(config.getInstanceName());

        //设置订阅tag下的subExpression
        defaultMQPushConsumer.subscribe(config.getTopic(), config.getTag());

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        //注册监听 保证消费成功
        defaultMQPushConsumer.registerMessageListener(rocketMqMessageWrapper);

        //关闭VIP通道，避免接收不了消息
        defaultMQPushConsumer.setVipChannelEnabled(false);
        try {

            defaultMQPushConsumer.start();
            logger.info("rocketMq consumer start success");
        }catch (Exception e){
            logger.error("初始化消费者远程连接出现异常",e);
            throw new MQClientException("初始化消费者远程连接出现异常，请检查ROCKETMQ_MQ_IP_PORT地址配置是否正确",e);
        }

    }

    @PreDestroy
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }
}
