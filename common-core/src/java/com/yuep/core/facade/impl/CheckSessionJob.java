/*
 * $Id: CheckSessionJob.java, 2011-1-31 ����09:38:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.facade.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yuep.base.scheduler.FixedIntervalSchedulerJob;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;

/**
 * <p>
 * Title: CheckSessionJob
 * </p>
 * <p>
 * Description: ���Զ���ϵͳ������client���ĻỰ������server�����client������״̬��sbi�����server������״̬, server������ϼ����ܵ�����״̬��
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����09:38:10
 * modified [who date description]
 * check [who date description]
 */
public class CheckSessionJob extends FixedIntervalSchedulerJob {

    private SessionService sessionService;
    private long timeout=60000L; //1����
    
    /**
     * 
     * @param interval ��
     */
    public CheckSessionJob(int interval) {
        super(null, null, interval);
        sessionService=CoreContext.getInstance().local().getService("sessionService", SessionService.class);
    }

    @Override
    public void execute() {
        Collection<Session> rawSessions = sessionService.getAllSessions();
        List<Session> sessions=new ArrayList<Session>();
        sessions.addAll(rawSessions);
        long now=System.currentTimeMillis();
        for(Session session : sessions){
            // �����ʱ�ˣ���Ϊsession����
            long distance=now-session.getLastActivetime();
            if(distance>timeout){
                sessionService.cleanup(session.getSessionId());
            }
        }
    }

}
