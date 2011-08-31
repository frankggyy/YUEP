/*
 * $Id: SubSystemPropertyDaoImpl.java, 2011-5-16 下午06:04:38 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.property.dao.impl;

import java.util.List;

import com.yuep.core.db.access.basedao.BaseDaoUtil;
import com.yuep.core.db.access.basedao.DaoExecute;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao;

/**
 * <p>
 * Title: SubSystemPropertyDaoImpl
 * </p>
 * <p>
 * Description:
 * SubSystemPropertyDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:04:38
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyPropertyDaoImpl implements SubSystemProxyPropertyDao {

    private SessionFactory sessionFactory;
    public SubSystemProxyPropertyDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao#createSubSystemProxyProperty(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty)
     */
    @Override
    public void createSubSystemProxyProperty(final SubSystemProxyProperty subSystemProxyProperty) {
         BaseDaoUtil.doSql(sessionFactory, SubSystemProxyProperty.class, new DaoExecute<SubSystemProxyProperty>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemProxyProperty> template) {
                template.saveBatch(subSystemProxyProperty);
                return subSystemProxyProperty;
            }
         });
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao#updateSubSystemProxyProperty(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty)
     */
    @Override
    public void updateSubSystemProxyProperty(final SubSystemProxyProperty subSystemProxyProperty) {
        BaseDaoUtil.doSql(sessionFactory, SubSystemProxyProperty.class, new DaoExecute<SubSystemProxyProperty>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemProxyProperty> template) {
                template.updateBatch(subSystemProxyProperty);
                return subSystemProxyProperty;
            }
         });
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao#deleteSubSystemProxyProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSubSystemProxyProperty(final MoNaming subSystemId) {
        BaseDaoUtil.doSql(sessionFactory, SubSystemProxyProperty.class, new DaoExecute<SubSystemProxyProperty>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemProxyProperty> template) {
                template.deleteEntityByProperty("subSystemId", subSystemId);
                return null;
            }
         });
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao#getAllSubSystemProxyProperties()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SubSystemProxyProperty> getAllSubSystemProxyProperties() {
       return (List<SubSystemProxyProperty>)BaseDaoUtil.doSql(sessionFactory, SubSystemProxyProperty.class, new DaoExecute<SubSystemProxyProperty>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemProxyProperty> template) {
                return template.getAllEntities();
            }
         });
    }
   

   
}
