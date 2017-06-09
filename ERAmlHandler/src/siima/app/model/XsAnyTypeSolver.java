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
import java.util.ArrayList;
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
	
	private static final Logger logger=Logger.getLogger(XsAnyTypeSolver.class.getName());
	
	/* SPECIAL MARSHAL METHOD (See Main2B.java)  */
	public static List<Object> marshal(Object value, String childNodeName, int child_item_nr ) { 
		/* @Param Object value contains the xs:anyType object as it's child item.
		 * If several 'childNodeName' elements, select the one with in order propOrder (>=1)
		 * (e.g. if the order child_item_ord = 3, the third child is to be selected, if it has childNodeName ).
		 * 
		 */
		logger.info("marshal()");
	    try {
	    	 List<Integer> child_node_indexes= new ArrayList<Integer>();
	    	 int child_node_ind=-1;
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
	        	if(node.getNodeName().equalsIgnoreCase(childNodeName)){
	        		child_node_indexes.add(new Integer(i));
	        		//System.out.println("================MATCH child_node_index value " + i);
	        	}
	        }
	        Integer tmp;
	        //System.out.println("================child_node_indexes.size() = " + child_node_indexes.size());
	       	if(child_node_indexes.size()==1){
	       		tmp =child_node_indexes.get(0);
	       		System.out.println("================tmp = " + tmp);
	       		child_node_ind= tmp.intValue();	       		
	       	} else if((child_node_indexes.size()>=child_item_nr)&&(child_item_nr>0)){
	       		tmp=child_node_indexes.get(child_item_nr-1);
	       		child_node_ind= tmp.intValue();
	       	} else if((child_node_indexes.size()<child_item_nr)&&(child_item_nr>0)){
	       		tmp=child_node_indexes.get(child_node_indexes.size()-1);
	       		child_node_ind= tmp.intValue();
	       	}
	       	
	       	//System.out.println("================child_node_ind= " + child_node_ind);
	        //if((child_item_nr>0)&&(child_item_nr<= nods.getLength())){ //One of the children is the anyType object
	        //	anytypenode = nods.item(child_item_nr-1);
	       
	        if((child_node_ind>=0)&&(child_node_ind<nods.getLength())){ //One of the children is the anyType object
		        	anytypenode = nods.item(child_node_ind);
	        } else { //the value object is the anyType object itself (DO NOTHING)
	        	//anytypenode = document.getDocumentElement();
	        	return null;
	        }
	        //if(anytypenode!=null)System.out.println("================anytypenode " + anytypenode.toString());
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
