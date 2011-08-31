/*
 * $Id: MoStaticInfo.java, 2011-3-28 ����11:23:49 yangtao Exp $
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
 * �������̬��Ϣ
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:23:49
 * modified [who date description]
 * check [who date description]
 */
public class MoStaticInfo implements Serializable {
    private static final long serialVersionUID = 1020376623910679060L;
    /**
     * ���������ϸ����
     */
    private String type;
    /**
     * ����������,ne,nm,port,card�ȵ�
     */
    private String kind;
    /**
     * �������������,�����ɿ������Լ�����
     */
    private String subkind;
    /**
     * �¼��������ȡֵ��Χ�����û�ж���subRange����ϵͳ�Զ����ɡ�subRangeΪ�ձ�ʾ���¼��������û������
     */
    private String subRange;
    /**
     * ������Ϣ�������������߿��Լ��붨��ľ�̬��Ϣ
     */
    private Map<String,String> additions;
    /**
     * �¼��������̬��Ϣ
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
