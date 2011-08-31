/*
 * $Id: CoreContext4Container.java, 2010-12-13 ����05:59:39 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.container.def;

import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: CoreContext4Container
 * </p>
 * <p>
 * Description:ģ��������ӿڶ������Ĺ���ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2010-12-13 ����05:59:39
 * modified [who date description]
 * check [who date description]
 */
public interface CoreContext4Container {
    
    /**
     * ���ñ���ϵͳ����Ϣ������������Ϣ(Զ����ϵͳ��������Ϣ��RemoteCommunicationObject��)
     * @param messageMetadata
     */
    public void setSelfMessageMetadata(MessageMetadata messageMetadata);

}
