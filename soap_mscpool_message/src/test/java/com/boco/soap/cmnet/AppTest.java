package com.boco.soap.cmnet;

import com.boco.soap.cmnet.util.SpringContextHolder;
import com.boco.soap.cmnet.message.msg.exception.MsgException;
import com.boco.soap.cmnet.message.producer.IMsgForProducer;
import com.boco.soap.cmnet.message.producer.MsgProducerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(AppTest.class);
    @Test
    public void testSendMessage() throws Exception {
        LOGGER.info("开始发送消息=====");
        IMsgForProducer producer = SpringContextHolder.getBean("msgRocketMQProducer");
        for (int i=0;i<10;i++) {
            MsgProducerClient.getRocketMQProducer().send("Hello the World   "+i);
        }
        /*Scanner sc = new Scanner(System.in);
        //利用hasNextXXX()判断是否还有下一输入项
        while (sc.hasNext()) {
            //利用nextXXX()方法输出内容
            String str = sc.next();
            System.out.println(str);
        }*/
        TimeUnit.MILLISECONDS.sleep(60);
    }

}