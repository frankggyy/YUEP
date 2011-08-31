/*
 * $Id: FtpUtilsTest.java, 2010-11-12 ����12:04:13 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import java.io.File;

import junit.framework.TestCase;

import com.yuep.base.util.net.ftp.FtpUtils;

/**
 * <p>
 * Title: FtpUtilsTest
 * </p>
 * <p>
 * Description: ftp������Ĳ�������
 * </p>
 * 
 * @author sufeng
 * created 2010-11-12 ����12:04:13
 * modified [who date description]
 * check [who date description]
 */
public class FtpUtilsTest extends TestCase{
    
    /**
     * ����������ļ�·��
     */
    public void testParseFtpFileAndDir(){
        String[] res1=FtpUtils.parseFtpFileAndDir(null);
        assertEquals(res1[0], "/");
        assertEquals(res1[1], null);
        
        String[] res2=FtpUtils.parseFtpFileAndDir("");
        assertEquals(res2[0], "/");
        assertEquals(res2[1], null);
        
        String[] res3=FtpUtils.parseFtpFileAndDir("/");
        assertEquals(res3[0], "/");
        assertEquals(res3[1], null);
        
        String[] res4=FtpUtils.parseFtpFileAndDir("/aaa/");
        assertEquals(res4[0], "/aaa/");
        assertEquals(res4[1], "");
        
        String[] res5=FtpUtils.parseFtpFileAndDir("/a.test");
        assertEquals(res5[0], "/");
        assertEquals(res5[1], "a.test");
        
        String[] res6=FtpUtils.parseFtpFileAndDir("/dir/b.test");
        assertEquals(res6[0], "/dir/");
        assertEquals(res6[1], "b.test");
    }
    
    /**
     * ��ftp�������ļ�
     */
    public void stestDownFile(){
        String destDisk="d:";
        FtpUtils.downFile("127.0.0.1","1", "1", "/V400R001B012.bin", destDisk+"");
        File file=new File(destDisk+"/V400R001B012.bin");
        assertEquals(true,file.exists());
    }
    
    /**
     * ��ftp�������ļ�
     */
    public void testDownFile(){
        //FtpUtils.downFile("127.0.0.1","1", "1", "/V400R001B012.bin", "d:");
        //File file=new File("d:/V400R001B012.bin");
        System.out.println("ignore,��ο�stestDownFile���������ftp���������ٽ��в���");
        assertEquals(true,true);
    }
    
}
