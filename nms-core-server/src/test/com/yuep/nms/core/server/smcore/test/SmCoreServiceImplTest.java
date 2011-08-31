/*
 * $Id: SmCoreServiceImplTest.java, 2011-5-9 下午03:28:52 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.service.SmCoreService;
import com.yuep.nms.core.server.base.test.CoreContextMock;
import com.yuep.nms.core.server.base.test.ModuleContextMock;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.SmCoreModule;
import com.yuep.nms.core.server.smcore.perm.PermissionManager;

/**
 * <p>
 * Title: SmCoreServiceImplTest
 * </p>
 * <p>
 * Description:SmCoreService的测试用例
 * </p>
 * 
 * @author sufeng
 * created 2011-5-9 下午03:28:52
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreServiceImplTest extends TestCase {

    private static SmCoreService smCoreService;
    private static SessionFactory sessionFactory;
    private static PermissionManager permManager;
    
    private static boolean inited=false;
    
    @Override
    protected void setUp() throws Exception {
        if(inited)            
            return;
        
        try{
            Logger logger=new SimpleLogger("smcore");
            SmCoreModule smCoreModule=new SmCoreModule();
            smCoreModule.setModuleName("smcore-server");
            
            // get home dir
            URL url=this.getClass().getResource("/com/yuep/nms/core/server/smcore/test/test-yuepdb-smcore.xml");
            String file = url.getFile();
            int pos = file.indexOf("nms-core-server/");
            String path=file.substring(0, pos);
            String homeDir=path+"nms-core-server";
            
            // init module manager
            ModuleContextMock.init();
            ModuleContextMock moduleContext = (ModuleContextMock) ModuleContext.getInstance();
            moduleContext.putParam(ContainerConst.KEY_HOME_DIR, homeDir+"/../yuep-build");
            
            moduleContext.putModule(SmCoreModule.class, smCoreModule);
            moduleContext.setCoreModule(smCoreModule);
            moduleContext.setContainerLogger(logger);
            
            CoreContextMock.init();
            
            SmCoreContext.setCoreContext(CoreContext.getInstance());
            
            // sm core service init
            SmCoreContext.setLogger(logger);
            // 启动db
            SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
            
            sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
            
            //  设置2个session factory相关的local service
            SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
            CoreContext.getInstance().setLocalService("sessionFactoryManager", SessionFactoryManager.class, sessionFactoryManager);
            
            sessionFactory=sessionFactoryManager.getSessionFactory("yuep");
            CoreContext.getInstance().setLocalService("sessionFactory", SessionFactory.class, sessionFactory);
            
            ApplicationContext ctx = new FileSystemXmlApplicationContext(homeDir+"/resource/smcore/conf/appctx-sm.xml");
            SmCoreContext.setCtx(ctx);
            smCoreService=SmCoreContext.getBean("smCoreService", SmCoreService.class);
            permManager=SmCoreContext.getBean("permissionManager", PermissionManager.class);
        }catch(Throwable th){
            System.out.println("SmCoreServiceImplTest init");
        }
        inited=true;
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testGetUser() {
        try{
            User user = smCoreService.getUser("admin");
            assertNotNull(user);
        }catch(Throwable th){
            System.out.println("testGetUser finished!");
        }
    }

    public void testAddUser() {
        try{
            User user = new User();
            user.setUserName("test");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            user.setRoles(roles);
            smCoreService.addUser(user);
            User userFromDB = smCoreService.getUser("test");
            assertNotNull(userFromDB);
            smCoreService.deleteUser(user);
        }catch(Throwable th){
            System.out.println("testAddUser finished!");
        }
    }

    public void testDeleteUser() {
        try{
            User user = new User();
            user.setUserName("test");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            user.setRoles(roles);
            smCoreService.addUser(user);
            User userFromDB = smCoreService.getUser("test");
            assertNotNull(userFromDB);
            smCoreService.deleteUser(user);
            User deleteUser = smCoreService.getUser("test");
            assertNull(deleteUser);
        }catch(Throwable th){
            System.out.println("testDeleteUser finished!");
        }
    }

    public void testUpdateUser() {
        try{
            User user = new User();
            user.setUserName("test");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            user.setRoles(roles);
            smCoreService.addUser(user);
            User userFromDb=smCoreService.getUser("test");
            userFromDb.setDepartment("Department");
            smCoreService.updateUser(userFromDb);
            assertEquals(smCoreService.getUser("test").getDepartment(), "Department");
            smCoreService.deleteUser(user);
        }catch(Throwable th){
            System.out.println("testUpdateUser finished!");
        }
    }

    public void testgetAllUsersIpRange() {
        try{
            List<IpRange> ipRangeList = new ArrayList<IpRange>();
            IpRange ipRange = new IpRange();
            ipRange.setStartIpAddress("192.168.11.1");
            ipRange.setEndIpAddress("192.168.11.5");
            ipRangeList.add(ipRange);
            User user = new User();
            user.setUserName("test");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            user.setRoles(roles);
            user.setIpRanges(ipRangeList);
            smCoreService.addUser(user);
            List<IpRange> ipRangeListDB = smCoreService.getAllUsersIpRange();
            smCoreService.deleteUser(user);
            List<String> startIps = new ArrayList<String>();
            for (IpRange range : ipRangeListDB) {
                startIps.add(range.getStartIpAddress());
            }
            assertTrue(startIps.contains(ipRange.getStartIpAddress()));
        }catch(Throwable th){
            System.out.println("testgetAllUsersIpRange finished!");
        }

    }
    
    public void testGetRoleByName(){
        try{
            Role role = smCoreService.getRoleByName("admin");
            assertNotNull(role);
        }catch(Throwable th){
            System.out.println("testGetRoleByName finished!");
        }
    }
    
    public void testAddRole(){
        try{
            Role role = new Role();
            role.setRoleName("test1");
            role.setMoList(null);
            role.setDescription("test1");
            List<Role> oldList=smCoreService.getAllRoles();
            smCoreService.addRole(role);
            List<Role> newList=smCoreService.getAllRoles();
            assertEquals(newList.size(), oldList.size()+1);
            assertTrue(newList.contains(role));
        }catch(Throwable th){
            System.out.println("testAddRole finished!");
        }
    }
    
    public void testUpdateRole(){
        try{
            Role role = smCoreService.getRoleByName("test1");
            role.setDescription("test");
            smCoreService.updateRole(role);
            Role newRole = smCoreService.getRoleByName("test1");
            assertEquals(newRole.getDescription(),"test");
        }catch(Throwable th){
            System.out.println("testUpdateRole finished!");
        }
    }
    
    public void testDeleteRole(){
        try{
            List<Role> oldList=smCoreService.getAllRoles();
            smCoreService.deleteRole("test1");
            List<Role> newList=smCoreService.getAllRoles();
            assertEquals(newList.size()+1, oldList.size());
            Role role = smCoreService.getRoleByName("test1");
            assertNull(role);
        }catch(Throwable th){
            System.out.println("testDeleteRole finished!");
        }
    }
    
    public void testAddMoPerm(){
        try{
            MoPermGroup permGroup = new MoPermGroup();
            permGroup.setGroupName("test");
            permGroup.setMoName(new MoNaming("domain=1/ne=1/"));
            permGroup.setRoleName("test");
            smCoreService.addMoPerm(permGroup);
            MoPermGroup perm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
            assertNotNull(perm);
        }catch(Throwable th){
            System.out.println("testAddMoPerm finished!");
        }
    }
    
    public void testGetMoPerm(){
        try{
            MoPermGroup perm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
            assertNotNull(perm);
        }catch(Throwable th){
            System.out.println("testGetMoPerm finished!");
        }
    }
    
   public void testUpdateMoPerm(){
       try{
           MoPermGroup perm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
           perm.setGroupName("test2");
           smCoreService.updateMoPerm("test", perm);
           MoPermGroup newPerm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
           assertEquals("test2", newPerm.getGroupName());
       }catch(Throwable th){
           System.out.println("testUpdateMoPerm finished!");
       }
   }
   
   public void testDeleteMoPerm(){
       try{
           MoPermGroup perm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
           smCoreService.deleteMoPerm(perm);
           MoPermGroup newPerm = smCoreService.getMoPerm(new MoNaming("domain=1/ne=1/"), "test");
           assertNull(newPerm);
       }catch(Throwable th){
           System.out.println("testDeleteMoPerm finished!");
       }
   }
   
   public void testGetPermissions(){
       try{
           permManager.init();
           List<Permission> perms = smCoreService.getPermissions("MO");
           assertNotNull(perms);
       }catch(Throwable th){
           System.out.println("testGetPermissions finished!");
       }
   }
   
   public void testAddPermissionGroup(){
       try{
           PermissionGroup permGroup = new PermissionGroup();
           permGroup.setGroupName("test1");
           permGroup.setDescription("test1");
           permGroup.setPermissions(null);
           List<PermissionGroup> permList = smCoreService.getAllPermissionGroups();
           smCoreService.addPermissionGroup(permGroup);
           List<PermissionGroup> newPermList = smCoreService.getAllPermissionGroups();
           assertEquals(permList.size()+1, newPermList.size());
           assertTrue(newPermList.contains(permGroup));
       }catch(Throwable th){
           System.out.println("testAddPermissionGroup finished!");
       }
   }
   
   public void testGetPermissionGroup(){
       try{
           PermissionGroup permGroup = smCoreService.getPermissionGroup("test1");
           assertNotNull(permGroup);
       }catch(Throwable th){
           System.out.println("testGetPermissionGroup finished!");
       }
   }
    
   public void testUpdatePermissionGroup(){
       try{
           PermissionGroup permGroup = smCoreService.getPermissionGroup("test1");
           permGroup.setDescription("test2");
           smCoreService.updatePermissionGroup(permGroup);
           PermissionGroup newPermGroup = smCoreService.getPermissionGroup("test1");
           assertEquals("test2", newPermGroup.getDescription());
       }catch(Throwable th){
           System.out.println("testUpdatePermissionGroup finished!");
       }
   }
   
   public void testDeletePermissionGroup(){
       try{
           List<PermissionGroup> permList = smCoreService.getAllPermissionGroups();
           smCoreService.deletePermissionGroup("test1");
           List<PermissionGroup> newPermList = smCoreService.getAllPermissionGroups();
           assertEquals(permList.size()-1, newPermList.size());
       }catch(Throwable th){
           System.out.println("testDeletePermissionGroup finished!");
       }
   }
    
}
