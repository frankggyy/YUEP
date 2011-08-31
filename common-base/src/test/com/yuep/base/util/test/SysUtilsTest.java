/*
 * $Id: SysUtilsTest.java, 2010-12-2 ÏÂÎç04:29:02 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import java.util.Map;

import junit.framework.TestCase;

import com.yuep.base.util.sys.SysUtils;

/**
 * <p>
 * Title: SysUtilsTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-12-2 ÏÂÎç04:29:02
 * modified [who date description]
 * check [who date description]
 */
public class SysUtilsTest extends TestCase{
    
    public void testParseArg(){
        Map<String, String> map1 = SysUtils.parseArg(new String[]{"a","b", "c"});
        assertEquals(3, map1.size());
        assertEquals(null, map1.get("a"));
        assertEquals(null, map1.get("b"));
        Map<String, String> map2 = SysUtils.parseArg(new String[]{"daemon","-log", "c:\test.log"});
        assertEquals(2, map2.size());
        assertEquals(null, map2.get("daemon"));
        assertEquals("c:\test.log", map2.get("log"));
        Map<String, String> map3 = SysUtils.parseArg(new String[]{"daemon","-log", "c:\test.log","-Level","5"});
        assertEquals(3, map3.size());
        assertEquals("5", map3.get("Level"));
    }

}
