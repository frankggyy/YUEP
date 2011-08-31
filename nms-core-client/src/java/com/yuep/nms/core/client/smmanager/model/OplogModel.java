/*
 * $Id: OplogModel.java, 2011-4-13 下午06:03:53 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractTabModel;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OplogModel
 * </p>
 * <p>
 * Description:操作日志界面的model
 * </p>
 * 
 * @author sufeng
 * created 2011-4-13 下午06:03:53
 * modified [who date description]
 * check [who date description]
 */
public class OplogModel extends AbstractTabModel<Object> {

    @Override
    public void init(Object... objects) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        
        List<String> userNames=smManagerService.getAllOperationUserNames();//操作员
        List<String> sources=smManagerService.getAllOperationObjects();//操作对象
        List<String> operates=smManagerService.getAllOperationNames(); //操作类别
        
        List<Object> datas=new ArrayList<Object>();
        datas.add("init");
        
        if(userNames==null)
            userNames=new ArrayList<String>();
        userNames.add(0, "");
        datas.add(userNames);
        
        if(sources==null)
            sources=new ArrayList<String>();
        sources.add(0,"");
        datas.add(sources);
        
        if(operates==null)
            operates=new ArrayList<String>();
        operates.add(0,"");
        datas.add(operates);
        
        boList.clear();
        boList.addAll(datas);
    }  
    
    @Override
    public void modifyDatas(List<Object> datas) {
        setChanged();
        notifyObservers(datas);
    }

}
