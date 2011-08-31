/*
 * $Id: SubMoInfo.java, 2011-3-28 ����11:27:05 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: SubMoInfo
 * </p>
 * <p>
 * Description:
 * �¼��������̬��Ϣ
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:27:05
 * modified [who date description]
 * check [who date description]
 */
public class SubMoInfo implements Serializable {
    
    private static final long serialVersionUID = -9027494337348313906L;
    
    /**����*/
    private Integer index;
    /**�ӽڵ�ɽ��ܵĹ����������*/
    private List<String> acceptedKinds;
    /**�ӽڵ�ɽ��ܵĹ������������*/
    private List<String> acceptedSubKinds;
    /**�ӽڵ�ɽ��ܵĹ��������ϸ����*/
    private List<String> acceptedTypes;
    /**�ӽڵ��Ƿ�ɲ�ε�*/
    private boolean fixed;
    /**������Ϣ*/
    private Map<String,String> additions;
    
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }
    public List<String> getAcceptedKinds() {
        return acceptedKinds;
    }
    public void setAcceptedKinds(List<String> acceptedKinds) {
        this.acceptedKinds = acceptedKinds;
    }
    public List<String> getAcceptedSubKinds() {
        return acceptedSubKinds;
    }
    public void setAcceptedSubKinds(List<String> acceptedSubKinds) {
        this.acceptedSubKinds = acceptedSubKinds;
    }
    public List<String> getAcceptedTypes() {
        return acceptedTypes;
    }
    public void setAcceptedTypes(List<String> acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }
    public boolean isFixed() {
        return fixed;
    }
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
    public Map<String, String> getAdditions() {
        return additions;
    }
    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }

    

}
