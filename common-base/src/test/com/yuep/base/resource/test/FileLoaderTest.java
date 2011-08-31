/*
 * $Id: FileLoaderTest.java, 2010-11-15 ÏÂÎç12:20:13 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource.test;

import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import com.yuep.base.resource.FileLoader;

/**
 * <p>
 * Title: FileLoaderTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2010-11-15 ÏÂÎç12:20:13
 * modified [who date description]
 * check [who date description]
 */
public class FileLoaderTest extends TestCase{
    
    
    @Override
    public void setUp(){
    }
    
    @Override
    public void tearDown(){
    }
    
    public void testGetInputStream(){
        String path=this.getClass().getResource("")+"test_data.xml";
        InputStream inputStream=FileLoader.getInputStream(path);
        assertTrue(inputStream!=null);
    }

    public void testGetUrl(){
        String path=this.getClass().getResource("")+"test_data.xml";
        URL url=FileLoader.getUrl(path);
        assertTrue(url!=null);
    }

    public void testGetAbsoluteFilePath(){
        String fullPath=FileLoader.getAbsoluteFilePath("readme.txt");
        assertNotNull(fullPath);
    }

}
