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

import java.util.List;

/**
 * <p>
 * Title: ProcessRsps
 * </p>
 * <p>
 * Description: ���̴����XML Dao���󣬶�Ӧ��XML�����ļ�process-mapping.xml
 * </p>
 * 
 * @author aaron
 * created 2010-9-28 ����07:45:08
 * modified [who date description]
 * check [who date description]
 */
public class ProcessRsps {
    
    /**
     * �����Ķ��ProcessRsp
     */
	private List<ProcessRsp> rsps;

	public List<ProcessRsp> getRsps() {
		return rsps;
	}

	public void setRsps(List<ProcessRsp> rsps) {
		this.rsps = rsps;
	}
}
