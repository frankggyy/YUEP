/*
 * $Id: MoStaticInfo.java, 2011-3-28 上午11:23:49 yangtao Exp $
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
 * Title: MoStaticInfo
 * </p>
 * <p>
 * Description:
 * 管理对象静态信息
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:23:49
 * modified [who date description]
 * check [who date description]
 */
public class MoStaticInfo implements Serializable {
    private static final long serialVersionUID = 1020376623910679060L;
    /**
     * 管理对象详细类型
     */
    private String type;
    /**
     * 管理对象大类,ne,nm,port,card等等
     */
    private String kind;
    /**
     * 管理对象子类型,可以由开发者自己定义
     */
    private String subkind;
    /**
     * 下级管理对象取值范围，如果没有定义subRange，则系统自动生成。subRange为空表示对下级管理对象没有限制
     */
    private String subRange;
    /**
     * 辅助信息，第三方开发者可以加入定义的静态信息
     */
    private Map<String,String> additions;
    /**
     * 下级管理对象静态信息
     */
    private List<SubMoInfo> subMoInfos;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getSubkind() {
        return subkind;
    }
    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }
    public String getSubRange() {
        return subRange;
    }
    public void setSubRange(String subRange) {
        this.subRange = subRange;
    }
    public Map<String, String> getAdditions() {
        return additions;
    }
    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }
    public List<SubMoInfo> getSubMoInfos() {
        return subMoInfos;
    }
    public void setSubMoInfos(List<SubMoInfo> subMoInfos) {
        this.subMoInfos = subMoInfos;
    }
    
    
    

}
