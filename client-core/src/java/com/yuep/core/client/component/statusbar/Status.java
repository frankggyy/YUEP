/*
 * $Id: Status.java, 2009-3-5 上午09:27:17 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * <p>
 * Title: Status
 * </p>
 * <p>
 * Description:状态栏对象
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:27:17
 * modified [who date description]
 * check [who date description]
 */
public class Status {

    private Object userObject;
    
    private boolean expand;
    
    private Icon    icon;
    
    private int width;
    /** the tip for status cell */ 
    private String toolTip;
    
    private Action action;
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    /** 
     * Constructor with userobject 
     * @param userObject the status object provided by user
     * @param expand    the status cell will be expand automaticly
     * @param icon      the icon for status cell
     */
    public Status(Object userObject, boolean expand, Icon icon){
        this(userObject, expand, icon, 0);
    }
    
    /**
     * Constructor with userobject 
     * @param userObject the status object provided by user
     * @param expand    the status cell will be expand automaticly
     * @param icon      the icon for status cell
     * @param width     the fixed width  for statuc cell if it is not expand
     * 
     */
    public Status(Object userObject, boolean expand, Icon icon, int width){
        this(userObject, expand, icon, width, null);
    }
    
    public Status(Object userObject, boolean expand, Icon icon, int width, String toolTip){
        this.setUserObject(userObject);
        this.setExpand(expand);
        this.setIcon(icon);
        this.setWidth(width);
        this.setToolTip(toolTip);
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        
    }

    /**
     * 获取Status绑定的userObject
     * @return
     */
    public Object getUserObject(){
        return userObject;
    }
    /**
     * 设置Status绑定的UserObject
     * @param userObject
     */
    public void setUserObject(Object userObject){
        Object oldValue = this.userObject;
        this.userObject = userObject;
        propertyChangeSupport.firePropertyChange("userObject", oldValue, userObject);
    }
      
    public boolean isExpand(){
        return expand;
    }
    
    public void setExpand(boolean expand){
        boolean oldValue = this.expand;
        this.expand = expand;
        propertyChangeSupport.firePropertyChange("expand", oldValue, expand);
    }
    
    /**
     * 获取状态栏对象的Icon
     * @return
     */
    public Icon getIcon() {

        return icon;
    }
    /**
     * 设置状态栏对象Icon
     * @param icon
     */
    public void setIcon(Icon icon) {
        Object oldValue = this.icon;
        this.icon = icon;
        propertyChangeSupport.firePropertyChange("icon", oldValue, icon);
    }
    /**
     * 获取状态栏对象宽度
     * @return
     */
    public int getWidth() {

        return width;
    }
    /**
     * 设置状态栏对象宽度
     * @param width
     */
    public void setWidth(int width) {
        Object oldValue = this.width;
        this.width = width;
        propertyChangeSupport.firePropertyChange("width", oldValue, width);
    }
    /**
     * 获取toolTip
     * @return
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * 设置toolTip
     * @param toolTip
     */
    public void setToolTip(String toolTip) {
        Object oldValue = this.toolTip;
        this.toolTip = toolTip;
        propertyChangeSupport.firePropertyChange("width", oldValue, toolTip);
    }

    /**
     * 获取状态栏对象点击时的动作Action
     * @return
     */
    public Action getAction() {
        return action;
    }

    /**
     * 设置状态栏对象Action
     * @param action
     */
    public void setAction(Action action) {
        Object oldValue = this.action;
        this.action = action;
        propertyChangeSupport.firePropertyChange("width", oldValue, action);
    }
    
     /**
     * Adds a <code>PropertyChange</code> listener. Containers and attached
     * components use these methods to register interest in this 
     * <code>Action</code> object. When its enabled state or other property
     * changes, the registered listeners are informed of the change.
     *
     * @param listener  a <code>PropertyChangeListener</code> object
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    /**
     * Removes a <code>PropertyChange</code> listener.
     *
     * @param listener  a <code>PropertyChangeListener</code> object
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
