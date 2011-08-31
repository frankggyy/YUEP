/*
 * $Id: DefaultProcessor.java, 2010-9-28 ����07:25:47 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.Assert;

import com.yuep.base.xml.XmlFileReader;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.process.rsp.Responsibility;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: DefaultProcessor
 * </p>
 * <p>
 * Description: �����������̵�Ĭ��ʵ��
 * </p>
 * 
 * @author aaron
 * created 2010-9-28 ����07:44:41
 * modified [who date description]
 * check [who date description]
 * @param <T>
 */
public class DefaultProcessor<T> implements Processor<T> {
    /** ְ���� */
	private Responsibility<T> startRsp = null;

	/**
	 * ��ȡ�������������ļ�,���������뵽ְ������
	 * @param fileName
	 */
	public void interpreterProcessor(String fileName) {
		Object obj = null;
		try {
		    // ��ȡxml,castor��ʽ�����н���
			obj = XmlFileReader.getXmlConfig(ClientCoreContext.getResourceFactory()
					.getConfFile("process-mapping.xml").openStream(),
					ClientCoreContext.getResourceFactory().getConfFile(
							fileName).openStream());
			Assert.notNull(obj);
			ProcessRsps rsps = (ProcessRsps) obj;
			List<ProcessRsp> processRsps = rsps.getRsps();
			
			// ����
			Collections.sort(processRsps, new Comparator<ProcessRsp>() {

				@Override
				public int compare(ProcessRsp arg0, ProcessRsp arg1) {
					int step = arg0.getStep();
					int step2 = arg1.getStep();
					if (step > step2) {
						return 1;
					} else {
						return -1;
					}
				}

			});
			
			// ����ְ������
			Responsibility<T> currentResponsibility = null;
			for (int i = 0; i < processRsps.size(); i++) {
				ProcessRsp processRsp = processRsps.get(i);
				Responsibility<T> responsibility = getResponsibility(processRsp);
				if (i == 0)
					startRsp = responsibility;
				else
					currentResponsibility.setNext(responsibility);
				currentResponsibility = responsibility;
			}
		} catch (Exception e) {
			DialogUtils.showErrorExpandDialog(null, e);
		}
	}

	/**
	 * ��ȡְ�����е����̲���ʵ��
	 * @param processRsp
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Responsibility<T> getResponsibility(ProcessRsp processRsp)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String rspClazz = processRsp.getRspClazz();
		Class<?> forName = Class.forName(rspClazz);//����
		Responsibility<T> newInstance = (Responsibility<T>) forName.newInstance();
		return newInstance;
	}

	/**
	 * (non-Javadoc)
	 * @see com.yuep.core.client.main.process.Processor#process(java.lang.Object)
	 */
	@Override
	public void process(T t) {
	    //ְ���������Ĵ���
		startRsp.process(t);
	}
}
