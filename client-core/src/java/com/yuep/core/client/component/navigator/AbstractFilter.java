/*
 * $Id: AbstractFilter.java, 2008-12-26 ����10:32:38 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import twaver.Element;

/**
 * <p>
 * Title: AbstractFilter
 * </p>
 * <p>
 * Description:���������û��������ֱ��������Ƿ����
 * </p>
 * 
 * @author Victor
 * created 2008-12-26 ����10:32:38
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractFilter {

    /**
     * ������������Ϣ,����ʾ�ڹ�������tooltip��
     * 
     * @return ������������Ϣ
     */
    public abstract String getDescription();

    /**
     * ��ĳ�������Ƿ���ù��ˣ����ݲ��������name�жϱ��������Ƿ���ˣ��������false�����������filter������
     * 
     * @param name
     *            ���֣�������������Ϣ
     * @return ���������Ƿ���Ч
     */
    public abstract boolean isFilter(String name);

    /**
     * ����ʵ�֣����isFilter��������true����ܻ���ñ���������ÿ���ڵ�data��ʵ�ַ�����Ҫ���ݹ���������name ���жϽڵ�data�Ƿ���ʾ
     * 
     * ���⣺����ӽڵ���ʾ�������и��ڵ㶼���Զ���ʾ��
     * 
     * @param data
     *            ���ڵ�
     * @param name
     *            �û����������
     * @return �Ƿ���ʾ�ڵ�data�������Ҫ��ʾ�򷵻�true��
     */
    public abstract boolean filter(Element data, String name);

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getDescription().hashCode();
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof AbstractFilter))
            return false;
        String description = ((AbstractFilter) obj).getDescription();
        if(description == null)
            return false;
        String description2 = getDescription();
        if(description2 == null)
            return false;
        return description2.equals(description);
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getDescription();
    }
}
