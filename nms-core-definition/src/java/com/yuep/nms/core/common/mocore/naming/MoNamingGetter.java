/*
 * $Id: MoNamingGetter.java, 2011-3-29 下午02:32:54 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.naming;

/**
 * <p>
 * Title: MoNamingGetter
 * </p>
 * <p>
 * Description: 得到Mo的标识名
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 下午02:32:54
 * modified [who date description]
 * check [who date description]
 */
public interface MoNamingGetter {

    /**
     * 返回管理对象标识
     * @return
     *       管理对象标识
     */
    public MoNaming getMoNaming();
    
}
