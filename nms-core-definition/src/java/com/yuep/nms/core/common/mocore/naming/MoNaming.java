/*
 * $Id: MoNaming.java, 2011-3-24 ����04:24:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.naming;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: MoNaming
 * </p>
 * <p>
 * Description: <br>
 * Mo��������
 * </p>
 * 
 * @author yangtao
 * created 2011-3-24 ����04:24:31
 * modified [who date description]
 * check [who date description]
 */
public class MoNaming implements Serializable {
    
    private static final long serialVersionUID = 7086603417891869301L;

    /**
     * ��ʶ
     */
    private String name;

    /** �豸����ָ��� */
    public static final char PRIMARY_DELIMETER = '/';
    /** ʵ����ʶ */
    public static final char SECONDARY_DELIMETER = '=';
    /** ������Ϣ */
    public static final char THIRD_DELIMETER = ':';
    
    public MoNaming(String name) {
        if (StringUtils.isEmpty(name))
            throw new IllegalArgumentException(" name is a null argument");
        this.name = name;
    }

    /**
     * ���캯��
     * 
     * @param parent
     * @param instance
     * @param moType
     */
    protected MoNaming(MoNaming parent, int instance, String moType) {
        if (StringUtils.isEmpty(moType))
            throw new IllegalArgumentException(" moType is a null argument");
        StringBuffer sb = new StringBuffer();
        if (parent != null)
            sb.append(parent);
        sb.append(moType);
        sb.append(SECONDARY_DELIMETER);
        sb.append(instance);
        sb.append(PRIMARY_DELIMETER);
        name = sb.toString();
    }

    /**
     * ���캯��
     * 
     * @param parent
     * @param instance
     * @param eqType
     */
    protected MoNaming(MoNaming parent, int instance, String moType, String addition) {
        if (StringUtils.isEmpty(moType))
            throw new IllegalArgumentException(" moType is a null argument");
        StringBuffer sb = new StringBuffer();
        if (parent != null)
            sb.append(parent);
        sb.append(moType);
        sb.append(SECONDARY_DELIMETER);
        sb.append(instance);
        if (StringUtils.isNotBlank(addition)) {
            sb.append(THIRD_DELIMETER);
            sb.append(addition);
        }
        sb.append(PRIMARY_DELIMETER);
        name = sb.toString();
    }

    /**
     * �Ƿ�Ϊnull
     * 
     * @return
     */
    public static boolean isNull(MoNaming MoNaming) {
        if (MoNaming == null)
            return true;
        return StringUtils.isEmpty(MoNaming.getName());
    }

    /**
     * ��ȡ��ǰMoNaming�Ĺ����������
     * 
     * @return
     */
    public String getMoType() {
        String temp = name.substring(0, name.lastIndexOf(PRIMARY_DELIMETER));
        return temp.substring(temp.lastIndexOf(PRIMARY_DELIMETER) + 1, temp.lastIndexOf(SECONDARY_DELIMETER));

    }

    /**
     * ��ȡ�������ʵ��
     * 
     * @return
     */
    public int getInstance() {
        String temp = name.substring(name.lastIndexOf(SECONDARY_DELIMETER));

        int endIndex = 0;
        if (StringUtils.isEmpty(this.getAddition())) {
            endIndex = temp.lastIndexOf(PRIMARY_DELIMETER);
        } else {
            endIndex = temp.lastIndexOf(THIRD_DELIMETER);
        }

        int instance = Integer.parseInt(temp.substring(1, endIndex));
        return instance;
    }

    /**
     * ��ȡ��չ��Ϣ
     * 
     * @return
     */
    public String getAddition() {
        String temp = name.substring(name.lastIndexOf(SECONDARY_DELIMETER));
        int index = temp.lastIndexOf(THIRD_DELIMETER);
        if (index < 0)
            return "";
        String addition = temp.substring(index + 1, temp.lastIndexOf(PRIMARY_DELIMETER));
        return addition;
    }

    /**
     * ��ȡ���ڵ�
     * 
     * @return MoNaming
     */
    public MoNaming getParent() {
        String temp = name.substring(0, name.lastIndexOf(PRIMARY_DELIMETER));
        String dn = temp.substring(0, temp.lastIndexOf(PRIMARY_DELIMETER) + 1);
        if (dn == null || dn.equals(""))
            return null;
        return new MoNaming(dn);
    }

    /**
     * ����moType��ȡ���ڵ�
     * @param moType
     * @return
     *        MoNaming
     */
    public MoNaming getParentByMoType(String moType){
        MoNaming parent=this.getParent();
        while(parent!=null){
            if(parent.getMoType().equals(moType)){
               return  parent;
            }
            parent=parent.getParent();
        }
        return null;
    }
    /**
     * ����EqObjectType��ȡ�丸MoNaming
     * 
     * @param moType
     *        
     * @return
     *        MoNaming
     */
    public MoNaming getMoNamingByMoType(String moType) {
        MoNaming MoNaming = this;
        while (true) {
            if (ObjectUtils.equals(MoNaming.getMoType(), moType))
                return MoNaming;
            MoNaming = MoNaming.getParent();
            if (MoNaming == null)
                return null;
        }
    }

    /**
     * ��ȡ���ڵ�
     * @return
     */
    public MoNaming getRoot(){
        MoNaming moNaming=this;
        while(moNaming.getParent()!=null){
            moNaming=moNaming.getParent();
        }
        return moNaming;
    }
    /**
     * �Ƿ�Ϊother�ӽڵ�
     * 
     * @param other
     * @return
     */
    public boolean isChild(MoNaming other) {
        return this.getName().startsWith(other.getName()) && !this.equals(other);
    }

    /**
     * �Ƿ�Ϊother�ڵ㸸�ڵ�
     * 
     * @param other
     * @return
     */
    public boolean isParent(MoNaming other) {
        return other.getName().startsWith(this.getName()) && !this.equals(other);
    }

    /**
     * �Ƿ����ָ��MoNaming
     * @param other
     * @return
     */
    public boolean contain(MoNaming other) {
        return this.getName().startsWith(other.getName());
    }

    /**
     * ��ȡ��ǰname����
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * ����ʼMoNaming����ֹMoNaming���ӣ���ȡ�µ�MoNaming
     * @param start
     *        ��ʼMoNaming
     * @param end
     *        ��ֹMoNaming
     * @return
     *        ����MoNaming
     */
    public static MoNaming append(MoNaming startMoNaming, MoNaming endMoNaming) {
        String startStr="";
        String endStr="";
        if(startMoNaming!=null)
            startStr=startMoNaming.getName();
        if(endMoNaming!=null)
            endStr=endMoNaming.getName();
        return new MoNaming(startStr + endStr);
    }

    /**
     * �ָ�Ŀ��MoNaming����ʼ���֣���ȡĿ��MoNaming��ʣ�ಿ��
     * @param start
     *        ��ʼ����
     * @param target
     *        Ŀ��MoNaming
     * @return
     *        Ŀ��MoNaming��ʣ�ಿ��
     *      
     */
    public static MoNaming split(MoNaming start, MoNaming target) {
        if(start==null)
            return target;
        if(target==null)
            return null;
        String moStr=StringUtils.substringAfter(target.getName(), start.getName());
        if(StringUtils.isEmpty(moStr))
            return null;
        return new MoNaming(moStr);
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MoNaming))
            return false;
        MoNaming name = (MoNaming) obj;
        return this.getName().equals(name.getName());
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

}
