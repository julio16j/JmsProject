package Br.Jc.TesteJMS;

import java.io.StringWriter;
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
import javax.xml.bind.JAXB;

import Br.Jc.TesteJMS.modelo.Pedido;
import Br.Jc.TesteJMS.modelo.PedidoFactory;

/**
 * Hello world!
 *
 */
public class TesteProducerTopico 
{
    public static void main( String[] args ) throws Exception {
    	InitialContext context = new InitialContext(); 

        //imports do package javax.jms
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection("user","senha");
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);
		for (int i = 0; i < 1; i++) {
			Pedido pedido = new PedidoFactory().geraPedidoComValores();
//			StringWriter writer = new StringWriter();
//			JAXB.marshal(pedido, writer);
//			String xml = writer.toString();
			Message message = session.createObjectMessage(pedido);
			//message.setBooleanProperty("ebook", true);
			producer.send(message);	
		}        
        session.close();
        connection.close();
        context.close();
    }
}
