/*
 * $Id: DefaultClientModel.java, 2010-1-20 下午04:00:31 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;


/**
 * <p>
 * Title: DefaultClientModel
 * </p>
 * <p>
 * Description: 默认的客户端Model的实现
 * </p>
 * 
 * @author aaron lee
 * created 2010-1-20 下午04:00:31
 * modified [who date description]
 * check [who date description]
 */
public class DefaultClientModel<T extends Object> extends AbstractClientModel<T>{

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if(objects != null && objects.length > 0){
            Object object = objects[0];
            boList.add((T) object);
        }
    }

}
