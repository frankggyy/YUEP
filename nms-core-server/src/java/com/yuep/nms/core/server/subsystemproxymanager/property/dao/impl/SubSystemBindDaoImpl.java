/*
 * $Id: SubSystemBindDaoImpl.java, 2011-5-16 下午06:03:46 yangtao Exp $
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
import com.yuep.nms.core.common.subsystemproxycore.SubSystemBind;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemBindDao;

/**
 * <p>
 * Title: SubSystemBindDaoImpl
 * </p>
 * <p>
 * Description:
 * SubSystemBindsDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:03:46
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemBindDaoImpl implements SubSystemBindDao {

    private SessionFactory sessionFactory;
    public SubSystemBindDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemBindDao#deleteSubSystemBind(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSubSystemBind(final MoNaming subSystemId, final MoNaming mangedNode) {
        BaseDaoUtil.doSql(sessionFactory, SubSystemBind.class, new DaoExecute<SubSystemBind>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemBind> template) {
                SubSystemBind subSystemBinds=template.getUniqueEntityByPropNames(new String[]{"subSystemId","mangedNode"}, new Object[]{subSystemId,mangedNode});
                template.deleteAllEntities(subSystemBinds);
                return subSystemBinds;
            }
         }); 
    
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemBindDao#setSubSystemBind(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void setSubSystemBind(final MoNaming subSystemId, final MoNaming managedNode) {
        BaseDaoUtil.doSql(sessionFactory, SubSystemBind.class, new DaoExecute<SubSystemBind>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemBind> template) {
                SubSystemBind subSystemBind=template.getUniqueEntityByPropNames(new String[]{"managedNode"}, new Object[]{managedNode});
                if(subSystemBind==null){
                    subSystemBind=new SubSystemBind();
                    subSystemBind.setManagedNode(managedNode);
                    subSystemBind.setSubSystemId(subSystemId);
                    template.saveBatch(subSystemBind);
                }else{
                    subSystemBind.setSubSystemId(subSystemId);
                    template.updateBatch(subSystemBind);
                }
                return subSystemBind;
            }
         }); 
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemBindDao#getAllSubSystemBinds()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SubSystemBind> getAllSubSystemBinds() {
       return  (List<SubSystemBind>)BaseDaoUtil.doSql(sessionFactory, SubSystemBind.class, new DaoExecute<SubSystemBind>(){
            @Override
            public Object execute(HibernateTemplate<SubSystemBind> template) {
               return template.getAllEntities();
            }
         }); 
    }

}
