/*
 * $Id: DefaultStatusModel.java, 2009-3-5 上午09:32:25 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.event.EventListenerList;

/**
 * <p>
 * Title: DefaultStatusModel
 * </p>
 * <p>
 * Description:默认状态的Model
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:32:25
 * modified [who date description]
 * check [who date description]
 */
public class DefaultStatusModel implements StatusModel,PropertyChangeListener{
    
    /** statuses list */
    private List<Status> statuses ; 
    
    /** List of listeners */
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * custuctor
     */
    public DefaultStatusModel(){
        statuses = new ArrayList<Status>();
        
    }
    
    /* 
     * @see StatusModel#getStatusCount()
     */
    public int getStatusCount() {
        return statuses.size();
    }
    
    /* 
     * @see StatusModel#getStatusIcon(int)
     */
    public Icon getStatusIcon(int index) {

        Status status = statuses.get(index);
        return status.getIcon();
        
    }
    
    /* 
     * @see StatusModel#getStatusValue(int)
     */
    public Object getStatusValue(int index) {
        
        Status status = statuses.get(index);
        return status.getUserObject();
        
    }
    

    /* 
     * @see StatusModel#setStatusIcon(int)
     */
    public void setStatusIcon(Icon icon,int index) {

        Status status = statuses.get(index);
        status.setIcon(icon);
        //fireStatusChanged(new StatusChangeEvent(this,index));

    }
    
    /* 
     * @see com.shjv.client.framework.component.status.StatusModel#setStatusVaule(java.lang.Object, int)
     */
    public void setStatusVaule(Icon icon, Object value, int index) {
        
        Status status = statuses.get(index);
        status.setUserObject(value);
        status.setIcon(icon);
        
        //fireStatusChanged(new StatusChangeEvent(this,index));
        
    }
    
    public void setStatusVaule(Object value, int index) {
        
        Status status = statuses.get(index);
        status.setUserObject(value);
        //fireStatusChanged(new StatusChangeEvent(this,index));
    }
    
      
    

    /**
     * Adds status to model
     * @param value
     * @param icon
     */
    public void addStatus(Status status){
        insertStatus(getStatusCount(), status);
    }
    
    /**
     * Insert status by index
     * @param index
     * @param Status
     */
    public synchronized void insertStatus(int index, Status status){
        
        this.statuses.add(index, status);
        status.addPropertyChangeListener(this);
        fireStatusChanged(new StatusChangeEvent(this, index, StatusChangeEvent.INSERT));
    }
    
    /**
     * Removes status by index
     * @param index
     */
    public synchronized void removeStatus(int index){
        
        Status status = statuses.remove(index);
        status.removePropertyChangeListener(this);
        fireStatusChanged(new StatusChangeEvent(this, index, StatusChangeEvent.REMOVE));
    }
    
    /* 
     * @see StatusModel#isExpandable(int)
     */
    public boolean isExpandable(int index) {
        
        Status status = statuses.get(index);
        return status.isExpand();
    }
    
    /* 
     * @see StatusModel#removeStatusModelListener(com.shjv.client.framework.component.status.StatusModelListener)
     */
    public void removeStatusModelListener(StatusModelListener l) {
        this.listenerList.remove(StatusModelListener.class,l);
    }
    
    /* 
     * @see StatusModel#addStatusModelListener(com.shjv.client.framework.component.status.StatusModelListener)
     */
    public void addStatusModelListener(StatusModelListener l) {
        this.listenerList.add(StatusModelListener.class,l);
    }
    
    /**
     * Forwards the given notification event to all
     * <code>StatusModelListeners</code> that registered
     * themselves as listeners for this table model.
     *
     * @param e  the event to be forwarded
     */
    public void fireStatusChanged(StatusChangeEvent e) {
        // Guaranteed to return TreeComboPane non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==StatusModelListener.class) {
                ((StatusModelListener)listeners[i+1]).statusChanged(e);
            }
        }
    }

    
    public int getWidth(int index) {

        Status status = statuses.get(index);
        return status.getWidth();
    }

    
    public void setWidth(int index, int width) {

        Status status = statuses.get(index);
        
        if(status.getWidth()!=width){
            status.setWidth(width);
            //fireStatusChanged(new StatusChangeEvent(this));
        }
    }


    /**
     * 根据状态栏的位置index获取Status对象
     */
    public Status getStatus(int index) {

        return statuses.get(index);
    }
    /**
     * 根据状态栏位置index设置status
     */
    public void setStatus(int index, Status status) {
        
        if(status==null){
            throw new IllegalArgumentException("status can not be null");
        }
        Status old = getStatus(index);
        if(old!=null){
            old.removePropertyChangeListener(this);
        }
        statuses.set(index, status);
        status.addPropertyChangeListener(this);
        fireStatusChanged(new StatusChangeEvent(this, index));
    }

  
    public int indexOf(Status status) {

        return statuses.indexOf(status);
    }

    public String getStatusTip(int index) {

        return getStatus(index).getToolTip();
    }

    public void setStatusTip(int index, String tip) {
        
        getStatus(index).setToolTip(tip);
        //fireStatusChanged(new StatusChangeEvent(this,index));
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        Status status = (Status)evt.getSource();
        int index = indexOf(status);
        
        StatusChangeEvent event = new StatusChangeEvent(this, index);
        this.fireStatusChanged(event);
        
    }

 
}
