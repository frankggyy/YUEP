/**
 * 
 */
package com.yuep.core.message.impl;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.YuepLog;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.container.def.CoreException;

/**
 * jms处理的工具类
 * @author sufeng
 *
 */
public class JmsHandleUtils {

	/**
	 * 获取一个jms topic/queue资源
	 * @param jndiName
	 * @param jndiTemplate
	 * @return
	 */
	public static Destination getDestination(String jndiName,JndiTemplate jndiTemplate){
		Destination destination=null;
		ClassLoader oldCls = Thread.currentThread().getContextClassLoader();
		try{
		    Module core = ModuleContext.getInstance().getCoreModule();
            Thread.currentThread().setContextClassLoader(core.getClass().getClassLoader());
			JndiObjectFactoryBean bean=new JndiObjectFactoryBean();
			bean.setJndiName(jndiName);
			bean.setJndiTemplate(jndiTemplate);
			bean.afterPropertiesSet();
			destination= (Destination) bean.getObject();
		}catch(Exception ex){
		    String info = ExceptionUtils.getCommonExceptionInfo(ex);
            getLogger().error("[error] "+info);
            throw new CoreException(CoreException.JMS_TOPIC_LOOKUP_FAILED,info);
		}finally{
            Thread.currentThread().setContextClassLoader(oldCls);
        }
		return destination;
	}
	
	/**
	 * 创建一个jms的 connection factory
	 * @param jndiName
	 * @param jndiTemplate
	 * @return
	 */
	public static ConnectionFactory getConnectionFactory(String jndiName,JndiTemplate jndiTemplate){
	    ConnectionFactory connectFactory=null;
	    ClassLoader oldCls = Thread.currentThread().getContextClassLoader();
        try{
            Module core = ModuleContext.getInstance().getCoreModule();
            Thread.currentThread().setContextClassLoader(core.getClass().getClassLoader());
            JndiObjectFactoryBean bean=new JndiObjectFactoryBean();
            bean.setJndiName(jndiName);
            bean.setJndiTemplate(jndiTemplate);
            bean.afterPropertiesSet();
            connectFactory= (ConnectionFactory) bean.getObject();
        }catch(Exception ex){
            String info = ExceptionUtils.getCommonExceptionInfo(ex);
            getLogger().error("[error] "+info);
            throw new CoreException(CoreException.JMS_CONNECTION_FACTORY_LOOKUP_FAILED,info);
        }finally{
            Thread.currentThread().setContextClassLoader(oldCls);
        }
        return connectFactory;
    }
	
	private static Logger getLogger(){
        return YuepLog.getDefaultLogger();
    }
	
}
