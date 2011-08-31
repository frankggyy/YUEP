/*
 * $Id: ObjectIDDaoImpl.java, 2010-7-13 下午01:47:22 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.id.def.ObjectID;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;

/**
 * <p>
 * Title: ObjectIDDaoImpl
 * </p>
 * <p>
 * Description:
 * ObjectIDDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2010-7-13 下午01:47:22
 * modified [who date description]
 * check [who date description]
 */
public class ObjectIDDaoImpl implements ObjectIDDao {

    private ConcurrentMap<String,AtomicLong> cache=new ConcurrentHashMap<String,AtomicLong>();
    private SessionFactory sessionFactory;
    
    
    public ObjectIDDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    
    public void init(){
        Session session=sessionFactory.openSession();
        HibernateTemplate<ObjectID> hibernateTemplate=new HibernateTemplateImpl<ObjectID>(ObjectID.class,session);
      try{
          List<ObjectID> objectStorageIDs=hibernateTemplate.getAllEntities();
          for(ObjectID objectStorageID:objectStorageIDs){
              cache.put(objectStorageID.getObjectType(), new AtomicLong(objectStorageID.getObjectId()));
          }
        }finally{
          session.close();
        }
    }
    
    public void destory(){
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.id.impl.ObjectIDDao#getObjectId(java.lang.String)
     */
    @Override
    public Long getObjectId(String objectType) {
        if(!cache.containsKey(objectType))
             cache.put(objectType, new AtomicLong(0));
        AtomicLong idValue= cache.get(objectType);
        Long id=idValue.incrementAndGet();
        updateObjectStorageID(objectType,id);
        return id;
    }
    
    
    private void updateObjectStorageID(String objectType,Long idValue){
        Session session=sessionFactory.openSession();
        HibernateTemplate<ObjectID> hibernateTemplate=new HibernateTemplateImpl<ObjectID>(ObjectID.class,session);
      try{
          ObjectID objectStorageID=hibernateTemplate.getUniqueEntityByOneProperty("objectType", objectType);
          if(objectStorageID!=null){
              objectStorageID.setObjectId(idValue);
              hibernateTemplate.updateBatch(objectStorageID);
          }else{
              objectStorageID=new ObjectID();
              objectStorageID.setObjectId(idValue);
              objectStorageID.setObjectType(objectType);
              hibernateTemplate.saveBatch(objectStorageID);
          }
        }finally{
          session.close();
        }
    }
    
}
