/*
 * $Id: ClassPathFileSearcherTestCase.java, 2010-11-10 上午10:51:36 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource.test;

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import com.yuep.base.resource.ClassPathFileSearcher;
import com.yuep.base.resource.ClassPathFileSearcherImpl;

/**
 * <p>
 * Title: ClassPathFileSearcherTestCase
 * </p>
 * <p>
 * Description:
 * <br>ClassPathFileSearcherImpl<br>单元测试
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 上午10:51:36
 * modified [who date description]
 * check [who date description]
 */
public class ClassPathFileSearcherTestCase extends TestCase {
    
    public final static String TEST_FILE_NAME_MATCHPATTERN="**/*test_data.xml";
    
    private ClassPathFileSearcher classPathFileSearcher;
    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
        setUpClassPathFileSearcher();
    }
    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        
    }
    
    private void setUpClassPathFileSearcher(){
        classPathFileSearcher=new ClassPathFileSearcherImpl();
    }
    
    /**
     * 测试符合文件名称规则的查询，查询类路径中查找符合匹配规则的文件
     */
    public void testSearch(){
        List<String> files=classPathFileSearcher.search(TEST_FILE_NAME_MATCHPATTERN);
        assertNotNull(files);
    }
    
    /**
     * 测试查询符合文件名称规则的资源查询
     */
    public void testSearchResource(){
        List<URL> resources=classPathFileSearcher.searchResource(TEST_FILE_NAME_MATCHPATTERN);
        assertNotNull(resources);
    }
    

}
