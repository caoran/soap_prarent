package com.boco.soap.cmnet;

import com.boco.soap.cmnet.check.excutor.ICheckDataAllMain;
import com.boco.soap.cmnet.util.SpringContextHolder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.boco.soap")
public class AppTest
{
    @Test
    public void testCheckDataAllMain() throws Throwable{
        ICheckDataAllMain checkDataAllMain= SpringContextHolder.getBean("checkDataAllMainImpl");
        checkDataAllMain.soapCheckDataInvokeResultByOrderId("test");
    }

}
