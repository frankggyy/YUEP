/*
 * $Id: AbstractFilter.java, 2008-12-26 上午10:32:38 Victor Exp $
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
 * Description:导航树上用户输入文字本过滤器是否过滤
 * </p>
 * 
 * @author Victor
 * created 2008-12-26 上午10:32:38
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractFilter {

    /**
     * 过滤器描述信息,将显示在过滤栏的tooltip上
     * 
     * @return 过滤器描述信息
     */
    public abstract String getDescription();

    /**
     * 对某个名称是否采用过滤，根据参数传入的name判断本过滤器是否过滤，如果返回false，将不会调用filter方法。
     * 
     * @param name
     *            名字，过滤栏输入信息
     * @return 本过滤器是否有效
     */
    public abstract boolean isFilter(String name);

    /**
     * 过滤实现，如果isFilter方法返回true，框架会调用本方法过滤每个节点data，实现方法需要根据过滤栏输入name 来判断节点data是否显示
     * 
     * 例外：如果子节点显示，其所有父节点都会自动显示。
     * 
     * @param data
     *            树节点
     * @param name
     *            用户输入的文字
     * @return 是否显示节点data，如果需要显示则返回true。
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
