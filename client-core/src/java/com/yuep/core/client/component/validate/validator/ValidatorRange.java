/*
 * $Id: ValidatorRange.java, 2009-3-9 上午11:02:02 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

/**
 * <p>
 * Title: ValidatorRange
 * </p>
 * <p>
 * Description:自动校验器校验的范围
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 上午11:02:02
 * modified [who date description]
 * check [who date description]
 */
public class ValidatorRange {

    /**
     * 最小值
     */
    private int min;
    /**
     * 最大值
     */
    private int max;

    /**
     * Contructor
     * @param min 最小值
     * @param max 最大值
     */
    public ValidatorRange(int min, int max) {
        this.max = max;
        this.min = min;
    }

    /**
     * 返回最小值
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * 设置最小值
     * @param min
     *            the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * 返回最大值
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * 设置最大值
     * @param max
     *            the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 设置最小值和最大值
     * @param min 最小值
     * @param max 最大值
     */
    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
