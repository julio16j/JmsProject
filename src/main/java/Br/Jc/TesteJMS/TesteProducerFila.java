package Br.Jc.TesteJMS;

import java.util.Iterator;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Hello world!
 *
 */
public class TesteProducerFila 
{
    public static void main( String[] args ) throws Exception {
    	InitialContext context = new InitialContext(); 

        //imports do package javax.jms
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection("user","senha");
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("financeiro");
		MessageProducer producer = session.createProducer(fila);
		for (int i = 0; i < 1000; i++) {
			Message message = session.createTextMessage("<pedido><id>" + i +"</id></pedido>");
			producer.send(message);	
		}        
        session.close();
        connection.close();
        context.close();
    }
}
