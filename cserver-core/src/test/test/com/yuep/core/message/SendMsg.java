/**
 * 
 */
package test.com.yuep.core.message;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.yuep.core.message.def.BaseMessage;

/**
 * 
 * <p>
 * Title: SendMsg
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-11-11 ÉÏÎç11:36:05
 * modified [who date description]
 * check [who date description]
 */
public class SendMsg {

	private JmsTemplate jmsTemplate;
    
    private Destination destination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
    
	public void sendMessage(String topicName, BaseMessage message) {
        if(message==null)
            return;
        sendMessage(message, destination);
    }
    
    private void sendMessage(final BaseMessage msg, Destination destination) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(msg);
            }
        });
    }
    
}
