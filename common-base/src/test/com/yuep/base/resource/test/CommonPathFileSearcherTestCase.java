/*
 * $Id: CommonPathFileSearcherTestCase.java, 2011-1-7 ÏÂÎç04:00:02 Owner Exp $
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

import com.yuep.base.resource.CommonPathFileSearcher;
import com.yuep.base.resource.CommonPathFileSearcherImpl;

/**
 * <p>
 * Title: CommonPathFileSearcherTestCase
 * </p>
 * <p>
 * Description:
 * ²âÊÔCommonPathFileSearcherImpl
 * </p>
 * 
 * @author yangtao
 * created 2011-1-7 ÏÂÎç04:00:02
 * modified [who date description]
 * check [who date description]
 */
public class CommonPathFileSearcherTestCase extends TestCase{
    
    private CommonPathFileSearcher commonPathFileSearcher;
    @Override
    public void setUp(){
        commonPathFileSearcher=new CommonPathFileSearcherImpl();
    }
    
    @Override
    public void tearDown(){
        
    }
    
    
    public void testSearch(){
        String path=this.getClass().getResource("").getPath();
        List<String> resources=commonPathFileSearcher.search(path, "**/"+"*"+CommonPathFileSearcherTestCase.class.getSimpleName()+"*");
        assertTrue(resources.size()==1);
    }
    
    
    public void testSearchWithDirectory(){
        String path=this.getClass().getResource("").getPath();
        List<String> resources=commonPathFileSearcher.search(path, "**/"+"aa/"+"*test.txt");
        assertTrue(resources.size()==1);
    }

}
