/*
 * $Id: StatusModel.java, 2009-3-5 ÉÏÎç09:36:11 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import javax.swing.Icon;

/**
 * <p>
 * Title: StatusModel
 * </p>
 * <p>
 * Description:Status Model
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 ÉÏÎç09:36:11
 * modified [who date description]
 * check [who date description]
 */
public interface StatusModel {
    /**
     * Returns status index 
     * @param index the index of stats
     * @return status
     */
    public Status getStatus(int index);
    
    /**
     * Sets status with index
     * @param index
     * @param status
     */
    public void setStatus(int index, Status status);
    /**
     * Returns status count
     * @return int
     */
    public int getStatusCount();
    
    /**
     * Return status icon
     * @param index
     * @return
     */
    public Icon getStatusIcon(int index);
    
    /**
     * Return the width of status cell specified by index
     * @param index
     * @return int  width 
     */
    public int getWidth(int index);
    /**
     * Sets status value icon
     * @param index
     */
    public void setStatusIcon(Icon icon,int index);
    
    /**
     * set width of status cell
     * @param index
     * @param width
     */
    public void setWidth(int index, int width);
        
    /**
     * Sets status value
     * @param obj
     * @param index
     */
    public void setStatusVaule(Object obj,int index);
    
    
    /**
     * Returns status value by index
     * @param index 
     * @return obj
     */
    public Object getStatusValue(int index);
    
    
    /**
     * Returns index of status
     * @param status
     * @return int
     */
    public int indexOf(Status status);
    
    /**
     * Returns tooptip string of status cell
     * @param index
     * @return String
     */
    String getStatusTip(int index);
        
    /**
     * Sets toop tip for status cell by index
     * @param index
     * @param tip
     */
    void setStatusTip(int index, String tip);
        
    
    /**
     * Returns status wheather expandable
     * @param index
     * @return boolean
     */
    public boolean isExpandable(int index);
    
    /**
     * Adds TreeComboPane listener to the list that is notified each time TreeComboPane change
     * to the data model occurs.
     *
     * @param   l       the StatusModelListener
     */
    public void addStatusModelListener(StatusModelListener l);

    /**
     * Removes TreeComboPane listener from the list that is notified each time TreeComboPane
     * change to the data model occurs.
     *
     * @param   l       the StatusModelListener
     */
    public void removeStatusModelListener(StatusModelListener l);
}
