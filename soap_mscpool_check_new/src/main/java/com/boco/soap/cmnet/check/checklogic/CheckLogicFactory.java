package com.boco.soap.cmnet.check.checklogic;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.checklogic.impl.DefaltCheckLogicChangeImpl;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class CheckLogicFactory {
    private Logger log = LoggerFactory.getLogger(CheckLogicFactory.class);

    public ICheckLogicChange getCheckLogicChange(BusinessCheckContext subTaskContext, String instructionId, String type)
    {
        ICheckLogicChange transitionObj = null;

        Ne neInfo = subTaskContext.getNeList().get(0);
        String manufacture = neInfo.getVendor() + "";
        String netType =""; //neInfo.getNetType() + "";
        String neType =""; //neInfo.getNeType() + "";
        String key = netType + "," + neType + "," + manufacture + "," + instructionId + "," + type;
        this.log.info("开始生成二次核查处理类：key{}", key);
        Map<String,String> classHash =new HashMap<>(); //subTaskContext.getCheckLogicMap();
        String className = classHash.get(key);
        if (this.log.isDebugEnabled()) {
            this.log.info("生成核查结果数据为指令生成需要数据的转换类=======" + className);
        }
        if ((className == null) || (className.equals(""))) {
            transitionObj = new DefaltCheckLogicChangeImpl();
            if (type.equals("2"))
                return null;
        }
        else
        {
            try {
                transitionObj = (ICheckLogicChange)Class.forName(className).newInstance();
            } catch (Exception e) {
                if (this.log.isDebugEnabled()) {
                    this.log.error("生成核查结果转化数据" + className + "失败" + e.getMessage());
                }
                e.printStackTrace();
            }
        }
        return transitionObj;
    }

    public Map<String, String> getTransClassHash()
    {
        //String transFilePath = SysProperty.getInstance().getValue("checkLogicFilePath");
        Map classHash = new Hashtable();
        /*File file = new File(transFilePath + File.separator + "CheckLogicDefineCfg.xml");
        this.log.info("开始初始化核查逻辑处理类，配置文件路径是{}", file);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            List transitionList = root.elements();
            for (Element transition : transitionList) {
                className = transition.attribute("class").getText();
                List instructions = transition.elements();
                List instructionList = ((Element)instructions.get(0)).elements();
                for (Element eleInstruction : instructionList)
                {
                    String netType = eleInstruction.attribute("netType") != null ? eleInstruction.attribute("netType").getText() : "";
                    String neType = eleInstruction.attribute("neType") != null ? eleInstruction.attribute("neType").getText() : "";
                    String manufacture = eleInstruction.attribute("manufacture") != null ? eleInstruction.attribute("manufacture").getText() : "";
                    String instructionId = eleInstruction.getText();
                    String type = eleInstruction.attribute("type") != null ? eleInstruction.attribute("type").getText() : "";
                    String key = netType + "," + neType + "," + manufacture + "," + instructionId + "," + type;
                    if (!classHash.containsKey(key)) {
                        this.log.info("开始初始化核查逻辑处理类，初始化类的key:{},className:{}", key, className);
                        classHash.put(key, className);
                    }
                }
            }
        }
        catch (DocumentException e)
        {
            String className;
            this.log.error("加载CheckLogicDefineCfg.xml配置文件错误");
            e.printStackTrace();
        }*/
        return classHash;
    }
}
