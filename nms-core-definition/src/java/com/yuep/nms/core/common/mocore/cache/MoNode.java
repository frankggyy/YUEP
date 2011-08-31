/*
 * $Id: MoNode.java, 2011-3-28 ����03:02:50 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.cache;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: MoNode
 * </p>
 * <p>
 * Description:
 * MoCache�ڵ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����03:02:50
 * modified [who date description]
 * check [who date description]
 */
final public class MoNode implements Serializable {
    private static final long serialVersionUID = 3313168786430739325L;
    /**�������*/
    private Mo mo;
    /**��������ӽڵ�*/
    private List<MoNode> children=new CopyOnWriteArrayList<MoNode>();
    /**������󸸽ڵ�*/
    private MoNode parent;
    public Mo getMo() {
        return mo;
    }
    public void setMo(Mo mo) {
        this.mo = mo;
    }
   
    public List<MoNode> getChildren() {
        return children;
    }
    public void setChildren(List<MoNode> children) {
        this.children = children;
    }
    public MoNode getParent() {
        return parent;
    }
    public void setParent(MoNode parent) {
        this.parent = parent;
    }
    
    /**
     * ɾ���ӽڵ�
     * @param moNode
     */
    public void removeChild(MoNode moNode){
        children.remove(moNode);
    }
    /**
     * ����ӽڵ�
     * @param moNode
     */
    public void addChild(MoNode moNode){
        children.add(moNode);
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mo == null) ? 0 : mo.hashCode());
        return result;
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MoNode other = (MoNode) obj;
        return ObjectUtils.equals(other.getMo(), this.mo);
    }
    
    

}
