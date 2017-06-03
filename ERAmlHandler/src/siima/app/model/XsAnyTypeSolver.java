/* XsAnyTypeSolver.java
 * 
 * Solver based on ideas in:
 * https://stackoverflow.com/questions/36901915/jax-ws-jaxb-and-unmarshalling-mixed-xsanytype
 * 
 * See also: ./doc/NOTES_xsAnyType.txt
 * Tested in egit-project JAXBExamples (siima): Main2B.java
 * IN USE: SPECIAL MARSHAL & UNMARSHAL METHODs 
 * SPECIAL MARSHAL METHOD NEEDED ESPECIALLY IN: used in MIXED content anyType objects See Main4B.java) 
 */

package siima.app.model;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XsAnyTypeSolver {
	
	private static final Logger logger=Logger.getLogger(JaxbContainer.class.getName());
	
	/* SPECIAL MARSHAL METHOD (See Main2B.java)  */
	public static List<Object> marshal(Object value, int child_item_nr ) { 
		/* @Param Object value contains the xs:anyType object as it's child item.
		 * (e.g. if the order child_item_ord = 3, the third child is the anyType item).
		 * If the Object value itself is the anyType object, set child_item_nr = 0 (??)
		 * (TODO: is the last option possible? Not tested).
		 * 
		 */
		logger.info("marshal()");
	    try {
	    	 Node anytypenode = null;
	    	 
	    	Class<?> type = value.getClass();
	        //System.out.println("Type before: " + type.getName());
	        if (type.isAnonymousClass())
	            type = type.getSuperclass();
	        //System.out.println("Type after: " + type.getName());
	        
	        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        Marshaller marshaller = JAXBContext.newInstance(type).createMarshaller(); 
	        marshaller.marshal(new JAXBElement<>(QName.valueOf("root"), Object.class, value), document); 
	               
	        NodeList nods = document.getDocumentElement().getChildNodes();
	        for (int i =0; i< nods.getLength(); i++){
	        	Node node = nods.item(i);
	        	logger.info("marshal() Child node nr(" + (i+1) + ") is " + node.getNodeName());
	        }
	       	       
	        if((child_item_nr>0)&&(child_item_nr<= nods.getLength())){ //One of the children is the anyType object
	        	anytypenode = nods.item(child_item_nr-1);
	        } else { //the value object is the anyType object itself (DO NOTHING)
	        	//anytypenode = document.getDocumentElement();
	        	return null;
	        }
	        NodeList nodes = anytypenode.getChildNodes();
	        		
	        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	/* SPECIAL UNMARSHAL METHOD (See Main2B.java)*/
	public static <T> T unmarshal(List<Object> content, Map<QName, String> attributes, Class<T> type) {
		logger.info("marshal()");
	    try {
	        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        document.appendChild(document.createElement("root"));

	        if (attributes != null)
	            attributes.forEach((q, s) -> document.getDocumentElement().setAttribute(q.getLocalPart(), s));

	        if (content != null)
	            content.forEach(o -> document.getDocumentElement().appendChild(document.importNode((Node) o, true)));

	        Unmarshaller unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
	        return unmarshaller.unmarshal(document, type).getValue();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
