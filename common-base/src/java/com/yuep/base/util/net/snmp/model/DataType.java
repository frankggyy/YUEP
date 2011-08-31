/*
 * $Id: DataType.java, 2010-11-12 上午11:40:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.net.snmp.model;

/**
 * <p>
 * Title: DataType
 * </p>
 * <p>
 * SNMP协议类型
 * </p>
 * 
 * @author pl
 * created 2010-11-16 上午11:30:46
 * modified [who date description]
 * check [who date description]
 */
public enum DataType {
    
    INTEGER, 
    STRING,
    MACADDRESS, 
    IPADDRESS,
    TIMETICKS_STRING_TIMEZONE,
    TIMETICKS_STRING,
    TIMETICKS_LONG,
    DATEANDTIME,
    COUNTER64,
    BOOLEAN,
    BYTE,
    BYTEARRAY;
    
}