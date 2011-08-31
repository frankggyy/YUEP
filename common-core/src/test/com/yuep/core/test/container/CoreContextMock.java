/*
 * $Id: CoreContextMock.java, 2011-5-10 ÉÏÎç09:07:16 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.test.container;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: CoreContextMock
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 ÉÏÎç09:07:16
 * modified [who date description]
 * check [who date description]
 */
public class CoreContextMock extends CoreContext {

    private LocalCommunicationObjectMock localCommunicationObjectMock=new LocalCommunicationObjectMock();
    private Map<RemoteProperty,RemoteCommunicationObjectMock> remoteCommunicationObjectMocks=new ConcurrentHashMap<RemoteProperty, RemoteCommunicationObjectMock>();
    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#getPropertiesManager()
     */
    @Override
    public ContainerPropertiesManager getPropertiesManager() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#getSelfMessageMetadata()
     */
    @Override
    public MessageMetadata getSelfMessageMetadata() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#local()
     */
    @Override
    public CommunicateObject local() {
        return localCommunicationObjectMock;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#publish(java.lang.String, java.io.Serializable)
     */
    @Override
    public void publish(String messageName, Serializable messageInfo) {
        localCommunicationObjectMock.notifyLocalMessage(messageName, messageInfo);
        for(RemoteCommunicationObjectMock remoteCommunicationObjectMock:remoteCommunicationObjectMocks.values()){
            remoteCommunicationObjectMock.notifyLocalMessage(messageName, messageInfo);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#remote(java.lang.String, int)
     */
    @Override
    public RemoteCommunicateObject remote(String ip, int port) {
        RemoteProperty remoteProperty=new RemoteProperty(ip,port);
        if(remoteCommunicationObjectMocks.containsKey(remoteProperty))
            return remoteCommunicationObjectMocks.get(remoteProperty);
        RemoteCommunicationObjectMock remoteCommunicationObjectMock=new RemoteCommunicationObjectMock(ip,port);
        remoteCommunicationObjectMocks.put(remoteProperty, remoteCommunicationObjectMock);
        return remoteCommunicationObjectMock;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#setLocalService(java.lang.String, java.lang.Class, java.lang.Object)
     */
    @Override
    public void setLocalService(String serviceName, Class<?> interfaceClass,Object serviceInstance) {
        localCommunicationObjectMock.setLocalService(serviceName, interfaceClass, serviceInstance);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CoreContext#setRemoteService(java.lang.String, java.lang.Class, java.lang.Object)
     */
    @Override
    public void setRemoteService(String serviceName, Class<?> interfaceClass,Object serviceInstance) {
    }
    
    
    private class RemoteProperty {
        
        private String ip;
        private int port;
        
        public RemoteProperty(String ip,int port){
            this.ip=ip;
            this.port=port;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public int getPort() {
            return port;
        }
        public void setPort(int port) {
            this.port = port;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((ip == null) ? 0 : ip.hashCode());
            result = prime * result + port;
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RemoteProperty other = (RemoteProperty) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (ip == null) {
                if (other.ip != null) {
                    return false;
                }
            } else if (!ip.equals(other.ip)) {
                return false;
            }
            if (port != other.port) {
                return false;
            }
            return true;
        }
        private CoreContextMock getOuterType() {
            return CoreContextMock.this;
        }
        
        
        
    }

    @Override
    public void unregisterLocalService(String serviceName, Class<?> serviceItf) {
        
    }

    @Override
    public void unregisterRemoteService(String serviceName, Class<?> serviceItf) {
        
    }

}
