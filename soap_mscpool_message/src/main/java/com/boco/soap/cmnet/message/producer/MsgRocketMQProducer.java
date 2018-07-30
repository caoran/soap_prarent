package com.boco.soap.cmnet.message.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.boco.soap.cmnet.message.config.MessageConfiguration;
import com.boco.soap.cmnet.message.msg.exception.MsgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Component
public class MsgRocketMQProducer extends MsgProducerAdapter{
    private final Logger logger = LoggerFactory.getLogger(MsgRocketMQProducer.class);
    private static final Map<String, DefaultMQProducer> producerMap = new HashMap();

    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private MessageConfiguration config;

    @PostConstruct
    public void init() throws MQClientException {
        if (producerMap.containsKey(config.getTopic())) {
              producerMap.get(config.getTopic());
        } else {

                this.defaultMQProducer = new DefaultMQProducer(config.getGroupName());
                defaultMQProducer.setNamesrvAddr(config.getNamesrvAddr());
                defaultMQProducer.setInstanceName(config.getInstanceName());
                //关闭VIP通道，避免出现connect to <:10909> failed导致消息发送失败
                defaultMQProducer.setVipChannelEnabled(false);
            try {
                defaultMQProducer.start();
                producerMap.put(config.getTopic(), defaultMQProducer);
                logger.info("RocketMq Producer start success");
            }catch (Exception e){
                throw new MQClientException("初始化生产者远程连接出现异常，请检查ROCKETMQ_MQ_IP_PORT地址配置是否正确", e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        defaultMQProducer.shutdown();
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }



    private boolean send(String topic, String keys, String tag, String msg) throws MsgException {
        SendResult sendResult = null;
        try {
            Message message = this.getMessage(topic, keys, tag, msg);
            sendResult = this.getDefaultMQProducer().send(message);
            logger.info("sender发送消息成功，topic:{},消息为：{}",topic,msg);
        } catch (Exception var9) {
            throw new MsgException("消息发送异常", var9);
        }

        return sendResult.getSendStatus().equals(SendStatus.SEND_OK);
    }

    @Override
    public boolean send(String var1) throws MsgException {
        return this.send(config.getTopic(),var1);
    }

    @Override
    public boolean send(String topic, String msg) throws MsgException {
        return this.send(topic,config.getTag(),msg);
    }

    @Override
    public boolean send(String topic, String tag, String msg) throws MsgException {
        return this.send(topic, null,tag, msg);
    }

    private Message getMessage(String topic, String keys, String tag, String msg) throws MsgException {
        Message message = new Message();

        try {
            if (null != keys) {
                message.setKeys(keys);
            }

            if (null != tag) {
                message.setTags(tag);
            }

            message.setTopic(topic);
            message.setBody(msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            return message;
        } catch (Exception var7) {
            throw new MsgException("消息体初始化出现异常", var7);
        }
    }

}
