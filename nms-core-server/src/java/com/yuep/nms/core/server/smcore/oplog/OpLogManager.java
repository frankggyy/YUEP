/*
 * $Id: OpLogManager.java, 2011-3-24 下午01:37:28 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.oplog;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.core.db.criteria.Criteria;
import com.yuep.core.db.criteria.LogicalExpression;
import com.yuep.core.db.criteria.Restrictions;
import com.yuep.core.db.criteria.SimpleExpress;
import com.yuep.core.db.criteria.SqlConditionBuilder;
import com.yuep.core.facade.def.FacadeManager;
import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;

/**
 * <p>
 * Title: OpLogManager
 * </p>
 * <p>
 * Description:操作日志管理器
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:37:28
 * modified [who date description]
 * check [who date description]
 */
public class OpLogManager {

    private OperationLogRecorder operationLogRecorder;
    
    private BaseDao<OperationLog> operationLogDao;
    public void setOperationLogRecorder(OperationLogRecorder operationLogRecorder) {
        this.operationLogRecorder = operationLogRecorder;
    }
    public void setOperationLogDao(BaseDao<OperationLog> operationLogDao) {
        this.operationLogDao = operationLogDao;
    }
    
    /**
     * 初始化
     */
    public void init(){
        // 插入记录操作日志的Facade Processor
        operationLogRecorder.init();
        FacadeManager facadeManager = CoreContext.getInstance().local().getService("facadeManager", FacadeManager.class);
        facadeManager.appendFacadeProcessor(operationLogRecorder);
    }

    /**
     * 查询操作日志
     * @param cond
     * @return
     */
    public List<OperationLog> getOperationLog(OperationLogCondition cond){
        if(cond==null)
            return operationLogDao.getAllEntities();
        return operationLogDao.findByCriteria(getQueryCondition(cond));
    }
    
    /**
     * 操作名列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllOperationNames() {
        return (List<String>)operationLogDao.getEntityProps(new String[]{"operateName"}, new String[0], new Object[0],true);
    }

    /**
     * 操作对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllOperationObjects() {
        return (List<String>) operationLogDao.getEntityProps(new String[]{"source"}, new String[0], new Object[0],true);
    }
    
    /**
     * 操作用户名
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllOperationUserNames() {
        return (List<String>)operationLogDao.getEntityProps(new String[]{"userName"}, new String[0], new Object[0],true);
    }
    
    /**
     * 拼接查询条件
     * @param condition
     * @return
     */
    private String getQueryCondition(OperationLogCondition condition){
        SqlConditionBuilder builder=new SqlConditionBuilder();
        if(condition.getUserName()!=null)
            builder.add(Restrictions.eq("userName", condition.getUserName()));
        if(condition.getIp()!=null)
            builder.add(Restrictions.eq("ip", condition.getIp()));
        if(condition.getStartTime()!=null)
            builder.add(Restrictions.ge("operateTime", condition.getStartTime()));
        if(condition.getEndTime()!=null)
            builder.add(Restrictions.le("operateTime", condition.getEndTime()));
        if(condition.getResult()!=null)
            builder.add(Restrictions.eq("operateResult", condition.getResult()));
        if(condition.getCategory()!=null)
            builder.add(Restrictions.eq("category", condition.getCategory()));
        
        if(CollectionUtils.isNotEmpty(condition.getObjects())){
            int size=condition.getObjects().size();
            SimpleExpress firstSource = Restrictions.eq("source", condition.getObjects().get(0));
            if(size==1){
                builder.add(firstSource);
            }else{
                Criteria lastCriteria=firstSource;
                for(int i=1;i<size;i++){
                    LogicalExpression or = Restrictions.or(lastCriteria, Restrictions.eq("source", condition.getObjects().get(i)));
                    lastCriteria=or;
                }
                builder.add(lastCriteria);
            }
        }
        
        if (CollectionUtils.isNotEmpty(condition.getOperateNames())) {
            int size=condition.getOperateNames().size();
            SimpleExpress firstOperateName = Restrictions.eq("operateName", condition.getOperateNames().get(0));
            if(size==1){
                builder.add(firstOperateName);
            }else{
                Criteria lastCriteria=firstOperateName;
                for(int i=1;i<size;i++){
                    LogicalExpression or = Restrictions.or(lastCriteria, Restrictions.eq("operateName", condition.getOperateNames().get(i)));
                    lastCriteria=or;
                }
                builder.add(lastCriteria);
            }
        }
        return builder.toSql();
    }
    
    /**
     * 添加操作日志
     * @param log
     */
    public void addOperationLog(OperationLog ... logs){
        operationLogDao.saveBatch(logs);
    }
    
}
