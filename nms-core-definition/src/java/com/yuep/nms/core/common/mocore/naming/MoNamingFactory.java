/*
 * $Id: MoNamingFactory.java, 2011-3-28 ����09:19:39 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.naming;

/**
 * <p>
 * Title: MoNamingFactory
 * </p>
 * <p>
 * Description:
 * <br>MoNaming��������
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����09:19:39
 * modified [who date description]
 * check [who date description]
 */
public class MoNamingFactory {
    
   /**
    * ����MoNaming
    * @param parent
    *        ���ڵ�MoNaming
    * @param moType
    *        mo����
    * @param instance
    *        moʵ�����
    * @return
    *        MoNaming
    */
   public static MoNaming createMoNaming(MoNaming parent,String moType,int instance){
       return new MoNaming(parent,instance,moType);
   }
   
   /**
    * ����MoNaming
    * @param parent
    *        ���ڵ�MoNaming
    * @param moType
    *        mo����
    * @param instance
    *        moʵ�����
    * @param addition
    *        ������Ϣ
    * @return
    *        MoNaming
    */
   public static MoNaming createMoNaming(MoNaming parent,String moType,int instance,String addition){
       return new MoNaming(parent,instance,moType,addition);
   }
   
   /**
    * ���¼����ܵ�MoNamingתΪ��������MoNaming
    * @param nm
    *        �¼������ڱ������ܵı�ʶ
    * @param remoteMoNaming
    *        �¼����ܵ�MoNaming
    * @return
    *        ��������MoNaming
    */
   public static MoNaming toLocalMoNaming(MoNaming nm,MoNaming remoteMoNaming){
       if(remoteMoNaming==null)
           return null;
       //new MoNaming("nm=1/")�����ܸ������ʶ��Ϊ����
       MoNaming mo=MoNaming.split(new MoNaming("nm=1/"),remoteMoNaming);
       return MoNaming.append(nm, mo);
   }
   
   /**
    * ���������ܵ�MoNamingתΪ�¼����ܵ�MoNaming
    * @param nm
    *        �¼������ڱ������ܵı�ʶ
    * @param localMoNaming
    *        ��������MoNaming
    * @return
    *        �¼�����MoNaming
    */
   public static MoNaming toRemoteMoNaming(MoNaming nm,MoNaming localMoNaming){
       MoNaming mo=MoNaming.split(nm, localMoNaming);
       //new MoNaming("nm=1/")�����ܸ������ʶ��Ϊ����
       return MoNaming.append(new MoNaming("nm=1/"), mo);
   }
   
}
