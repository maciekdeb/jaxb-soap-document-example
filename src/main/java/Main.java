import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by maciek on 10.07.15.
 */
public class Main {

    public static void main(String[] args) throws ParserConfigurationException, JAXBException, SOAPException, IOException {

        Customer customer = new Customer();
        customer.setId(0);
        customer.setFirstName("maciej");
        customer.setAge(24);

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Marshaller marshaller = JAXBContext.newInstance(Customer.class).createMarshaller();
        marshaller.marshal(customer, document);

        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
        soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("soap", "http://www.w3.org/2001/12/soap-envelope");
        soapMessage.getSOAPPart().getEnvelope().setPrefix("soap");
        soapMessage.getSOAPHeader().setPrefix("soap");
        soapMessage.getSOAPBody().setPrefix("soap");
        soapMessage.getSOAPBody().addDocument(document);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        soapMessage.writeTo(outputStream);
        String output = new String(outputStream.toByteArray());

        System.out.println(output);
    }

}
