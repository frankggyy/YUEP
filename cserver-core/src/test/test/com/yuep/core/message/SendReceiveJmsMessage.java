package test.com.yuep.core.message;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.yuep.base.util.sys.SysUtils;
import com.yuep.core.message.def.BaseMessage;

/**
 * 
 * <p>
 * Title: SendReceiveJmsMessage
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-11-11 ÉÏÎç11:35:49
 * modified [who date description]
 * check [who date description]
 */
public class SendReceiveJmsMessage {

	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext(new String[]{
				"test//com//yuep//core//message//appctx-jms-subscriber.xml",
				"test//com//yuep//core//message//appctx-jms-publisher.xml"
		});
		
		DefaultMessageListenerContainer container=(DefaultMessageListenerContainer)ctx.getBean("defaultMessageListenerContainer");
		container.getDestination();
		
		
		SendMsg sendMsg=(SendMsg)ctx.getBean("sendMsg");
		BaseMessage message=new BaseMessage("system.msg","2");
		sendMsg.sendMessage("systemTopic", message);
		
		SysUtils.sleepNotException(1000000);
	}
}
