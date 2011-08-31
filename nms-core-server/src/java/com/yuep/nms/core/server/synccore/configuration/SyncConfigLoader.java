/*
 * $Id: SyncConfigLoader.java, 2009-3-9 ����11:24:12 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.configuration;

import java.util.List;

import com.yuep.nms.core.common.synccore.model.SyncNode;

/**
 * <p>
 * Title: SyncConfigLoader
 * </p>
 * <p>
 * Description: <br>
 * ͬ�����ü��ؽӿ�
 * </p>
 * 
 * @author yangtao
 * created 2009-3-9 ����11:24:12
 * modified [who date description]
 * check [who date description]
 */
public interface SyncConfigLoader {
  
    /**
     * ���ݹ���������ͻ�ȡͬ���ڵ�
     * @param type
     *        ���������ϸ����
     * @return
     *        ͬ���ڵ�
     */
    public List<SyncNode> getSyncNodes(String type);

    /**
     * ���ݹ��������ϸ�����Լ�ͬ���ڵ����ͻ�ȡͬ���ڵ�
     * 
     * @param type
     * @param syncNodeTypes
     * @return 
     *          ͬ���ڵ�
     */
    public List<SyncNode> getSyncNodesByNodeType(String type, String... syncNodeTypes);

    /**
     * ����ͬ��ҵ��ڵ㣬�����뵱ǰҵ��ڵ���������ڵ�,����ͬ���Ⱥ��������
     * 
     * @return
     *       ͬ���ڵ�
     */
    public List<SyncNode> getSyncNodesByNodeName(String type, String... syncNodes);

}
