/*
 * $Id: RealSampleTableData.java, 2010-3-29 ÏÂÎç02:20:07 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.model;

import com.yuep.core.client.component.window.MessageDialog.MessageType;


/**
 * <p>
 * Title: RealSampleTableData
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç02:20:07
 * modified [who date description]
 * check [who date description]
 */
public class RealSampleTableData {
    
    private long id;
    
    private String column3;
    
    private String column4;
    
    private int column2;
    
    private MessageType column5;
    
    private String column6;

    private boolean column7;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the column4
     */
    public String getColumn4() {
        return column4;
    }

    /**
     * @param column4 the column4 to set
     */
    public void setColumn4(String column4) {
        this.column4 = column4;
    }

    /**
     * @return the column7
     */
    public boolean isColumn7() {
        return column7;
    }

    /**
     * @param column7 the column7 to set
     */
    public void setColumn7(boolean column7) {
        this.column7 = column7;
    }

    /**
     * @return the column3
     */
    public String getColumn3() {
        return column3;
    }

    /**
     * @param column3 the column3 to set
     */
    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    /**
     * @return the column2
     */
    public int getColumn2() {
        return column2;
    }

    /**
     * @param column2 the column2 to set
     */
    public void setColumn2(int column2) {
        this.column2 = column2;
    }

    /**
     * @return the column5
     */
    public MessageType getColumn5() {
        return column5;
    }

    /**
     * @param column5 the column5 to set
     */
    public void setColumn5(MessageType column5) {
        this.column5 = column5;
    }

    /**
     * @return the column6
     */
    public String getColumn6() {
        return column6;
    }

    /**
     * @param column6 the column6 to set
     */
    public void setColumn6(String column6) {
        this.column6 = column6;
    }
}
