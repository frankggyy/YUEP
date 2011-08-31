/*
 * $Id: MessageServiceManagerFactory.java, 2011-2-25 ����11:47:52 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.impl;

/**
 * <p>
 * Title: MessageServiceManagerFactory
 * </p>
 * <p>
 * Description:��Ϣ����Ĺ����࣬������ȡ��Ϣ����manager
 * </p>
 * 
 * @author sufeng
 * created 2011-2-25 ����11:47:52
 * modified [who date description]
 * check [who date description]
 */
public class MessageServiceManagerFactory {

    private static MessageServiceManager messageServiceManager=new MessageServiceManagerImpl();
    
    /**
     * ��ȡ��Ϣ���й���ķ���ӿ�
     * @return
     */
    public static MessageServiceManager getMessageServiceManager(){
        return messageServiceManager;
    }
    
}
