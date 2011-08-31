/*
 * $Id: ClassPathXMLFileReaderTestCase.java, 2010-11-11 ����03:41:12 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource.test;

import java.util.List;

import junit.framework.TestCase;

import com.yuep.base.resource.ClassPathXMLFileReader;
import com.yuep.base.resource.ClassPathXMLFileReaderImpl;

/**
 * <p>
 * Title: ClassPathXMLFileReaderTestCase
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 ����03:41:12
 * modified [who date description]
 * check [who date description]
 */
public class ClassPathXMLFileReaderTestCase extends TestCase {
    
    public final static String TEST_FILE_NAME_MATCHPATTERN="**/*test_data.xml";
    public final static String TEST_FILE_MAPPING="./com/yuep/base/resource/test/test_data_mapping.xml";
    public final static String TEST_FILE_DATA="./com/yuep/base/resource/test/test_data.xml";
    private ClassPathXMLFileReader classPathXMLFileReader;
   
    @Override
    public void setUp(){
        setUpClassPathXMLFileReader();
    }
    
    private void setUpClassPathXMLFileReader(){
        classPathXMLFileReader=new ClassPathXMLFileReaderImpl();
    }
    
    @Override
    public void tearDown(){
        
    }
    
    /**
     * ����������·���з����ļ����ƹ涨��XML�����ļ�������ȡ��Щ��������XML�ļ�����
     */
    public void testSearchAndRead(){
      List<TestObject> testObjects=classPathXMLFileReader.searchAndRead(TEST_FILE_NAME_MATCHPATTERN, TEST_FILE_MAPPING, TestObject.class);
      assertNotNull(testObjects);
    }

    /**
     * ���Զ�ȡָ��XML�����ļ�
     */
    public void testRead(){
        List<TestObject> testObjects=classPathXMLFileReader.searchAndRead(TEST_FILE_DATA, TEST_FILE_MAPPING, TestObject.class);
        assertNotNull(testObjects);
    }
}
