package com.yuep.base.antpathmatch.test;

import junit.framework.TestCase;

import org.springframework.util.AntPathMatcher;



public class AntPathMatcherTest extends TestCase{


    public void setUp() throws Exception {
    }


    public void tearDown() throws Exception {
    }
    
    
    public void testMatch(){
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        boolean match=antPathMatcher.match("**/*a/*test.txt", "D:/yuepworkspace/common-base/classes/com/yuep/base/resource/test/aa/test.txt");
        assertEquals(true, match);
    }

}
