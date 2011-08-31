/*
 * $Id: MySessionService.java, 2011-5-10 ÉÏÎç11:22:31 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;

/**
 * <p>
 * Title: MySessionService
 * </p>
 * <p>
 * Description:Ä£Äâsession service
 * </p>
 * 
 * @author sufeng
 * created 2011-5-10 ÉÏÎç11:22:31
 * modified [who date description]
 * check [who date description]
 */
public class MySessionService implements SessionService{

    private Map<Long,Session> sessions=new ConcurrentHashMap<Long, Session>();
    public void addSession(Session session){
        sessions.put(session.getSessionId(), session);
    }
    
    @Override
    public void cleanup(Long sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public Session connect(String clientIp, String serverIp) {
        return null;
    }

    @Override
    public Collection<Session> getAllSessions() {
        return sessions.values();
    }

    @Override
    public Session getSession() {
        Iterator<Long> sid = sessions.keySet().iterator();
        return getSessionById(sid.next());
    }

    @Override
    public Session getSessionById(Long sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Long getSessionId() {
        return getSession().getSessionId();
    }

    @Override
    public void setSessionInCurrentThread(Long sessionId) {
    }
    
};
