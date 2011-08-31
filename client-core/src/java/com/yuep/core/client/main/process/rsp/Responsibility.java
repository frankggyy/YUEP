/*
 * $Id: Responsibility.java, 2010-4-27 ����03:07:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process.rsp;

/**
 * <p>
 * Title: Responsibility
 * </p>
 * <p>
 * Description:����ӿڣ���Ҫ�ǽ�����ͬ�µĹ������ֿ���Ϊ�Ժ����չ�Կ��ǣ��ɲο�start-process.xml
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 ����03:08:52
 * modified [who date description]
 * check [who date description]
 */
public interface Responsibility<T> {
    /**
     * ִ�й��ܣ�����
     * 
     * @param t
     *            T ��������
     * @return ����ִ���Ƿ�ɹ�
     */
    public boolean execute(T t);

    /**
     * ����ִ��
     * 
     * @param t
     * @return null,˵��ִ�����쳣�ж�; not null����ִ���������˳��������Ǵ��м�ĳһ�������һ����������
     */
    public T process(T t);

    /**
     * ���ص�ǰ�������һ������
     * 
     * @return Responsibility ����
     */
    public Responsibility<T> getNext();

    /**
     * ������һ������
     * 
     * @param rsp
     *            Responsibility ����
     * @return Լ�����ڲ�ʵ�ַ��ص�Responsibility����������Ĳ�����������ͬ! �˷���ֵֻΪ������ϱ���
     */
    public Responsibility<T> setNext(Responsibility<T> rsp);
}
