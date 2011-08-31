/*
 * $Id: Validator.java, 2009-3-9 上午10:50:26 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import com.yuep.core.client.mvc.validate.ValidateMessage;


/**
 * <p>
 * Title: Validator
 * </p>
 * <p>
 * Description:自动校验器的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 上午10:50:26
 * modified [who date description]
 * check [who date description]
 */
public interface Validator {

    /**
     * 校验控件的输入内容
     * @param value 输入内容
     * @return ValidateMessage 校验结果信息
     */
    ValidateMessage validate(String value);
    /**
     * 设置输入内容长度校验
     * @param range
     */
    void setRange(ValidatorRange range);
}
