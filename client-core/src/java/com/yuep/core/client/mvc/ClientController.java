/*
 * $Id: ClientController.java, 2009-2-24 ����09:29:33 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.awt.Window;
import java.util.List;

/**
 * <p>
 * Title: ClientController
 * </p>
 * <p>
 * Description: �ͻ���MVCģʽ�Ŀ����ࣨcontroller���Ľӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-24 ����09:29:33
 * modified [who date description]
 * check [who date description]
 */
public interface ClientController<T, V, M> {
    /**
     * ��ʼ�������model,���ݲ�Ӧ���ڴ˷������˷���һ������Ӧ��ֻ����һ�Ρ�
     * @param viewParams �����ʼ������
     */
    void initView(Object... viewParams);

    /**
     * ��ʼ�����ݵķ��������ȳ�ʼ����Model�е����ݣ�����model�����ݸ��µ�������ʾ�ؼ�
     * @param objects ��ʼ���Ĳ���
     * @return ��ʼ���Ƿ�ɹ����ɹ�����<code>true</code>�����򷵻�<code>false</code>
     */
    boolean initData(Object... objects);

    /**
     * ������ݵ�Model�����½��棬����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ��ӵ������б�
     */
    void addDatas(List<T> datas);
    
    /**
     * �޸����ݵ�Model�����½��棬����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ�޸ĵ������б�
     */
    void modifyDatas(List<T> datas);
    
    /**
     * ɾ�����ݵ�Model�����½��棬����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫɾ���������б�
     */
    void deleteDatas(List<T> datas);

    /**
     * ��ȡ�����ϵ�����
     * @return ��ȡModel�е�����������Ϣ
     */
    List<T> getDatas();
    
    /**
     * �ռ��������ݣ������������ύ��Model
     * 
     */
    void collectData();
    
    /**
     * �رս��沢���Model�е����ݻ���
     */
    void dispose();
    
    /**
     * ��ȡ��ǰ����
     * @return <code>Window</code> ��ǰ����
     */
    Window getWindow();
    
    /**
     * ���Model���������
     */
    void clearData();
    
    /**
     * ��ȡ�ͻ���ClientView
     * @return V �ͻ��˽���
     */
    V getClientView();
}
