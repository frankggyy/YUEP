/*
 * $Id: ClientBatchResult.java, 2009-12-18 下午01:30:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;


/**
 * <p>
 * Title: ClientBatchResult
 * </p>
 * <p>
 * Description:客户端批量处理结果
 * </p>
 * 
 * @author yangtao
 * created 2009-12-18 下午01:30:31
 * modified [who date description]
 * check [who date description]
 */
public class ClientBatchResult{
    /**
     * 
     */
    private static final long serialVersionUID = 2664328004479393810L;

    private String result;
    private String cause;
    private String solution;



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
