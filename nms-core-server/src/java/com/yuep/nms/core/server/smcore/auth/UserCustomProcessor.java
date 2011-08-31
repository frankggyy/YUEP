/*
 * $Id: UserCustomProcessor.java, 2011-4-22 ����10:47:50 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.auth;

import java.util.HashMap;
import java.util.Map;

import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.nms.core.common.smcore.model.UserCustom;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.server.smcore.SmCoreContext;

/**
 * <p>
 * Title: UserCustomProcessor
 * </p>
 * <p>
 * Description: �����û����Ի���Ϣ
 * </p>
 * 
 * @author sufeng
 * created 2011-4-22 ����10:47:50
 * modified [who date description]
 * check [who date description]
 */
public class UserCustomProcessor {

    private BaseDao<UserCustom> userCustomDao;
    public void setUserCustomDao(BaseDao<UserCustom> userCustomDao) {
        this.userCustomDao = userCustomDao;
    }
    
    /**
     * ��ȡ�û��ĸ��Ի���Ϣ
     * @param userName
     * @return
     */
    public UserCustom getUserCustom(String userName){
        UserCustom custom = userCustomDao.getUniqueEntityByOneProperty("userName", userName);
        return custom;
    }
    
    /**
     * �����û��ĸ��Ի���Ϣ
     * @param userName
     * @param key
     * @param value
     */
    public void setUserCustom(String userName,String key,String value){
        if(key==null)
            return;
        UserCustom custom = getUserCustom(userName);
        if(custom==null){
            custom=new UserCustom();
            custom.setUserName(userName);
            Map<String, String> customInfo =new HashMap<String, String>();
            customInfo.put(key, value);
            custom.setCustomInfo(customInfo);
            userCustomDao.saveEntity(custom);
        }else{
            Map<String, String> customInfo = custom.getCustomInfo();
            if(customInfo==null){
                customInfo=new HashMap<String, String>();
                custom.setCustomInfo(customInfo);
            }
            customInfo.put(key, value);
            userCustomDao.updateEntity(custom);
        }
        
        sendUserCustomChangeMessage(custom);
    }
    
    /**
     * ɾ��user���Ի���һ����¼
     * @param userName
     * @param key
     */
    public void deleteUserCustomItem(String userName,String key){
        if(key==null)
            return;
        UserCustom custom = getUserCustom(userName);
        if(custom!=null){
            Map<String, String> customInfo = custom.getCustomInfo();
            if(customInfo!=null){
                String info = customInfo.get(key);
                if(info!=null){
                    if(customInfo.size()==1){
                        // ���һ��keyҲ��ɾ���ˣ���ֱ��ɾ�����û���custom��¼��
                        deleteUserCustom(userName);
                    }else{
                        customInfo.remove(key);
                        userCustomDao.updateEntity(custom);
                        sendUserCustomChangeMessage(custom);
                    }
                }
            }
        }
    }
    
    /**
     * ɾ��һ���û��ĸ��Ի���Ϣ
     * @param userName
     */
    public void deleteUserCustom(String userName){
        userCustomDao.deleteEntityByProperty("userName", userName);
        sendUserCustomDeleteMessage(userName);
    }
    
    private void sendUserCustomChangeMessage(UserCustom custom){
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_CUSTOM_CHANGED);
        msg.setMessageBody(custom);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }
    
    private void sendUserCustomDeleteMessage(String userName){
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_CUSTOM_DELETED);
        msg.setMessageBody(userName);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }
    
}
