/*
 * $Id: ConsumerListener.java, 2010-7-1 上午11:12:38 jimsu Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.def.collaboration.producerconsumer;

import java.util.Collection;

/**
 * <p>
 * Title: ConsumerListener
 * </p>
 * <p>
 * Description: 消费者处理
 * </p>
 * 
 * @author sufeng
 * created 2010-7-1 上午11:12:38
 * modified [who date description]
 * check [who date description]
 */
public interface ConsumerListener {

    /**
     * 消费者对其进行处理
     * @param objsInQueue 生产者传递过来的数据
     */
    public void handle(Collection<Object> objsInQueue);
    
}
