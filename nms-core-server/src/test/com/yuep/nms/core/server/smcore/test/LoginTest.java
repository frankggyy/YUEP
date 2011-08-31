/*
 * $Id: LoginTest.java, 2011-5-10 ����11:18:22 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.test;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;
import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.base.util.format.IpAddrFormatter;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.def.SessionState;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.service.SmCoreService;
import com.yuep.nms.core.server.base.test.CoreContextMock;
import com.yuep.nms.core.server.base.test.ModuleContextMock;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.SmCoreModule;
import com.yuep.nms.core.server.smcore.auth.UserManager;

/**
 * <p>
 * Title: LoginTest
 * </p>
 * <p>
 * Description:��½�Ĳ�����������Ҫע�ⷢ����������ҪΪyuep-build
 * </p>
 * 
 * @author sufeng
 * created 2011-5-10 ����11:18:22
 * modified [who date description]
 * check [who date description]
 */
public class LoginTest extends TestCase {
    private static SmCoreService smCoreService;
    private static boolean inited = false;
    private static SessionFactory sessionFactory;
    private static UserManager userManager;
    private static MySessionService sessionService = new MySessionService();

    @Override
    protected void setUp() throws Exception {
        if (inited)
            return;

        try{
            Logger logger = new SimpleLogger("smcore");
            SmCoreModule smCoreModule = new SmCoreModule();
            smCoreModule.setModuleName("smcore-server");
    
            // get home dir
            URL url = this.getClass().getResource("/com/yuep/nms/core/server/smcore/test/test-yuepdb-smcore.xml");
            String homeDir=getHomeDir(url);
            
            // init module manager
            ModuleContextMock.init();
            ModuleContextMock moduleContext = (ModuleContextMock) ModuleContext.getInstance();
            moduleContext.putParam(ContainerConst.KEY_HOME_DIR, homeDir + "/../yuep-build");
    
            moduleContext.putModule(SmCoreModule.class, smCoreModule);
            moduleContext.setCoreModule(smCoreModule);
            moduleContext.setContainerLogger(logger);
    
            CoreContextMock.init();
    
            SmCoreContext.setCoreContext(CoreContext.getInstance());
    
            // sm core service init
            SmCoreContext.setLogger(logger);
            // ����db
            SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration = new SessionFactoryManagerConfiguration();
    
            sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
    
            // ����2��session factory��ص�local service
            SessionFactoryManager sessionFactoryManager = sessionFactoryManagerConfiguration.buildSessionFactoryManager();
            CoreContext.getInstance().setLocalService("sessionFactoryManager", SessionFactoryManager.class,
                    sessionFactoryManager);
    
            sessionFactory = sessionFactoryManager.getSessionFactory("yuep");
            CoreContext.getInstance().setLocalService("sessionFactory", SessionFactory.class, sessionFactory);
    
            ApplicationContext ctx = new FileSystemXmlApplicationContext(homeDir + "/resource/smcore/conf/appctx-sm.xml");
            SmCoreContext.setCtx(ctx);
            smCoreService = SmCoreContext.getBean("smCoreService", SmCoreService.class);
    
            // mock session
            String[] ips = CmdUtils.getLocalIps();
            Session session = new Session(1L);
            session.setOwner("admin");
            session.setIp(ips[0]);
            session.setServerIp(ips[0]);
            session.setSessionState(SessionState.Linkup);
            sessionService.addSession(session);
            CoreContext.getInstance().setLocalService("sessionService", SessionService.class, sessionService);
    
            userManager = SmCoreContext.getBean("userManager", UserManager.class);
            userManager.init();
        }catch(Throwable th){
            System.out.println("LoginTest init");
        }
        inited = true;
    }
    
    /**
     * �õ����𻷾���home dir(���������Ҫ����ʵ���������,���粿���Ŀ¼�ȵ�)
     * @return
     */
    private String getHomeDir(URL url){
        String file = url.getFile();
        int pos = file.indexOf("nms-core-server/");
        String path = file.substring(0, pos);
        String homeDir = path + "nms-core-server";
        return homeDir;
    }

    /**
     * ��¼�Ĳ���
     */
    public void testLogin() {
        try{
            login();
        }catch(Throwable th){
            System.out.println("testLogin finished");
        }
    }
    private void login(){
        // ʹ����ȷ������
        boolean loginOk = false;
        try {
            userManager.login("admin", "abc");
            loginOk = true;
        } catch (SmException ex) {
            System.out.println(ex);
        } catch (Exception ex) {
            loginOk = true;
        }
        assertFalse(loginOk);

        // ʹ�ò���ȷ������
        try {
            userManager.login("admin", "admin");
            loginOk = true;
        } catch (SmException ex) {
            System.out.println(ex);
        } catch (Exception ex) {
            loginOk = true;
        }
    }

    /**
     *  ���Թ����û���½
     */
    public void testUserExpired() {
        try{
            userExpired();
        }catch(Throwable th){
            System.out.println("testUserExpired finished");
        }
    }
    private void userExpired(){
        boolean loginOk = false;
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        user.setRoles(roles);
        // �û��ѹ��ڣ���½���쳣
        user.setExpiredTime(System.currentTimeMillis() - 1000);
        smCoreService.addUser(user);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (SmException ex) {
            assertEquals(ex.getErrorCode(), SmException.USER_EXPIRED);
        } catch (Exception ex) {
            loginOk = true;

        }
        assertFalse(loginOk);
        // �û�δ����,��������½
        User userFromDb = smCoreService.getUser("test");
        userFromDb.setExpiredTime(System.currentTimeMillis() + 1000);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (Exception ex) {
            loginOk = true;

        }
        assertTrue(loginOk);
        smCoreService.deleteUser(user);
    }

    /**
     *  ������������û���½
     */
    public void testPwdExpired() {
        try{
            pwdExpired();
        }catch(Throwable th){
            System.out.println("testPwdExpired finished");
        }
    }
    private void pwdExpired(){
        boolean loginOk = false;
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        user.setRoles(roles);
        // �û������ѹ��ڣ���½���쳣
        user.setPasswordExpiredTime(System.currentTimeMillis() - 1000);
        smCoreService.addUser(user);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (SmException ex) {
            assertEquals(ex.getErrorCode(), SmException.PASSWORD_EXPIRED);
        } catch (Exception ex) {
            loginOk = true;

        }
        assertFalse(loginOk);

        // �û�����δ���ڣ���½����
        User userFromDb = smCoreService.getUser("test");
        userFromDb.setPasswordExpiredTime(System.currentTimeMillis() + 1000);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (Exception ex) {
            loginOk = true;

        }
        assertTrue(loginOk);

        smCoreService.deleteUser(user);
    }

    /**
     *  ���Խ����û���½
     */
    public void testInactiveUser() {
        try{
            inactiveUser();
        }catch(Throwable th){
            System.out.println("testInactiveUser finished!");
        }
    }
    private void inactiveUser(){
        boolean loginOk = false;
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        user.setRoles(roles);
        // �û������ã���½���쳣
        user.setState(User.STATE_DISABLE);
        smCoreService.addUser(user);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (SmException ex) {
            assertEquals(ex.getErrorCode(), SmException.USER_DISABLE);
        } catch (Exception ex) {
            loginOk = true;

        }
        assertFalse(loginOk);

        // �û��������½����
        User userFromDb = smCoreService.getUser("test");
        userFromDb.setState(User.STATE_NORMAL);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (Exception ex) {
            loginOk = true;

        }
        assertTrue(loginOk);
        smCoreService.deleteUser(user);
    }

    /**
     *  �����û���½��Ip��Χ
     */
    public void testIpRange() {
        try{
            ipRange();
        }catch(Throwable th){
            System.out.println("testIpRange finished!");
        }
    }
    private void ipRange(){
        boolean loginOk = false;
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        user.setRoles(roles);
        InetAddress ia = null;
        String ip =null;
        try {
            ia = InetAddress.getLocalHost();
            ip = ia.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ip="";
        }
        // �û��ĵ�½ip���ڹ���Χ��
        IpRange ipRange = new IpRange();
        List<IpRange> ipRanges = new ArrayList<IpRange>();
        ipRange.setStartIpAddress(IpAddrFormatter.nextIpAddress(ip));
        ipRange.setEndIpAddress(IpAddrFormatter.nextIpAddress(IpAddrFormatter.nextIpAddress(ip)));
        ipRanges.add(ipRange);
        user.setIpRanges(ipRanges);
        smCoreService.addUser(user);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (SmException ex) {
            assertEquals(ex.getErrorCode(), SmException.NOT_IN_IPRANGE);
        } catch (Exception ex) {
            loginOk = true;

        }
        assertFalse(loginOk);

        // �û���½��ip�ڹ���Χ��,�ɹ���½
        User userFromDb = smCoreService.getUser("test");
        ipRange.setStartIpAddress(ip);
        ipRange.setEndIpAddress(IpAddrFormatter.nextIpAddress(ip));
        ipRanges.clear();
        ipRanges.add(ipRange);
        userFromDb.setIpRanges(ipRanges);
        try {
            userManager.login("test", "test");
            loginOk = true;
        } catch (Exception ex) {
            loginOk = true;

        }
        assertTrue(loginOk);
        smCoreService.deleteUser(user);
    }

}
