/*
 * $Id: XmlFileWriterTest.java, 2010-11-11 ����08:16:31 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import junit.framework.TestCase;

import com.yuep.base.xml.XmlFileWriter;

/**
 * <p>
 * Title: XmlFileWriterTest
 * </p>
 * <p>
 * Description:
 * дXML�ļ�XmlFileWriter�൥Ԫ����
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 ����08:16:31
 * modified [who date description]
 * check [who date description]
 */
public class XmlFileWriterTest extends TestCase {
    
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
    
   /**
    * ���Ը���data��mapping�ļ�·������д�ļ�
    * @throws Exception
    */
   public void testWriteFileByDataPathAndMappingPath() throws Exception{
 
       TestStudent student=new TestStudent();
       student.setName("aaaa");
       student.setOld(30);
       
       URL url=this.getClass().getResource("");//��ȡ��ǰ�����ڵ�·��
       String path=url.getPath();
       
       boolean result=XmlFileWriter.writeFile(path+"test_student-mapping.xml", path+"Student.xml", student);
       assertTrue(result);
   }
   
   /**
    * ���Ը���Mapping�ļ�InputStream��data�ļ�·������д�ļ�
    * @throws Exception
    */
   public void testWriteFileByMappingFileInputStream() throws Exception{
       
       TestStudent student=new TestStudent();
       student.setName("aaaa");
       student.setOld(30);
       
       URL url=this.getClass().getResource("");//��ȡ��ǰ�����ڵ�·��
       String path=url.getPath();
       
       FileInputStream mappingOutputStreaming = new FileInputStream(path+"test_student-mapping.xml");
       boolean result=XmlFileWriter.writeFile(mappingOutputStreaming, "Student.xml", student);
       assertTrue(result);
   }
   
   /**
    * ���Ը���Mapping�ļ�InputStream��Data�ļ�OutputStream����д�ļ�
    * @throws Exception
    */
   public void testWriteFileByStream() throws Exception{
       TestStudent student=new TestStudent();
       student.setName("aaaa");
       student.setOld(30);
       
       URL url=this.getClass().getResource("");//��ȡ��ǰ�����ڵ�·��
       String path=url.getPath();
       
       FileInputStream mappingOutputStreaming =null;
       try{
           mappingOutputStreaming=new FileInputStream(path+"test_student-mapping.xml");
       
           FileOutputStream outputStream=new FileOutputStream(path+"student.xml");
       
           boolean result=XmlFileWriter.writeFile(mappingOutputStreaming, outputStream, student);
           assertTrue(result);
       }finally{
           if(mappingOutputStreaming!=null){
               mappingOutputStreaming.close();
           }
       }
       
   }
   
    
    

}
