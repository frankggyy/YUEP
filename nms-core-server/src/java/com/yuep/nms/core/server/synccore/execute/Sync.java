/*
 * $Id: Sync.java, 2009-3-6 ����03:43:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: Sync
 * </p>
 * <p>
 * Description: <br>
 * ҵ��ͬ���ӿڣ�����ҵ��ͬ���඼Ҫʵ�ָýӿ�
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 ����03:43:58
 * modified [who date description]
 * check [who date description]
 */
public interface Sync {
    /**
     * ͬ������
     * 
     * @param target
     *            target�п����ǵ�ǰͬ���ڵ�ĸ��ڵ㣬Ҳ�п��ܾ��ǵ�ǰ�ڵ�
     * @param listeners
     *            ͬ�����̼���������
     */
    public void sync(MoNaming target, List<SyncListener> listeners);

}
