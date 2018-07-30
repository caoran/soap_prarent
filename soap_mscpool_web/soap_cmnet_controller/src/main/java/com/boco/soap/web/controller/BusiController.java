package com.boco.soap.web.controller;

import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.iservice.IBusiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.cs.ext.IBM037;

import java.util.List;

@RestController
public class BusiController {

    private static final Logger LOGGER=LoggerFactory.getLogger(BusiController.class);

    @Autowired
   private IBusiService busiServiceImpl;

    @RequestMapping(value = "/busi",method = RequestMethod.GET)
    public List<Busi> getBusiAll() {
        LOGGER.info("获取所有业务信息");
        List<Busi> busisList=busiServiceImpl.getAll();
        return busisList;
    }

    @RequestMapping(value = "/busi/{id}",method = RequestMethod.GET)
    public Busi getBusiById(@PathVariable String id) {
        LOGGER.info("获取业务信息id:{}",id);
        return busiServiceImpl.getById(id);
    }
}
