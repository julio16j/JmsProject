package Br.Jc.TesteJMS;

import java.util.ArrayList;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import org.apache.activemq.ActiveMQConnectionFactory;

import Br.Jc.TesteJMS.modelo.Pedido;

/**
 * Hello world!
 *
 */
public class TesteConsumerTopicoComercial 
{
    public static void main( String[] args ) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        ArrayList<String> listaPacotes = new ArrayList<>();
        listaPacotes.add("Br.Jc.TesteJMS.modelo");
        listaPacotes.add("java");
        listaPacotes.add("sun");
        ((ActiveMQConnectionFactory) factory).setTrustedPackages(listaPacotes);
        Connection connection = factory.createConnection("user","senha");
        connection.setClientID("comercial");
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura-comercia");
		consumer.setMessageListener(new MessageListener(){

		    @Override
		    public void onMessage(Message message){
		    	ObjectMessage objectMessage = (ObjectMessage) message;
		        try {
		        	Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.getCodigo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		});
        new Scanner(System.in).nextLine();
        session.close();
        connection.close();
        context.close();
    }
}
