package com.yuep.core.test.container;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

public class RemoteCommunicationObjectMock implements RemoteCommunicateObject {

    private String remoteIp;
    private int port;
    private Map<String,List<MessageReceiver>> remoteMessageReceivers=new ConcurrentHashMap<String,List<MessageReceiver>>();
    public RemoteCommunicationObjectMock(String remoteIp,int port){
        this.remoteIp=remoteIp;
        this.port=port;
    }
    @Override
    public void cleanup() {
    }

    @Override
    public int getLinkStatus() {
        return 1;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.RemoteCommunicateObject#getRemoteIp()
     */
    @Override
    public String getRemoteIp() {
        return remoteIp;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.RemoteCommunicateObject#getRemoteServerPort()
     */
    @Override
    public int getRemoteServerPort() {
        return port;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.RemoteCommunicateObject#resetRemoteServer(java.lang.String, int)
     */
    @Override
    public void resetRemoteServer(String ip, int port) {
        this.remoteIp=ip;
        this.port=port;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#getService(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T getService(String serviceName, Class<T> serviceItf) {
        return null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#subscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void subscribe(String name, MessageReceiver receiver) {
        if(!remoteMessageReceivers.containsKey(name)){
            remoteMessageReceivers.put(name, new CopyOnWriteArrayList<MessageReceiver>());
        }
        remoteMessageReceivers.get(name).add(receiver);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#unsubscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void unsubscribe(String name, MessageReceiver receiver) {
        if(!remoteMessageReceivers.containsKey(name))
            return;
        remoteMessageReceivers.get(name).remove(receiver);
    }
    
    
    public void notifyLocalMessage(String messageName, Serializable messageInfo){
        List<MessageReceiver> messageReceivers=remoteMessageReceivers.get(messageName);
        for(MessageReceiver messageReceiver:messageReceivers){
            messageReceiver.receive(this, messageName, messageInfo);
        }
     }

}
