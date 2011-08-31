/*
 * $Id: ResultHandler.java, 2009-11-30 下午02:18:25 jimsu Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.def.collaboration.syncwait;


/**
 * <p>
 * Title: ResultHandler
 * </p>
 * <p>
 * Description: 需要各自实现子类，专门用来获取结果，并判断结果是否结束
 * </p>
 * 
 * @author sufeng
 * created 2009-11-30 下午02:18:25
 * modified [who date description]
 * check [who date description]
 */
public interface ResultHandler {

    /**
     * 处理请求，比如从数据库获取一条记录，下发配置到设备等，并返回结果
     * @return
     */
    public ResultStauts getSingleResult();
    
    /**
     * 以后用来进行异步接受外部的消息数据
     * @param data
     */
    public void postData(Object data);
    
}
