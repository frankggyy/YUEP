/*
 * $Id: XmlParseUtilTest.java, 2010-11-12 ÉÏÎç11:27:12 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.jdom.Element;

import com.yuep.base.xml.XmlParseUtil;

/**
 * <p>
 * Title: XmlParseUtilTest
 * </p>
 * <p>
 * Description:
 * <code>XmlParseUtil</code>
 * </p>
 * 
 * @author yangtao
 * created 2010-11-12 ÉÏÎç11:27:12
 * modified [who date description]
 * check [who date description]
 */
public class XmlParseUtilTest extends TestCase {

    private XmlParseUtil xmlParseUtil;
    
    @Override
    public void setUp(){
    }
    
    @Override
    public void tearDown(){
        
    }
    
   
    public void testGetDocumentElementsByFileURL(){
        URL url=this.getClass().getResource("test_student-data.xml");
        xmlParseUtil=new XmlParseUtil(url);
        Element element=xmlParseUtil.getDocumentElement();
        assertNotNull(element);
        xmlParseUtil.close();
    }
    
    
    public void testGetDocumentElementsByInputStream() throws IOException{
        URL url=this.getClass().getResource("test_student-data.xml");
        InputStream fileInputStream=url.openStream();
        xmlParseUtil=new XmlParseUtil(fileInputStream);
        Element element=xmlParseUtil.getDocumentElement();
        assertNotNull(element);
        xmlParseUtil.close();
    }
    
}
