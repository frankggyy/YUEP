/*
 * $Id: AlwayTrueAccessCheck.java, 2011-3-29 下午02:24:24 sufeng Exp $
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
 * Title: AlwayTrueAccessCheck
 * </p>
 * <p>
 * Description: 默认的不进行权限检查的实现
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 下午02:24:24
 * modified [who date description]
 * check [who date description]
 */
public class AlwayTrueAccessCheck implements AccessCheck {

    @Override
    public boolean check(String actionId, Object... objectIds) {
        // 总返回true，表示不检查权限
        return true;
    }

}
