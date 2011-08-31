/*
 * $Id: StatusChangeEvent.java, 2009-3-5 上午09:20:26 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.util.EventObject;

/**
 * <p>
 * Title: StatusChangeEvent
 * </p>
 * <p>
 * Description:状态栏改变事件
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:20:26
 * modified [who date description]
 * check [who date description]
 */
public class StatusChangeEvent extends EventObject {
    /**
     * 
     */
    private static final long serialVersionUID = 7752530090327297274L;

    /**all column be changed  */
    public static final int ALL = -1;
    
    public static final int INSERT = 1, UPDATE = 2, REMOVE = 3;
    
    /** indicate which column was changed*/
    private int index;
    
    /** indicates that columns were be insert, remove, or update */
    private int type;
    
    /**
     * constructor
     * @param source
     */
    public StatusChangeEvent(Object source) {

        super (source);
        this.index = ALL;
        this.type = UPDATE;
    }
    /**
     * constructor
     * @param source
     * @param index
     */
    public StatusChangeEvent(Object source,int index) {

        super (source);
        this.index = index;
        this.type = UPDATE;
    }
    
    /**
     * constructor
     * @param source
     * @param index
     * @param type
     */
    public StatusChangeEvent(Object source, int index, int type) {
        super(source);
        this.index = index;
        this.type = type;
    }
    
    /**
     * Returns the index of changed column
     * @return
     */
    public int getIndex(){
        return this.index;
    }
    /**
     * 获取事件类型
     * @return
     * public static final int ALL = -1;
       public static final int INSERT = 1, UPDATE = 2, REMOVE = 3; 
     */
    public int getType() {
        return type;
    }
    /**
     * 设置事件类型
     * @param type
     *      public static final int ALL = -1;
       		public static final int INSERT = 1, UPDATE = 2, REMOVE = 3; 
     */
    public void setType(int type) {
        this.type = type;
    }

}
