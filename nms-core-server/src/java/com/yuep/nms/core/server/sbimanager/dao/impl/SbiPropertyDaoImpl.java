/*
 * $Id: SbiPropertyDaoImpl.java, 2011-4-15 下午12:41:19 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.dao.impl;

import java.util.List;

import com.yuep.core.db.access.basedao.BaseDaoUtil;
import com.yuep.core.db.access.basedao.DaoExecute;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao;

/**
 * <p>
 * Title: SbiPropertyDaoImpl
 * </p>
 * <p>
 * Description:
 * SbiPropertyDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午12:41:19
 * modified [who date description]
 * check [who date description]
 */
public class SbiPropertyDaoImpl implements SbiPropertyDao {
    private SessionFactory sessionFactory;
    
    public SbiPropertyDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#createSbiProperty(com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public void createSbiProperty(final SbiProperty sbiProperty) {
        BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                template.saveBatch(sbiProperty);
                return sbiProperty;
            }
        });
        
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#deleteSbiProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbiProperty(final MoNaming sbiNaming) {
        BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                template.deleteEntityByProperty("sbiNaming", sbiNaming);
                return null;
            }
        });
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#updateSbiProperty(com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public void updateSbiProperty(final SbiProperty sbiProperty) {
        BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                template.updateBatch(sbiProperty);
                return null;
            }
        });
        
    }


    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#getAllSbiPropertys()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SbiProperty> getAllSbiPropertys() {

      return  (List<SbiProperty>) BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                return  template.getAllEntities();
            }
        });
        
    
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#getSbiProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SbiProperty getSbiProperty(final MoNaming sbiNaming) {
       return (SbiProperty) BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                SbiProperty property = template.getUniqueEntityByOneProperty("sbiNaming", sbiNaming);
                return property;
            }
        });        
    }
    
    @Override
    public SbiProperty getSbiPropertyByIpPort(final String ip,final int port){
        return (SbiProperty) BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                List<SbiProperty> propertyList = template.getEntitiesByPropNames(new String[]{"ip","port"}, new Object[]{ip,port});
                return propertyList.get(0);
            }
        });
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao#getSbiProperty(java.lang.String)
     */
    @Override
    public SbiProperty getSbiProperty(final String sbiName) {
        return (SbiProperty) BaseDaoUtil.doSql(sessionFactory, SbiProperty.class, new DaoExecute<SbiProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiProperty> template) {
                return template.getUniqueEntityByOneProperty("sbiName", sbiName);
            }
        });
    }

}
