package com.boco.soap.web.controller;

import com.alibaba.fastjson.JSON;
import com.boco.soap.cmnet.beans.entity.*;
import com.boco.soap.cmnet.beans.enums.OrderStateEnum;
import com.boco.soap.cmnet.iservice.*;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FrameController {

    private static  final Logger LOGGER= LoggerFactory.getLogger(FrameController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IProcessDetailService processDetailServiceImpl;
    @Autowired
    private IBusiService busiServiceImpl;
    @Autowired
    private INeService neService;
    @Autowired
    private IBusiDictService busiDictService;

    @RequestMapping(value = "/frame/{busiId}/{orderId}/{userId}",method = RequestMethod.GET)
    public ModelAndView getFrameContent(@PathVariable("orderId") String orderId,@PathVariable("userId") String userId,@PathVariable("busiId") String busiId){
        LOGGER.info("获取frame页面========");
        Order order=orderService.getById(orderId);
        ModelAndView modelAndView=new ModelAndView("frame");
        Busi busi=busiServiceImpl.getById(order.getBusiId());
        modelAndView.addObject("busiId",busiId);
        modelAndView.addObject("orderId",orderId);
        modelAndView.addObject("userId",userId);
        modelAndView.addObject("status",order.getStatus());
        return modelAndView;
    }

    @RequestMapping(value = "/phaseCtrl/{orderId}/{status}/{userId}/{busiId}",method = RequestMethod.GET)
    public String getphaseCtrl(@PathVariable  String orderId,@PathVariable String status,@PathVariable String userId,@PathVariable String busiId,ModelMap modelMap){
        System.out.println("busiId============="+busiId);
        Busi busi=busiServiceImpl.getById(busiId);
        List<ProcessDetail> processDetailList=processDetailServiceImpl.getByProcessId(busi.getProcessId());
        modelMap.put("busiId",busiId);
        modelMap.put("orderId",orderId);
        modelMap.put("userId",userId);
        modelMap.put("status",status);
        modelMap.put("processDetailList",processDetailList);
        return "common/phaseCtrl";
    }

    @RequestMapping(value = "/frame/{orderId}/detail/{busiDictName}",method = RequestMethod.GET)
    public ModelAndView getOrderDetail(@PathVariable String orderId,@PathVariable String busiDictName){
        OrderMongo orderMongo=orderService.getOrderCheckResult(orderId);
        List resuList=(List)orderMongo.getCheckResult().get(busiDictName).get("resultList");
        ModelAndView modelAndView=new ModelAndView("common/checkdetail");
        modelAndView.addObject("busiDictName",busiDictName);
        modelAndView.addObject("orderId",orderId);
        modelAndView.addObject("resuList",resuList);
        modelAndView.addObject("searchFiled",orderMongo.getBusiDictList().get(busiDictName).getKeyStr());
        modelAndView.addObject("itemNameList",orderMongo.getBusiDictList().get(busiDictName).getItemNames());
        return modelAndView;
    }

    @RequestMapping(value = "/result/{userId}",method = RequestMethod.GET)
    public ModelAndView getOrderHistory(@PathVariable String userId){
        ModelAndView modelAndView=new ModelAndView("common/checkhistory");
        List orderList=orderService.getList(null);
        modelAndView.addObject("resuList",orderList );
        return modelAndView;
    }

    @RequestMapping(value = "/frame/{userId}/{orderId}/{status}/{busiId}/detail",method = RequestMethod.GET)
    public ModelAndView getDetailContent(@PathVariable String userId,@PathVariable String orderId,@PathVariable String status,@PathVariable String busiId){
        OrderStateEnum orderStateEnum=OrderStateEnum.getEnumByValue(Integer.parseInt(status));
        String curPage="";
        ModelAndView modelAndView=new ModelAndView(curPage);
        modelAndView.addObject("busiId",busiId);
        modelAndView.addObject("orderId",orderId);
        modelAndView.addObject("userId",userId);
        modelAndView.addObject("status",status);
        switch (orderStateEnum){
            case CHECK_DATA:
                if ("1".equals(busiId)) {
                    curPage = "volte/checkdata";
                }else {
                    curPage="common/checkdata";
                }
                break;
            case CHECK_CONFIG:
                if ("1".equals(busiId)) {
                    curPage = "volte/checkconfig";
                }else if("2".equals(busiId)){
                    List<String> cityList=neService.getNeCitys();
                    List<BusiDict> busiDictList =null;
                    Order orderTmp=orderService.getById(orderId);
                    List<String> citySelect=new ArrayList<>();
                    if (!status.equals(String.valueOf(orderTmp.getStatus()))) {
                         busiDictList = busiDictService.getListAndCheckStatue(orderId);
                        citySelect=neService.getNeSelected(orderId);
                    }else {
                        busiDictList=busiDictService.getListByBusiId(busiId);
                    }
                    modelAndView.addObject("citySelect",citySelect);
                    modelAndView.addObject("cityList",cityList);
                    modelAndView.addObject("busiDictList",busiDictList);
                    curPage="mscpool/checkconfig";
                }else {
                    curPage="common/checkconfig";
                }
                break;
            case CHECK_COMP:
                curPage="common/checkcompare";
                break;
            case CHECK_RESULT:
                modelAndView.addObject("data",orderService.getOrderCheckResult(orderId));
                curPage="common/checkresult";
                break;
            default:
                curPage="index";
        }
        modelAndView.setViewName(curPage);
        return modelAndView;
    }

}
