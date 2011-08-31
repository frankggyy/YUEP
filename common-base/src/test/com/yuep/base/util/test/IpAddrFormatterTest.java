/*
 * $Id: IpAddrFormatterTest.java, 2010-11-15 上午11:36:19 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.yuep.base.util.format.IpAddrFormatter;

/**
 * <p>
 * Title: IpAddrFormatterTest
 * </p>
 * <p>
 * Description: ip地址转换的测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:36:19
 * modified [who date description]
 * check [who date description]
 */
public class IpAddrFormatterTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
    }
    
    @Override
    protected void tearDown() throws Exception {
    }
    
    public void testStr2Long(){
        String ip="192.168.0.110";
        long  ipLong= IpAddrFormatter.textToLongFormat(ip);
        String ip2=IpAddrFormatter.longToTextFormat(ipLong);
        assertEquals(ip2, ip);
    }
    
    public void testGetNetAddr(){
        String ip="192.168.1.110";
        String net1= IpAddrFormatter.getNetAddr(ip, "255.255.255.0");
        assertEquals("192.168.1.0", net1);
        
        String net2= IpAddrFormatter.getNetAddr(ip, "255.255.0.0");
        assertEquals("192.168.0.0", net2);
    }
    
    public void testGetBroadCast(){
        String ip="192.168.1.110";
        String broadAddr1= IpAddrFormatter.getNetBrdcstAddr(ip, "255.255.255.0");
        assertEquals("192.168.1.255", broadAddr1);
    }
    
    public void testGetLocalIp(){
        Map<String, String> map = IpAddrFormatter.getLocalIpAndMask();
        assertTrue(map.size()>0);
    }
    
    public void testGetIPsInNet(){
        List<String> ips = IpAddrFormatter.getIPsInNet("192.168.11.1", "255.255.255.0");
        assertEquals(255, ips.size());
        assertEquals("192.168.11.1", ips.get(0));
        assertEquals("192.168.11.255", ips.get(254));
    }
    
}
