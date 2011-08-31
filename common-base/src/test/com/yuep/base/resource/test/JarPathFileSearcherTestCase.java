/*
 * $Id: JarPathFileSearcherTestCase.java, 2011-1-7 ÏÂÎç03:42:40 Owner Exp $
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

import com.yuep.base.resource.JarPathFileSearcher;
import com.yuep.base.resource.JarPathFileSearcherImpl;

/**
 * <p>
 * Title: JarPathFileSearcherTestCase
 * </p>
 * <p>
 * Description:
 * ²âÊÔJarPathFileSearcherImpl
 * </p>
 * 
 * @author yangtao
 * created 2011-1-7 ÏÂÎç03:42:40
 * modified [who date description]
 * check [who date description]
 */
public class JarPathFileSearcherTestCase extends TestCase {
    
    private JarPathFileSearcher jarPathFileSearcher;
   
    @Override
    public void setUp(){
        jarPathFileSearcher=new JarPathFileSearcherImpl();
    }
    
    @Override
    public void tearDown(){
        
    }
    
    
    public void testSearch(){
      String path=this.getClass().getResource("").getPath();
      System.out.println(path);
      List<String> resources=jarPathFileSearcher.search(path+"searchjar.jar", "*readme.txt"); 
      assertTrue(resources.size()==1);
    }

}
