package com.yuep.nms.core.sbi.nemanager;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Iterator;
import java.util.List;

import com.yuep.base.exception.YuepException;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.impl.ModuleContextImpl;
import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.model.SbiInitialParameter;
import com.yuep.nms.core.common.sbi.service.SbiSysMgr;
import com.yuep.nms.core.sbi.sbicore.SbiContext;

/**
 * <p>
 * Title: SbiSysMgrImpl
 * </p>
 * <p>
 * SBI�����ṩ��Ԫ����ӿ�ʵ����
 * </p>
 * 
 * @author pl
 * created 2011-4-16 ����04:01:04
 * modified [who date description]
 * check [who date description]
 */
public class SbiSysMgrImpl implements SbiSysMgr {

    @Override
    public void ping() {
        // TODO
    }

    @Override
    public void shutdownSbi() {
        //System.exit(0);
        ModuleContextImpl.notifyExit();
    }

    @Override
    public void initParameters(SbiInitialParameter parameter) {
        // sbi��ʼ��
        SbiContext.setSbiInitialParameter(parameter);
        try {
            String serviceIp = getClientHost();//��ȡserver�˵�IP
            List<NeLoginParameter> neList = parameter.getNeList();
            for (NeLoginParameter ne : neList) {
                if (SbiContext.nmIpNeMap.containsKey(serviceIp)) {
                    SbiContext.nmIpNeMap.get(serviceIp).add(ne);
                } else {
                    SbiContext.nmIpNeMap.put(serviceIp, YuepObjectUtils.newArrayList(ne));
                }
            }
        } catch (Exception e) {
            throw new YuepException("initParameters in sbi error");
        }
        SbiContext.setPingOk(true); //���ñ�־λ
    }

    @Override
    public void addNe(NeLoginParameter ne) {
        // ��ne���뵽sbi��cache��
        try {
            String serviceIp = getClientHost();//�����ip
            if (SbiContext.nmIpNeMap.containsKey(serviceIp)) {
                SbiContext.nmIpNeMap.get(serviceIp).add(ne);
            } else {
                SbiContext.nmIpNeMap.put(serviceIp, YuepObjectUtils.newArrayList(ne));
            }
        } catch (Exception e) {
            throw new YuepException("add ne in sbi error");
        }
    }

    @Override
    public void deleteNe(NeLoginParameter ne) {
        try {
            String serviceIp = getClientHost();
            List<NeLoginParameter> neList = SbiContext.nmIpNeMap.get(serviceIp);
            for (Iterator<NeLoginParameter> iterator = neList.iterator(); iterator.hasNext();) {
                NeLoginParameter neNode = iterator.next();
                if (neNode.getNeNaming().equals(ne.getNeNaming())) {
                    iterator.remove();
                    break;
                }
            }
        } catch (Exception e) {
            throw new YuepException("delete ne in sbi error");
        }
    }

    @Override
    public void updateNeLoginParam(NeLoginParameter ne) {
        try {
            String serviceIp = getClientHost();
            getClientHost();
            List<NeLoginParameter> neList = SbiContext.nmIpNeMap.get(serviceIp);
            for (Iterator<NeLoginParameter> iterator = neList.iterator(); iterator.hasNext();) {
                NeLoginParameter neNode = iterator.next();
                if (neNode.getNeNaming().equals(ne.getNeNaming())) {
                    iterator.remove();
                    break;
                }
            }
            neList.add(ne);
        } catch (Exception e) {
            throw new YuepException("updateNeLoginParam in sbi error");
        }
    }
    
    /**
     * ��rmi�ĵ�ǰthread�л�ȡ����˵�ip
     * @return
     */
    protected String getClientHost(){
        try {
            return RemoteServer.getClientHost();
        } catch (ServerNotActiveException e) {
            throw new YuepException("get client host error");
        }
    }
    
}
