/*
 * $Id: ClientModel.java, 2009-2-24 ����09:29:03 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.util.List;

/**
 * <p>
 * Title: ClientModel
 * </p>
 * <p>
 * Description:�ͻ���MVCģʽ������ģ���ࣨmodel���Ľӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-24 ����09:29:03
 * modified [who date description]
 * check [who date description]
 */
public interface ClientModel<T> {
    /**
     * ��ʼ��Model�е����ݣ���ʼ���ɹ���controller���ȡ��ʼ�������ݣ�Ȼ�����ݸ��µ�����
     * @param objects ��ʼ���Ĳ���
     */
    void init(Object... objects);

    /**
     * ������ݵ�Model������������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ��ӵ������б�
     */
    void addDatas(List<T> datas);
    
    /**
     * �޸����ݵ�Model������������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ�޸ĵ������б�
     */
    void modifyDatas(List<T> datas);
    
    /**
     * ɾ�����ݵ�Model������������Ͷ���������ʹ�ø÷���
     * @param datas Ҫɾ���������б�
     */
    void deleteDatas(List<T> datas);

    /**
     * ��ȡModel�е�����������Ϣ
     * @return List<T> ����������Ϣ
     */
    List<T> getModelDatas();
    
    /**
     * ���Model�еĻ������ݣ��ڹرմ����ǵ���
     */
    void clearData();
}
