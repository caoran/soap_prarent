package com.boco.soap.cmnet.check.excutor;

public interface ICheckDataAllMain {
     String soapCheckDataInvokeResult(String paramString)
            throws Exception;

       String soapCheckDataInvokeResultByOrderId(String paramString)
            throws Exception;
}
