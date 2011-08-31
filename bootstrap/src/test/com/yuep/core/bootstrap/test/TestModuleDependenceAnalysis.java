/*
 * $Id: TestModuleDependenceAnalysis.java, 2011-3-1 ÏÂÎç04:15:54 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.yuep.core.bootstrap.impl.module.ModuleDependenceAnalysis;
import com.yuep.core.bootstrap.impl.module.ModuleEntity;

/**
 * <p>
 * Title: TestModuleDependenceAnalysis
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2011-3-1 ÏÂÎç04:15:54
 * modified [who date description]
 * check [who date description]
 */
public class TestModuleDependenceAnalysis extends TestCase{
    
    private ModuleDependenceAnalysis analysis;
     
    @Override
    protected void setUp() throws Exception {
        analysis=new ModuleDependenceAnalysis();
    }
    
    public void testFetchAllModule(){
        List<ModuleEntity> sourceModules=new ArrayList<ModuleEntity>();
        ModuleEntity core=new ModuleEntity("core");
        sourceModules.add(core);
        
        ModuleEntity m5=new ModuleEntity("m5");
        sourceModules.add(m5);
        
        ModuleEntity m1=new ModuleEntity("m1");
        m1.setDepends("core");
        sourceModules.add(m1);
        
        ModuleEntity m2=new ModuleEntity("m2");
        m2.setDepends("m1");
        sourceModules.add(m2);
        
        ModuleEntity m3=new ModuleEntity("m3");
        m3.setDepends("core,m5");
        sourceModules.add(m3);
        
        ModuleEntity m4=new ModuleEntity("m4");
        m4.setDepends("m3");
        sourceModules.add(m4);
        
        ModuleEntity m6=new ModuleEntity("m6");
        m6.setDepends("m5");
        sourceModules.add(m6);
        
        ModuleEntity m7=new ModuleEntity("m7");
        m7.setDepends("m6");
        sourceModules.add(m7);

        //ModuleEntity m8=new ModuleEntity("m8");
        //m8.setDepends("m9");
        //sourceModules.add(m8);
        
        //ModuleEntity m9=new ModuleEntity("m9");
        //m9.setDepends("m8");
        //sourceModules.add(m9);
        
        try{
            analysis.initModuleDepenceMesh(sourceModules);
            List<String> modules = analysis.getAllModulesByOrder();
            System.out.println(modules);
        }catch(Exception ex){
            System.out.println(ex);
            System.out.println("test completed");
        }
        
    }
    
    
}
