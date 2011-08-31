/*
 * $Id: SbiBindProperty.java, 2011-4-15 下午01:05:10 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.sbimanager.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SbiBindProperty
 * </p>
 * <p>
 * Description:
 * SBI绑定网元信息（用于调用到网元的路由）
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午01:05:10
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class SbiBindProperty implements Serializable {
    private static final long serialVersionUID = 7437193699524324423L;
    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    /**网元标识*/
    private MoNaming ne;
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    /**SBI标识*/
    private MoNaming sbiNaming;
    
    public MoNaming getNe() {
        return ne;
    }
    public void setNe(MoNaming ne) {
        this.ne = ne;
    }
    public MoNaming getSbiNaming() {
        return sbiNaming;
    }
    public void setSbiNaming(MoNaming sbiNaming) {
        this.sbiNaming = sbiNaming;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ne == null) ? 0 : ne.hashCode());
        result = prime * result
                + ((sbiNaming == null) ? 0 : sbiNaming.hashCode());
        return result;
    }
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
        SbiBindProperty other = (SbiBindProperty) obj;
        if (ne == null) {
            if (other.ne != null) {
                return false;
            }
        } else if (!ne.equals(other.ne)) {
            return false;
        }
        if (sbiNaming == null) {
            if (other.sbiNaming != null) {
                return false;
            }
        } else if (!sbiNaming.equals(other.sbiNaming)) {
            return false;
        }
        return true;
    }
   
    
    
    

}
