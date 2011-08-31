package com.yuep.nms.core.common.sbi.service;

import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.model.SbiInitialParameter;


/**
 * <p>
 * Title: SbiSysMgr
 * </p>
 * <p>
 * SBI��Ԫ����ӿ�
 * </p>
 * 
 * @author pl
 * created 2011-4-16 ����04:20:40
 * modified [who date description]
 * check [who date description]
 */
public interface SbiSysMgr {
    /**
     * �ṩ��Server���ã�����Ƿ�pingͨ
     */
    public void ping();

    /**
     * �ر�sbi��ֱ�ӽ����˳�
     */
    public void shutdownSbi();

    /**
     * ��ʼ��SBI����
     * @param parameter
     */
    public void initParameters(SbiInitialParameter parameter);

    /**
     * �����Ԫ
     * @param ne
     */
    public void addNe(NeLoginParameter ne);

    /**
     * ɾ����Ԫ
     * @param ne
     */
    public void deleteNe(NeLoginParameter ne);

    /**
     * ������ԪͨѶ����
     * @param ne
     */
    public void updateNeLoginParam(NeLoginParameter ne);

}
