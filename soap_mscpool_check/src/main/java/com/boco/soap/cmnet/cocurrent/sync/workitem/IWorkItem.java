package com.boco.soap.cmnet.cocurrent.sync.workitem;

import com.boco.soap.cmnet.cocurrent.exception.ThreadWorkException;

public interface IWorkItem {
     String getWorkItemName();

     void run()  throws ThreadWorkException;
}
