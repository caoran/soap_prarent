package com.boco.soap.cmnet.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置文件值
 */
@Component
@PropertySource(value={"classpath:message.properties"})
/*@ConfigurationProperties(prefix = "spring.rocketmq.producer")*/
public class MessageConfiguration {

    @Value("${producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${producer.groupName}")
    private String groupName;

    @Value("${producer.topic}")
    private String topic;

    @Value("${producer.tag}")
    private String tag;

    @Value("${producer.instanceName}")
    private String instanceName;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }


    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}