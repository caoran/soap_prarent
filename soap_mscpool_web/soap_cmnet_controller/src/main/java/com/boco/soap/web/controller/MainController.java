package com.boco.soap.web.controller;

import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.iservice.IBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private IBusiService busiServiceImpl;
    /**
     * 入口页面
     * @return
     */
    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") String userId, ModelMap map) {
        List<Busi> busisList=busiServiceImpl.getAll();
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("busilist",busisList);
        modelAndView.addObject("userId",userId);
        return modelAndView;
    }


}
