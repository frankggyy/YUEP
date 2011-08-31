/*
 * $Id: ExitService.java, 2010-10-11 上午09:39:34 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.servercore.def;



/**
 * <p>
 * Title: ExitService
 * </p>
 * <p>
 * Description: 退出子系统
 * </p>
 * 
 * @author sufeng
 * created 2010-10-11 上午09:39:34
 * modified [who date description]
 * check [who date description]
 */
public interface ExitService {
    
    /**
     * 通知子系统退出
     */
    public void exit();

}
