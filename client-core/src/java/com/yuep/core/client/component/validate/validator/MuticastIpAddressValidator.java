/*
 * $Id: MuticastIpAddressValidator.java, 2009-12-7 下午04:44:32 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import com.yuep.base.util.format.IpAddrFormatter;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: MuticastIpAddressValidator
 * </p>
 * <p>
 * Description:组播IP地址自动校验器
 * </p>
 * 
 * @author yangtao
 * created 2009-12-7 下午04:44:32
 * modified [who date description]
 * check [who date description]
 */
public class MuticastIpAddressValidator extends IpAddressValidator {
    private static final long serialVersionUID = 1475295940181371929L;

    /**
     * Constructor
     * @param range 校验范围
     */
    public MuticastIpAddressValidator(ValidatorRange range) {
        super(range);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.validator.IpAddressValidator#validate(java.lang.String)
     */
    public ValidateMessage validate(String value) {
        ValidateMessage validMessage=new ValidateMessage();
        if (!IpAddrFormatter.isMulticastAddress(value)) {
            validMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String msg = ClientCoreContext.getString("common.validate.muticastip.message","224.0.1.0","238.255.255.255");
            validMessage.setMsg(msg);
        }else{
            validMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validMessage.setMsg("");
        }
        return validMessage;
    }
}
