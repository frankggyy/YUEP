/*
 * $Id: MacAddrFormatterTest.java, 2010-11-15 上午11:36:19 sufeng Exp $
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

import com.yuep.base.util.format.MacAddrFormatter;

/**
 * <p>
 * Title: MacAddrFormatterTest
 * </p>
 * <p>
 * Description:mac地址格式转换的测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:36:19
 * modified [who date description]
 * check [who date description]
 */
public class MacAddrFormatterTest extends TestCase {

    public void testFormatMacAddr(){
        String newMac1 = MacAddrFormatter.formatMacAddr("01-02-03-04-05-06");
        assertEquals("010203040506", newMac1);
        
        String newMac2 = MacAddrFormatter.formatMacAddr("01:02:03:04:05:06");
        assertEquals("010203040506", newMac2);
        
        String newMac3 = MacAddrFormatter.formatMacAddr("010203040506");
        assertEquals("010203040506", newMac3);
    }
    
    public void testFormatMacAddr22ByteArray(){
        byte[] macs=MacAddrFormatter.formatMacAddr2ByteArray("f1:02:03:04:05:06");
        assertEquals(-15, macs[0]);
        assertEquals(2, macs[1]);
        assertEquals(3, macs[2]);
        assertEquals(4, macs[3]);
        assertEquals(5, macs[4]);
        assertEquals(6, macs[5]);
    }
    
    public void testMacAddr2LinuxFormat(){
        String linuxMac = MacAddrFormatter.macAddr2LinuxFormat("a1-b2-03-04-05-f6");
        assertEquals("a1:b2:03:04:05:f6", linuxMac);
    }
    
}
