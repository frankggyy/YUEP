/*
 * $Id: OperationLogRecorder.java, 2011-3-24 ����01:24:57 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.oplog;

import java.util.Collection;

import com.yuep.base.concurrent.def.collaboration.CollaborationService;
import com.yuep.base.concurrent.def.collaboration.producerconsumer.ConsumerListener;
import com.yuep.base.concurrent.def.collaboration.producerconsumer.ProducerConsumer;
import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepException;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.core.facade.def.AbstractFacadeProcessor;
import com.yuep.core.facade.def.FacadeResponse;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;
import com.yuep.nms.core.common.base.def.DisplayNameGetter;
import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingGetter;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.server.smcore.SmCoreContext;

/**
 * <p>
 * Title: OperationLogRecorder
 * </p>
 * <p>
 * Description:��¼������־
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����01:24:57
 * modified [who date description]
 * check [who date description]
 */
public class OperationLogRecorder extends AbstractFacadeProcessor{
    
    private ProducerConsumer pc;
    private SessionService sessionService;
    private MoCore moCore;
    
    public void init(){
        sessionService=SmCoreContext.getLocalService("sessionService", SessionService.class);
        moCore=SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        
        // ������- ������ģʽ��д������־�����������������߳�
        CollaborationService collaborationService=SmCoreContext.getLocalService("collaborationService", CollaborationService.class);
        pc = collaborationService.addProducerConsumer("oplog", 1000);
        WriteLog2Db writer2Db=new WriteLog2Db();
        pc.addConsumerListener(writer2Db);
    }
    
    public FacadeResponse afterInvoke(FacadeResponse lastResponse, Class<?> serviceClz, String methodName, Class<?>[] paramType, Object[] args) {
        FacadeMethod[] anns = SmCoreContext.getFacadeMethods(serviceClz,methodName,paramType);
        // ���˵�����Ҫ��¼������־�����
        if(anns==null || anns.length==0)
            return lastResponse;
        FacadeMethod ann=anns[0];
        if(!ann.needLog())       
            return lastResponse;

        // ��������־��Ϣ
        OperationLog log=new OperationLog();
        String source=getSource(args, ann.sourceLocation());
        log.setSource(source);
        log.setOperateName(serviceClz.getSimpleName()+"."+methodName);
        log.setOperateTime(System.currentTimeMillis());
        log.setCategory(ann.oplogType().getValue());
        
        if(lastResponse==null){
            log.setOperateResult(OperationLog.LOG_RESULT_FAILED);
        }else{
            if(lastResponse.getThrowedException()!=null){
                log.setOperateResult(OperationLog.LOG_RESULT_EXCEPTION);
                String detailInfo = getExceptionDetailInfo(lastResponse.getThrowedException());
                log.setDetailInfo(detailInfo); //�쳣��
            }
        }
        
        // ��ȡuser,ip..
        Session session = sessionService.getSession();
        if(session==null){
            log.setUserName("unknown");
        }else{
            log.setUserName(session.getOwner());
            log.setIp(session.getIp());
        }
        
        pushLog2Queue(log);
        return lastResponse;
    }

    /**
     * �����쳣����Ҫ����쳣�ĸ�����Ϣ
     * @param th
     * @return
     */
    private String getExceptionDetailInfo(Throwable th){
        Throwable ex = ExceptionUtils.getRawThrowable(th);
        if(ex instanceof YuepException){
            return ex.getClass().getSimpleName()+"."+(((YuepException)ex).getErrorCode()); // eg:xxException.1244
        }else{
            return ex.getClass().getSimpleName();
        }
    }
    
    /**
     * ��ȡ��������
     * @param args
     * @param location
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getSource(Object[] args,int location){
        String source=null;
        if(args==null || args.length==0){
            source="system";
        }else {
            if(args.length<=location){
                source="unkown";
            }else{
                Object subject=args[location];
                if(subject==null){
                    source="null";
                }else if(subject instanceof Collection){
                    Collection col=(Collection)subject;
                    if(col.size()==0)
                        source="null";
                    else if(col.size()==1)
                        source=getSingleObjectName(col.iterator().next());
                    else// �������
                        source="multiple object=["+getSingleObjectName(col.iterator().next())+",...]";
                }else if(subject instanceof Object[]){
                    Object[] col=(Object[])subject;
                    if(col.length==0)
                        source="null";
                    else if(col.length==1)
                        source=getSingleObjectName(col[0]);
                    else// �������
                        source="multiple object=["+getSingleObjectName(col[0])+",...]";
                }else{
                    source=getSingleObjectName(subject);
                }
            }
        }
        
        return source;
    }
    
    private String getSingleObjectName(Object subject){
        if(subject==null)
            return "null";
        if(subject instanceof MoNaming){
            return getMoDisplayName((MoNaming)subject);
        }else if(subject instanceof MoNamingGetter){
            MoNaming moNaming = ((MoNamingGetter)subject).getMoNaming();
            return getMoDisplayName(moNaming);
        }else if(subject instanceof DisplayNameGetter){
            return ((DisplayNameGetter)subject).getDisplayName();
        }
        return subject.toString();
    }
    
    /**
     * mo����ʾ��
     * @param moNaming
     * @return
     */
    private String getMoDisplayName(MoNaming moNaming){
        if(moNaming==null)
            return "null";
        Mo mo = moCore.getMo(moNaming);
        if(mo==null)
            return moNaming.toString();
        else
            return mo.getDisplayName();
    }
    
    /**
     * ���뵽������
     * @param oplog
     */
    private void pushLog2Queue(OperationLog oplog){
        if(oplog==null)
            return;
        pc.produce(oplog);
    }
    
    /**
     * �ڲ��࣬�����Ѳ�����־�������ݿ�
     * <p>
     * Title: WriteLog2Db
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author sufeng
     * created 2011-4-1 ����10:13:32
     * modified [who date description]
     * check [who date description]
     */
    static class WriteLog2Db implements ConsumerListener{
        private BaseDao<OperationLog> operationLogDao;
        @SuppressWarnings("unchecked")
        public WriteLog2Db(){
            operationLogDao=SmCoreContext.getBean("operationLogDao", BaseDao.class);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public void handle(Collection<Object> objsInQueue) {
            operationLogDao.saveBatch((Collection)objsInQueue);
        }
    };
    
}

    