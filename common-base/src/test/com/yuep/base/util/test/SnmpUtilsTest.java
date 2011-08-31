/*
 * $Id: SnmpUtilsTest.java, 2010-11-15 上午11:35:11 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;

import com.adventnet.snmp.snmp2.SnmpInt;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.yuep.base.util.net.snmp.SnmpContentUtil;
import com.yuep.base.util.net.snmp.SnmpOidUtil;

/**
 * <p>
 * Title: SnmpUtilsTest
 * </p>
 * <p>
 * Description: snmp工具类测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:35:11
 * modified [who date description]
 * check [who date description]
 */
public class SnmpUtilsTest extends TestCase {
    public void testGetIndexes() {
        String mibIndex = SnmpOidUtil.getIndexes(1, 2, 3);
        assertEquals(".1.2.3", mibIndex);
    }

    public void testGetIndexByOid() {
        int[] indexs = SnmpOidUtil.getIndexByOid(".1.3.6.1.2.3", 2);
        assertEquals(true, ArrayUtils.isEquals(new int[] { 2, 3 }, indexs));
    }

    public void testIsMibNodeByOid() {
        boolean isNode1 = SnmpOidUtil.isMibNodeByOid(".1.3.6.1.2.3", ".1.3.6.1.2");
        boolean isNode2 = SnmpOidUtil.isMibNodeByOid(".1.3.6.1.2.3", ".1.3.6.1.2.");
        assertEquals(true, isNode1);
        assertEquals(false, isNode2);
    }
    
    public void testBool2snmpvar(){
        SnmpVar trueVar = SnmpContentUtil.bool2snmpvar(true);
        SnmpVar falseVar = SnmpContentUtil.bool2snmpvar(false);
        assertEquals(new SnmpInt(1), trueVar);
        assertEquals(new SnmpInt(0), falseVar);
    }
}
