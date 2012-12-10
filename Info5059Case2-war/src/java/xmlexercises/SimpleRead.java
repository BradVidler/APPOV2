package xmlexercises;

/**
 * SimpleReadXML.java
 *	- an example of using the StAX parser to read an existing XML document.
 */
// StAX
import java.io.FileInputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class SimpleRead {

    public SimpleRead() {
    }

    public static void main(String argv[]) {
        // QName stands for Qualified Name
        QName qNode = new QName("Cost");
        QName qAttribute = new QName("currency");
        String filename = "c:\\temp\\some.xml";

        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new stream Reader
            XMLStreamReader parser = inputFactory.createXMLStreamReader(new FileInputStream(filename));

            // Read the XML document
            while (true) {
                int event = parser.next();
                if (event == XMLStreamConstants.END_DOCUMENT) {
                    parser.close();
                    break;
                }

                if (event == XMLStreamConstants.START_ELEMENT) {

                    if (parser.getName().getLocalPart().equals(qNode.getLocalPart())) {
                        System.out.println("Node is =>" + qNode.getLocalPart());
                        System.out.println("Attribute is =>" + qAttribute.getLocalPart() +
                                           " and has a value of =>" +
                                           parser.getAttributeValue(null, qAttribute.getLocalPart()));
                        break;
                    }
                }
            }

            parser.close();
            
        } catch (Exception ex) {
            System.out.print("exception " + ex.getMessage());
        }
    }
}