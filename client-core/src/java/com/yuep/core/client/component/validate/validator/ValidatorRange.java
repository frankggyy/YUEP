/*
 * $Id: ValidatorRange.java, 2009-3-9 ����11:02:02 aaron lee Exp $
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
 * Description:�Զ�У����У��ķ�Χ
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 ����11:02:02
 * modified [who date description]
 * check [who date description]
 */
public class ValidatorRange {

    /**
     * ��Сֵ
     */
    private int min;
    /**
     * ���ֵ
     */
    private int max;

    /**
     * Contructor
     * @param min ��Сֵ
     * @param max ���ֵ
     */
    public ValidatorRange(int min, int max) {
        this.max = max;
        this.min = min;
    }

    /**
     * ������Сֵ
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * ������Сֵ
     * @param min
     *            the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * �������ֵ
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * �������ֵ
     * @param max
     *            the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * ������Сֵ�����ֵ
     * @param min ��Сֵ
     * @param max ���ֵ
     */
    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
