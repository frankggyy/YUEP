/*
 * $Id: XmlFileReaderTest.java, 2010-11-11 下午07:31:19 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml.test;

import java.io.InputStream;

import junit.framework.TestCase;

import com.yuep.base.resource.FileLoader;
import com.yuep.base.xml.ArrayListItems;
import com.yuep.base.xml.XmlFileReader;

/**
 * <p>
 * Title: XmlFileReaderTest
 * </p>
 * <p>
 * Description:
 * XML文件读取XmlFileReader类单元测试用例
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 下午07:31:19
 * modified [who date description]
 * check [who date description]
 */
public class XmlFileReaderTest extends TestCase {
    
    public final static String TEST_FILE_MAPPING="./com/yuep/base/xml/test/test_student-mapping.xml";
    public final static String TEST_FILE_DATA="./com/yuep/base/xml/test/test_student-data.xml";
    
 
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
       
    }
    /**
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        
    }
    
    

    
  
    @SuppressWarnings("unchecked")
    public void testGetXmlConfigByPath(){
       Object value= XmlFileReader.getXmlConfig(TEST_FILE_DATA, TEST_FILE_MAPPING);
       ArrayListItems arrayListItems=(ArrayListItems)value;
       assertTrue(arrayListItems.getItems().size()!=0); 
    }
    
    
    @SuppressWarnings("unchecked")
    public void testGetXmlConfigByDataFullName(){
        Object value=XmlFileReader.getXmlConfigByDataFullName("./com/yuep/base/xml/test/", "test_student", TEST_FILE_DATA);
        ArrayListItems arrayListItems=(ArrayListItems)value;
        assertTrue(arrayListItems.getItems().size()!=0); 
    }
    
    @SuppressWarnings("unchecked")
    public void testGetXmlConfigByDataPathAndMappingPath(){
        Object value=XmlFileReader.getXmlConfig("./com/yuep/base/xml/test/","./com/yuep/base/xml/test/", "test_student");
        ArrayListItems arrayListItems=(ArrayListItems)value;
        assertTrue(arrayListItems.getItems().size()!=0); 
    }
    
    @SuppressWarnings("unchecked")
    public void testGetXmlConfigByDataInputStreamAndFullMappingFilePaths(){
        InputStream inputStream=FileLoader.getInputStream(TEST_FILE_DATA);
        Object value=XmlFileReader.getXmlConfig(inputStream, TEST_FILE_MAPPING);
        ArrayListItems arrayListItems=(ArrayListItems)value;
        assertTrue(arrayListItems.getItems().size()!=0); 
    }
    
    
    @SuppressWarnings("unchecked")
    public void testGetXmlConfigByInputStream(){
        InputStream dataInputStream=FileLoader.getInputStream(TEST_FILE_DATA);
        InputStream mappingInputStream=FileLoader.getInputStream(TEST_FILE_MAPPING);
        Object value=XmlFileReader.getXmlConfig(mappingInputStream, dataInputStream);
        ArrayListItems arrayListItems=(ArrayListItems)value;
        assertTrue(arrayListItems.getItems().size()!=0); 
    }
    
    
    

}
