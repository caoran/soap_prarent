package com.boco.soap.cmnet.db.service;


import com.boco.soap.cmnet.beans.enums.OrderStateEnum;
import com.boco.soap.cmnet.db.mybitas.Session;
import com.boco.soap.cmnet.db.mybitas.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DBCheckResultService {
    private static  final Logger LOGGER= LoggerFactory.getLogger(DBCheckResultService.class);



    public void updateMainTaskForComplete(String orderId)
    {

        LOGGER.info("DBCheckResultService=================，orderId:{}",orderId);
        Map param = new HashMap();
        param.put("orderId", orderId);
        param.put("status", OrderStateEnum.CHECK_RESULT.getState());

        Session session = SessionFactory.getSession("DB", "result");
        try
        {
            session.update("update_main_task", param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            session.close();
        }
    }



}
