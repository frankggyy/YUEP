/*
 * $Id: SampleTableData.java, 2010-3-29 ÏÂÎç02:20:29 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.model;

/**
 * <p>
 * Title: SampleTableData
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç02:20:29
 * modified [who date description]
 * check [who date description]
 */
public class SampleTableData {
    
    private RealSampleTableData realExampleTableData;
    private String column1;
    
    /**
     * @return the realExampleTableData
     */
    public RealSampleTableData getRealExampleTableData() {
        return realExampleTableData;
    }
    /**
     * @param realExampleTableData the realExampleTableData to set
     */
    public void setRealExampleTableData(RealSampleTableData realExampleTableData) {
        this.realExampleTableData = realExampleTableData;
    }
    /**
     * @return the column1
     */
    public String getColumn1() {
        return realExampleTableData.getColumn3() + "£º" + realExampleTableData.getColumn4();
    }
    /**
     * @param column1 the column1 to set
     */
    public void setColumn1(String column1) {
        this.column1 = column1;
    }
}
