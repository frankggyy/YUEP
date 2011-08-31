/*
 * $Id: LoadResourceRsp.java, 2010-4-27 下午03:19:37 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing;

import java.io.FileInputStream;
import java.util.Properties;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.resource.ResourceFactory;
import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: LoadResourceRsp
 * </p>
 * <p>
 * Description:默认客户的资源加载类
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:19:37
 * modified [who date description]
 * check [who date description]
 */
public class DefaultLoadResource{
	
    /**
     * 加载一些配置文件等
     * @param coreModuleName
     * @return
     */
    public boolean loadResource(String coreModuleName) {
        ResourceFactory resourceFactory = new ResourceFactory(coreModuleName);
        ClientCoreContext.setResourceFactory(resourceFactory);
        resourceFactory.setLocale(ClientCoreContext.getLocale());

        FileInputStream stream =null;
        try {
            // 读取oem标志位
            Properties prop = new Properties();
            stream = new FileInputStream(resourceFactory.getConfFile("system.properties").getFile());
            prop.load(stream);
            if (prop.getProperty("oemName") != null && prop.getProperty("oemName").trim().length() > 0) {
                resourceFactory.setOemName(prop.getProperty("oemName"));
            }
            //读取客户端界面是否缓存
            String mvcCache = prop.getProperty("mvccache");
            ClientCoreContext.setMvcCache(Boolean.valueOf(mvcCache));
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getCommonExceptionInfo(e));
        }finally{
            if(stream!=null){
                try{
                    stream.close();
                }catch(Exception ex){
                    System.out.println(ExceptionUtils.getCommonExceptionInfo(ex));
                }
            }
        }
        
        return true;
    }
}
