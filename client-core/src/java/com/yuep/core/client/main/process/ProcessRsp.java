/*
 * $Id: ProcessRsps.java, 2010-9-28 ����07:25:47 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process;

/**
 * <p>
 * Title: ProcessRsp
 * </p>
 * <p>
 * Description:���̴����XML Dao���󣬶�Ӧ��XML�����ļ�process-mapping.xml
 * </p>
 * 
 * @author aaron
 * created 2010-9-28 ����07:44:31
 * modified [who date description]
 * check [who date description]
 */
public class ProcessRsp {
	
    /**
     * �������
     */
	private int step;
	
	/**
	 * ������
	 */
	private String rspClazz;

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getRspClazz() {
		return rspClazz;
	}

	public void setRspClazz(String rspClazz) {
		this.rspClazz = rspClazz;
	}

}
