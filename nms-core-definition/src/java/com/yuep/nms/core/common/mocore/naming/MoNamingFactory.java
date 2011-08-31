/*
 * $Id: MoNamingFactory.java, 2011-3-28 上午09:19:39 yangtao Exp $
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
 * <br>MoNaming创建工厂
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午09:19:39
 * modified [who date description]
 * check [who date description]
 */
public class MoNamingFactory {
    
   /**
    * 创建MoNaming
    * @param parent
    *        父节点MoNaming
    * @param moType
    *        mo类型
    * @param instance
    *        mo实例编号
    * @return
    *        MoNaming
    */
   public static MoNaming createMoNaming(MoNaming parent,String moType,int instance){
       return new MoNaming(parent,instance,moType);
   }
   
   /**
    * 创建MoNaming
    * @param parent
    *        父节点MoNaming
    * @param moType
    *        mo类型
    * @param instance
    *        mo实例编号
    * @param addition
    *        辅助信息
    * @return
    *        MoNaming
    */
   public static MoNaming createMoNaming(MoNaming parent,String moType,int instance,String addition){
       return new MoNaming(parent,instance,moType,addition);
   }
   
   /**
    * 将下级网管的MoNaming转为本级网管MoNaming
    * @param nm
    *        下级网管在本级网管的标识
    * @param remoteMoNaming
    *        下级网管的MoNaming
    * @return
    *        本级网管MoNaming
    */
   public static MoNaming toLocalMoNaming(MoNaming nm,MoNaming remoteMoNaming){
       if(remoteMoNaming==null)
           return null;
       //new MoNaming("nm=1/")：网管根对象标识，为常量
       MoNaming mo=MoNaming.split(new MoNaming("nm=1/"),remoteMoNaming);
       return MoNaming.append(nm, mo);
   }
   
   /**
    * 将本级网管的MoNaming转为下级网管的MoNaming
    * @param nm
    *        下级网管在本级网管的标识
    * @param localMoNaming
    *        本级网管MoNaming
    * @return
    *        下级网管MoNaming
    */
   public static MoNaming toRemoteMoNaming(MoNaming nm,MoNaming localMoNaming){
       MoNaming mo=MoNaming.split(nm, localMoNaming);
       //new MoNaming("nm=1/")：网管根对象标识，为常量
       return MoNaming.append(new MoNaming("nm=1/"), mo);
   }
   
}
