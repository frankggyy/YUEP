/*
 * $Id: AccessCheck.java, 2011-3-29 下午02:20:15 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.permit;


/**
 * <p>
 * Title: AccessCheck
 * </p>
 * <p>
 * Description: 访问权限验证接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 下午02:20:15
 * modified [who date description]
 * check [who date description]
 */
public interface AccessCheck {

    /**
     * 当前用户对mo是否有actionid的权限
     * @param actionId action ID操作ID
     * @param objectIds 操作对象
     * @return true有权限
     */
    public boolean check(String actionId, Object... objectIds);
    
}
