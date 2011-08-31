/*
 * $Id: MvcSampleModel.java, 2010-3-29 ÏÂÎç02:16:49 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.model;

import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: MvcSampleModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç02:16:49
 * modified [who date description]
 * check [who date description]
 */
public class MvcSampleModel extends AbstractClientModel<Object>{

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if(objects != null){
            for (Object object : objects) {
                SampleTableData exampleTableData = new SampleTableData();
                exampleTableData.setRealExampleTableData((RealSampleTableData) object);
                boList.add(exampleTableData);
            }
        }
    }

}
