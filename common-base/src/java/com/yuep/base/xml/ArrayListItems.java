/*
 * $Id: ArrayListItems.java, 2009-2-19 ����11:58:29 sufeng Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: ArrayListItems
 * </p>
 * <p>
 * Description: Ϊ��ʹ��castor�ļ��ϣ���list���а�װ
 * </p>
 * 
 * @author sufeng
 * created 2009-2-19 ����11:58:29
 * modified [who date description]
 * check [who date description]
 */
public class ArrayListItems<T> implements Serializable{
    
    private static final long serialVersionUID = -5107393535117241810L;
    
    /**
     * ������item
     */
    private List<T> items=null;
    
    public List<T> getItems(){
        return this.items;
    }
    
    public void setItems(List<T> items){
        this.items=items;
    }
    
}
