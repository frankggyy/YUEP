package com.yuep.nms.core.sbi.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuep.base.util.net.snmp.model.YuepSnmpConst;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.service.SbiSysMgr;
import com.yuep.nms.core.sbi.nemanager.SbiSysMgrImpl;
import com.yuep.nms.core.sbi.sbicore.SbiContext;

import junit.framework.TestCase;

/**
 * <p>
 * Title: SbiSysMgrTest
 * </p>
 * <p>
 * SbiSysMgr²âÊÔ´úÂë
 * </p>
 * 
 * @author pl
 * created 2011-5-11 ÏÂÎç04:44:41
 * modified [who date description]
 * check [who date description]
 */
public class SbiSysMgrTest extends TestCase {
    private SbiSysMgr sysMgr1 = new SbiSysMgrImpl() {
        @Override
        protected String getClientHost() {
            return "192.168.11.1";
        }
    };
    private SbiSysMgr sysMgr2 = new SbiSysMgrImpl() {
        @Override
        protected String getClientHost() {
            return "192.168.11.2";
        }
    };

    public void testAddNe() {
        NeLoginParameter parameter1 = new NeLoginParameter();
        parameter1.setIp("192.168.11.251");
        parameter1.setNeNaming(new MoNaming("/NM=1/NE=1"));
        NeLoginParameter parameter2 = new NeLoginParameter();
        parameter2.setIp("192.168.11.252");
        parameter2.setNeNaming(new MoNaming("/NM=1/NE=1"));
        sysMgr1.addNe(parameter1);
        sysMgr2.addNe(parameter2);
        Map<String, List<NeLoginParameter>> nmIpNeMap = SbiContext.nmIpNeMap;
        assertTrue(nmIpNeMap.containsKey("192.168.11.1"));
        assertTrue(nmIpNeMap.containsKey("192.168.11.2"));
    }

    public void testUpdateNeLoginParam() {
        NeLoginParameter parameter1 = new NeLoginParameter();
        parameter1.setIp("192.168.11.251");
        parameter1.setNeNaming(new MoNaming("/NM=1/NE=1"));
        NeLoginParameter parameter2 = new NeLoginParameter();
        parameter2.setIp("192.168.11.252");
        parameter2.setNeNaming(new MoNaming("/NM=1/NE=1"));
        sysMgr1.addNe(parameter1);
        sysMgr2.addNe(parameter2);
        // update
        Map<String, Object> loginMap = new HashMap<String, Object>();
        loginMap.put(YuepSnmpConst.SNMP_PORT, 161);
        parameter1.setLoginMap(loginMap);
        sysMgr1.updateNeLoginParam(parameter1);
        Map<String, List<NeLoginParameter>> nmIpNeMap = SbiContext.nmIpNeMap;
        assertTrue(nmIpNeMap.size() == 2);
        NeLoginParameter neLoginParameter = nmIpNeMap.get("192.168.11.1").get(0);
        int port = ((Integer) neLoginParameter.getLoginMap().get(YuepSnmpConst.SNMP_PORT)).intValue();
        assertTrue(port == 161);
    }
}
