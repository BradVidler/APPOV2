package xmlexercises;

//StAX
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import javax.xml.stream.XMLOutputFactory;

public class BuildCardXML {
    
    public byte[] buildXML() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = null;
        
        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            writer = xof.createXMLStreamWriter(out);
            writer.writeStartDocument("1.0");
            writer.writeStartElement("cards"); //write root
            writer.writeEmptyElement("card"); //start 1st card
            writer.writeAttribute("name", "John Doe");
            writer.writeAttribute("title", "CEO, Widget Inc.");
            writer.writeAttribute("email", "john.doe@widget.com");
            writer.writeAttribute("phone", "(202) 555-1414");
            writer.writeAttribute("logo", "widget1.gif");
            
            writer.writeEmptyElement("card"); //start 2nd card
            writer.writeAttribute("name", "Jane Doe");
            writer.writeAttribute("title", "CEO, Widgets Are Us");
            writer.writeAttribute("email", "jane.doe@widget.com");
            writer.writeAttribute("phone", "(202) 555-6666");
            writer.writeAttribute("logo", "widget2.gif");
            
            writer.writeEmptyElement("card"); //start 2nd card
            writer.writeAttribute("name", "Brad Vidler");
            writer.writeAttribute("title", "CEO, Fate Solutions");
            writer.writeAttribute("email", "brad@fateolutions.com");
            writer.writeAttribute("phone", "(519) 871-5719");
            writer.writeAttribute("logo", "xxf8xx.jpg");
            
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        
        return out.toByteArray();
    }
    
}
