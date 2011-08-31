/*
 * $Id: OtherFormatterTest.java, 2010-11-15 上午11:40:53 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.base.util.format.YuepStringUtils;

/**
 * <p>
 * Title: OtherFormatterTest
 * </p>
 * <p>
 * Description: 杂项工具类的测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:40:53
 * modified [who date description]
 * check [who date description]
 */
public class OtherFormatterTest extends TestCase{

    public void testLong2String(){
        String str = YuepStringUtils.toString(1L, 4);
        assertEquals("0001", str);
        
        str = YuepStringUtils.toString(12L, 4);
        assertEquals("0012", str);
        
        str = YuepStringUtils.toString(123L, 4);
        assertEquals("0123", str);
        
        str = YuepStringUtils.toString(1234L, 4);
        assertEquals("1234", str);
    }
    
    public void testCapitalize(){
        String cap1 = YuepStringUtils.capitalize("abce");
        assertEquals("Abce", cap1);
        
        cap1 = YuepStringUtils.capitalize("AAA");
        assertEquals("AAA", cap1);
    }
    
    public void testConvertList2String(){
        List<String> list=new ArrayList<String>();
        list.add("name=yuep");
        list.add("version=1");
        list.add("time=2010");
        String str = YuepStringUtils.convertList2String(list, ";");
        assertEquals("name=yuep;version=1;time=2010", str);
    }
    
    public void testDecodeAndEncode(){
        String plainText="1234567890abcdefghijklmn";
        String encryText = EncryptUtils.setEncrypt(plainText, "ikj09az");
        String text = EncryptUtils.getEncrypt(encryText, "ikj09az");
        assertEquals(plainText, text);
    }
    
    public void testCollectionEquals(){
        List<String> list1=new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        
        Set<String> set1=new HashSet<String>();
        set1.add("2");
        set1.add("1");
        boolean res = YuepObjectUtils.collectionEquals(list1, set1);
        assertEquals(true, res);
    }
    
    public void testByteArray2Int(){
        int intValue=YuepObjectUtils.byteArray2Int(new byte[]{0x1f,0x2e,0x3,0x0} );
        assertEquals(0x1f2e0300, intValue);
    }
    
    public void testSplit2MultiSubList(){
        List<String> list1=new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        List<List<String>> split2MultiSubList = YuepObjectUtils.split2MultiSubList(list1, 2);
        assertEquals(3, split2MultiSubList.size());
        assertEquals("1", split2MultiSubList.get(0).get(0));
        assertEquals("2", split2MultiSubList.get(0).get(1));
        assertEquals("3", split2MultiSubList.get(1).get(0));
        assertEquals("4", split2MultiSubList.get(1).get(1));
        assertEquals("5", split2MultiSubList.get(2).get(0));
    }
    
}
