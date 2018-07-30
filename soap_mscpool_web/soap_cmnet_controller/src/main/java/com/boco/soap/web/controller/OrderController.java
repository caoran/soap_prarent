package com.boco.soap.web.controller;

import com.alibaba.fastjson.JSON;
import com.boco.soap.cmnet.base.RestResponse;
import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.beans.entity.Order;
import com.boco.soap.cmnet.beans.entity.ProcessDetail;
import com.boco.soap.cmnet.entity.ExcelData;
import com.boco.soap.cmnet.iservice.IBusiService;
import com.boco.soap.cmnet.iservice.IOrderService;
import com.boco.soap.cmnet.iservice.IProcessDetailService;
import com.boco.soap.cmnet.message.producer.MsgProducerClient;
import com.boco.soap.cmnet.util.CommonUtil;
import com.boco.soap.cmnet.util.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private static  final Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderServiceImpl;
    @Autowired
    private IBusiService busiService;
    @Autowired
    private IProcessDetailService processDetailService;


    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public RestResponse<Order> startOder(Order order){
        RestResponse<Order> retRes=new RestResponse<>();
        try {
        order.setCreateTime(new Date());
        order.setId(CommonUtil.nextStrId());
        Busi busi=busiService.getById(order.getBusiId());
        List<ProcessDetail> processDetailsList=processDetailService.getByProcessId(busi.getProcessId());
        order.setStatus((int)processDetailsList.get(0).getSeqNo());
        orderServiceImpl.save(order);
        retRes.setSuccess(true);
        retRes.setMessage("创建工单成功");
        retRes.setData(order);
        }catch (Exception e){
            retRes.setSuccess(false);
            retRes.setMessage("创建工单成功");
            logger.error("",e);
        }
        return retRes;
    }

    @RequestMapping(value = "/order",method = RequestMethod.PUT)
    public RestResponse<Order> uppdateOderStatus(Order order){
        RestResponse<Order> retRes=new RestResponse<>();
        try {
            orderServiceImpl.update(order);
            retRes.setSuccess(true);
            retRes.setMessage("修改工单成功");
            retRes.setData(order);
        }catch (Exception e){
            retRes.setSuccess(false);
            retRes.setMessage("创建工单失败");
            logger.error("",e);
        }
        return retRes;
    }

    @RequestMapping(value = "/order/{id}/std",method = RequestMethod.POST)
    public RestResponse<Order> saveOrderStd(@PathVariable String id,@RequestParam String city, @RequestParam String[] busiDict){
        RestResponse<Order> retRes=new RestResponse<>();
        try {
            Order order=orderServiceImpl.getById(id);
            orderServiceImpl.saveOrderStd(order,city,busiDict);
            retRes.setSuccess(true);
            retRes.setMessage("保存工单标准数据成功");
            retRes.setData(order);
            MsgProducerClient.getRocketMQProducer().send(id);
            //new Thread(new TocketMessage(id)).start();
        }catch (Exception e){
            retRes.setSuccess(false);
            retRes.setMessage("保存工单标准数据失败");
            logger.error("",e);
        }
        return retRes;
    }

    @RequestMapping(value = "/order/{busiId}/businame",method = RequestMethod.GET)
    public String getOrderName(@PathVariable String busiId){
        return JSON.toJSONString(orderServiceImpl.getOrderName(busiId));
    }

    @RequestMapping(value = "/order/{orderId}",method = RequestMethod.GET)
    public RestResponse<Order>  getOrder(@PathVariable String orderId){
       Order order= orderServiceImpl.getById(orderId);
       return RestResponse.ok(order,"");
    }



    @RequestMapping(value = "/order/{orderId}/{busiDictName}/detail",method = RequestMethod.POST)
    public RestResponse<List>  getOrderDetail(@PathVariable String orderId,@PathVariable String busiDictName,@RequestParam String paramName,@RequestParam String paramValue){
        List list = orderServiceImpl.getOrderDetail(orderId,busiDictName,paramName,paramValue);
        return RestResponse.ok(list,"");
    }


    @RequestMapping(value = "/order/{orderId}/{busiDictName}/detail/excel",method = RequestMethod.GET)
    public void  exportOrderDetailExcel(@PathVariable String orderId, @PathVariable String busiDictName, @RequestParam String paramName, @RequestParam String paramValue, HttpServletResponse response) throws Exception{
        List<Map<String,Object>> list = orderServiceImpl.getOrderDetail(orderId,busiDictName,paramName,paramValue);
        ExcelData<Map<String,Object>> excelData=new ExcelData<Map<String,Object>>(list);
        ExcelUtils.exportExcel(response,"核查结果导出.xlsx",excelData);
    }



}
