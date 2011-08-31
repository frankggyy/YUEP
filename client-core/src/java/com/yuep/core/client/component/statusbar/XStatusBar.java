/*
 * $Id: XStatusBar.java, 2009-2-20 下午01:20:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * <p>
 * Title: XStatusBar
 * </p>
 * <p>
 * Description:Extends status bar
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-20 下午01:20:59
 * modified [who date description]
 * check [who date description]
 */
public class XStatusBar  extends JComponent implements StatusModelListener{
    
    private static final long serialVersionUID = 9191202264089825855L;

    /**status bar default height */
    public static final int DEFAULT_HEIGHT = 20;
    
    /** The <code>StatusModel</code> of the statusBar. */
    protected StatusModel        dataModel;
    
    /** The all status labels*/
    private List<StatusBarCell>  statusComponents ;

    /**
     * constuctor
     */
    public XStatusBar() {
        
        this(null);
    }
    
    /**
     * constructs status bar by model
     * @param model
     */
    public XStatusBar(StatusModel model){
        
        this.addPropertyChangeListener(new PropertyHandle());
        
        if(model==null){
            model = new DefaultStatusModel();
        }
        this.setFont(new java.awt.Font("Dialog", 1, 12));
        this.setModel(model);
        
    }
    
    /**
     * Sets statusModel
     * @param model
     */
    public void setModel(StatusModel model){
        if (model == null) {
            throw new IllegalArgumentException("Cannot set null StatusModel");
        }
        if (this.dataModel != model) {
            StatusModel old = this.dataModel;
            if (old != null) {
                old.removeStatusModelListener(this);
            }
            this.dataModel = model;
            dataModel.addStatusModelListener(this);


            firePropertyChange("model", old, dataModel);
        }
    }
    
    /**
     * Returns statusModel of statusBar
     * @return StatusModel
     */
    public StatusModel getModel(){
        return this.dataModel;
    }
    
    
    /**
     * refresh status bar components
     *
     */
    public void refreshUI(){
        
        this.hideAllBubble();
        
        statusComponents = new ArrayList<StatusBarCell>(dataModel.getStatusCount());
        
        for(int i=0,n=dataModel.getStatusCount();i<n;i++){
            StatusBarCell cell = createStatusCell(dataModel.getStatus(i));
            statusComponents.add(cell);
        }
        reLayout();
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        
    }
    /**
     * 重新布局
     */
    protected void reLayout(){
        
        this.removeAll();
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        
        GridBagConstraints gbc = new GridBagConstraints ();
        for(int i=0;i<statusComponents.size();i++){
            
            StatusBarCell cell = statusComponents.get(i);
            
            JComponent cellComp = cell.getComponent();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            if(dataModel.isExpandable(i)){
                gbc.weightx = 100;
                gbc.weighty = 0;
            }else{
                gbc.weightx = 0;
                gbc.weighty = 0;
            }
            if(i==0){
                gbc.insets = new Insets (3, 0, 0, 0);
            }else{
                gbc.insets = new Insets (3, 3, 0, 0);
            }
            this.add (this, cellComp, gbl, gbc, i+1, 0, 1, 1);
            
        }
        this.invalidate();
    }
    
    /**
     * 获取状态栏状态块
     * @param status
     * @return
     */
    protected StatusBarCell createStatusCell(Status status){
        return new DefaultStatusBarCell(status);
    }

    /* 
     * @see com.shjv.client.framework.component.status.StatusModelListener#StatusChanged(com.shjv.client.framework.component.status.StatusChangeEvent)
     */
    public void statusChanged(StatusChangeEvent event) {

        if(event.getIndex()==StatusChangeEvent.ALL){
            this.refreshUI();
        }else if(event.getType()==StatusChangeEvent.INSERT){
            statusInserted(event.getIndex());
        }else if(event.getType()==StatusChangeEvent.REMOVE){
            statusRemoved(event.getIndex());
        }else if(event.getType()==StatusChangeEvent.UPDATE){
            statusUpdated(event.getIndex());
        }

    }

    
    void statusInserted(int index){
        Status status = getModel().getStatus(index);
        StatusBarCell cell = createStatusCell(status);
        this.statusComponents.add(index, cell);
        reLayout();
    }
    
    void statusUpdated(int index){
        refreshStatus(index);
    }
    
    void statusRemoved(int index){
        this.statusComponents.remove(index);
        this.reLayout();
    }
    
    /**
     * refresh one status of bar by index
     * @param index
     */
    public void refreshStatus(int index){
        
        getStatusCell(index).refresh();

    }
   
   
    
    /**
     * add visual component to gridBagLayout 
     * @param c
     *            Container
     * @param comp
     *            Component will be add
     * @param gbl
     *            GridBagLayout
     * @param gbc
     *            GridBagConstraints
     * @param x
     * @param y
     * @param width
     * @param height
     */
    protected void add(Container c, Component comp, GridBagLayout gbl, GridBagConstraints gbc, int x, int y, int width, int height) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;

        gbl.setConstraints (comp, gbc);
        c.add (comp);
    }
    
    class PropertyHandle implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent event){
            if(event.getPropertyName().equals("model")){
                refreshUI();
            }
        }
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        
        return computeToolTipLocation(event.getPoint());
        
    }
    
    static Point computeToolTipLocation(final Point mousePoint){
        Point point = new Point();
        int y = mousePoint.y;
        y = y - 20; 
        point.x = mousePoint.x;
        point.y = y;
        return point;
    }
    

    
    public StatusBarCell getStatusCell(int index){
        return statusComponents.get(index);
    }
    
    public void showBubble(int index, Object message){
        StatusBarCell cell = getStatusCell(index);
        cell.showBubble(message);
    }
    
    public void hideBubble(int index){
        StatusBarCell cell = getStatusCell(index);
        cell.hideBubble();
    }
    
    public void hideAllBubble(){
        if(statusComponents!=null){
            for(StatusBarCell cell:statusComponents){
                cell.hideBubble();
            }
        }
    }
}
