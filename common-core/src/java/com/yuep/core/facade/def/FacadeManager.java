/*
 * $Id: FacadeManager.java, 2011-1-30 ����04:30:27 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.facade.def;

import java.util.List;


/**
 * <p>
 * Title: FacadeManager
 * </p>
 * <p>
 * Description:����facade
 * </p>
 * 
 * @author sufeng
 * created 2011-1-30 ����04:30:27
 * modified [who date description]
 * check [who date description]
 */
public interface FacadeManager {

    /**
     * �õ�before��������д�����
     * @return
     */
    public List<FacadeProcessor> getFacadeProcessorByBeforeOrder();
    
    /**
     * �õ�after��������д�����
     * @return
     */
    public List<FacadeProcessor> getFacadeProcessorByAfterOrder();
    
    /**
     * �õ����е�facade����������
     * @return
     */
    public List<String> getAllFacadeProcessorNames();
    
    /**
     * ��facade��������ְ���������
     * @param processor facade������
     */
    public void appendFacadeProcessor(FacadeProcessor processor);
    
    /**
     * ���һ��facade��������ְ������ָ��λ��
     * @param previousProcessorName  ��һ�����������ƣ�null��ʾ����ְ��������ǰ��
     * @param processor              facade������
     */
    public void insertFacadeProcessor(String previousProcessorName,FacadeProcessor processor);
    
    /**
     * ɾ��һ��facade������
     * @param processorName
     */
    public void removeFacadeProcessor(String processorName);
    
}
