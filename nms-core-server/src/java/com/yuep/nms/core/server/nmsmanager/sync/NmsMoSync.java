/*
 * $Id: NmsMoSync.java, 2011-5-19 上午09:39:03 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsmanager.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.server.synccore.execute.Sync;
import com.yuep.nms.core.server.synccore.execute.SyncListener;

/**
 * <p>
 * Title: NmsMoSync
 * </p>
 * <p>
 * Description:
 * 下级网管Mo同步
 * </p>
 * 
 * @author yangtao
 * created 2011-5-19 上午09:39:03
 * modified [who date description]
 * check [who date description]
 */
public class NmsMoSync implements Sync {
    
    private SubSystemProxyCore subSystemProxyCore;
    private MoCore moCore;
    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.Sync#sync(com.yuep.nms.core.common.mocore.naming.MoNaming, java.util.List)
     */
    @Override
    public void sync(MoNaming target, List<SyncListener> listeners) {
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(target);
        MoManager moManager=subSystemProxy.getService(target, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        List<Mo> remoteNmsMos=new CopyOnWriteArrayList<Mo>(moManager.getChildrenMos(null, target));
        List<Mo> localNmsMos=new CopyOnWriteArrayList<Mo>(moCore.getChildrenMos(null, target));
  
        List<Mo> updatedMos=new ArrayList<Mo>();
        for(Mo remoteNmsMo:remoteNmsMos){
            if(localNmsMos.contains(remoteNmsMo)){
                updatedMos.add(remoteNmsMo);
                localNmsMos.remove(remoteNmsMo);
                remoteNmsMos.remove(remoteNmsMo);
            }
            
        }
        
        if(CollectionUtils.isNotEmpty(remoteNmsMos)){
            moCore.createMos(remoteNmsMos);
        }
        
        if(CollectionUtils.isNotEmpty(localNmsMos))
            moCore.deleteMos(localNmsMos);
        
        if(CollectionUtils.isNotEmpty(updatedMos)){
            moCore.updateMos(updatedMos);
        }
        
    }

}
