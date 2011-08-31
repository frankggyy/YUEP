/**
 * 
 */
package test.com.yuep.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.yuep.core.message.def.BaseMessage;

/**
 * 
 * <p>
 * Title: MsgReceive
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-11-11 ÉÏÎç11:36:00
 * modified [who date description]
 * check [who date description]
 */
public class MsgReceive implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		try {
			ObjectMessage msg = (ObjectMessage) arg0;
	        Object target;
			target = msg.getObject();
			
	        BaseMessage msgBody = (BaseMessage) target;
			System.out.println(msgBody);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
