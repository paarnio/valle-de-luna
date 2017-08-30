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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import siima.models.jaxb.caex.AppInfoEXTRAContentType;
import siima.models.jaxb.caex.AppInfoEXTRAContentType.WriterHeader;

public class XsAnyTypeSolver {
	
	private static final Logger logger=Logger.getLogger(XsAnyTypeSolver.class.getName());
	
	/* NEW
	 * parseAnyTypeContent() 
	 * TODO: Testing method here
	 * Moved from JaxbContainer
	 */
	public static String parseAnyTypeContent(String parentObjectType, Object parentNodeObject, String anyTypePropertyName,
			int propOrder) {
		/*
		 * Parameters: parentObjectType is JAXB type Note: propOrder not in use.
		 * If several 'anyTypePropertyName' elements, select the one with in
		 * order propOrder (>=1) 
		 * 
		 */
		
		logger.log(Level.INFO, "parseAnyTypeContent() parentObjectType: " + parentObjectType
				+ " & anyTypePropertyName: " + anyTypePropertyName);
		String content = null;

		if ("AttributeType".equals(parentObjectType)) {
			/*
			 * AttributeType Order| Property
			 * 
			 * @XmlElement(name = "DefaultValue") 1. protected Object
			 * defaultValue;
			 * 
			 * @XmlElement(name = "Value") 2. protected Object value;
			 */
			//REMOVED cLASS PREFIX:XsAnyTypeSolver.
			//content = XsAnyTypeSolver.getAnyTypeStringContent(parentNodeObject, anyTypePropertyName); // NEW
			content = getAnyTypeStringContent(parentNodeObject, anyTypePropertyName);
			logger.log(Level.INFO,
					"parseAnyTypeContent() AttributeType/" + anyTypePropertyName + " String value: " + content);

		} else if ("CAEXBasicObject".equals(parentObjectType)) {
			/*
			 * CAEXFile: ------ AdditionalInformation -------- (e.g.
			 * common/caex/caex_lego/Lego_example_mod2.aml) 2017-06-01 TOIMII
			 * ------------------------------------------------------------
			 * 
			 * @XmlRootElement(name = "CAEXFile") public class CAEXFile extends
			 * CAEXBasicObject
			 * 
			 * public class CAEXBasicObject {
			 * 
			 * @XmlElement(name = "Description") protected
			 * CAEXBasicObject.Description description;
			 * 
			 * @XmlElement(name = "Version") protected CAEXBasicObject.Version
			 * version;
			 * 
			 * @XmlElement(name = "Revision") protected
			 * List<CAEXBasicObject.Revision> revision;
			 * 
			 * @XmlElement(name = "Copyright") protected
			 * CAEXBasicObject.Copyright copyright;
			 * 
			 * @XmlElement(name = "AdditionalInformation") protected
			 * List<Object> additionalInformation;
			 * 
			 * @XmlAttribute(name = "ChangeMode") protected ChangeMode
			 * changeMode;
			 * 
			 */

			StringBuffer addinfobuf = new StringBuffer();
			//String strContent = XsAnyTypeSolver.getAnyTypeStringContent(parentNodeObject, anyTypePropertyName); // NEW
			String strContent = getAnyTypeStringContent(parentNodeObject, anyTypePropertyName);
			if (strContent != null) {

				logger.log(Level.INFO,
						"parseAnyTypeContent() AdditionalInformation has a String content: " + strContent);
				addinfobuf.append("\n----CAEXFile: ADDITIONAL INFORMATION-----------------");
				addinfobuf.append("\n" + strContent);
				addinfobuf.append("\n ----------------------------------------------------");
				content = addinfobuf.toString();

			} else {

				/*
				 * EXTRA content class defined for xs:anyType content container
				 */
				logger.log(Level.INFO, "parseAnyTypeContent(): AdditionalInformation has special object content:(AppInfoEXTRAContentType)!\n");
				AppInfoEXTRAContentType appInfoExtra = new AppInfoEXTRAContentType();
				appInfoExtra = (AppInfoEXTRAContentType) getAnyTypeElementContent(parentNodeObject,
						anyTypePropertyName, null, AppInfoEXTRAContentType.class);
				if (appInfoExtra != null) {
					WriterHeader header = appInfoExtra.getWriterHeader();

					if ((header != null)) {
						addinfobuf.append("\n----CAEXFile: ADDITIONAL INFORMATION-----------------");
						addinfobuf.append("\nINFO: WriterName: " + header.getWriterName());
						addinfobuf.append("\nINFO: getWriterID: " + header.getWriterID());
						addinfobuf.append("\nINFO: getWriterVendor: " + header.getWriterVendor());
						addinfobuf.append("\nINFO: getWriterVendorURL: " + header.getWriterVendorURL());
						addinfobuf.append("\nINFO: getWriterVersion: " + header.getWriterVersion());
						addinfobuf.append("\nINFO: getWriterRelease: " + header.getWriterRelease());
						addinfobuf.append("\nINFO: getLastWritingDateTime: " + header.getLastWritingDateTime());
						addinfobuf.append("\nINFO: getWriterProjectTitle: " + header.getWriterProjectTitle());
						addinfobuf.append("\nINFO: getWriterProjectID: " + header.getWriterProjectID());
						addinfobuf.append("\n-----------------------------------------------------");
						content = addinfobuf.toString();

					} else {
						System.out.println("parseAnyTypeContent: WriterHeader header is NULL: ");
					}
				} else {
					System.out.println("parseAnyTypeContent: appInfoExtra is NULL: ");
				}

			}
		}
		return content;
	}

	
	
	public static String getAnyTypeStringContent( Object parentNodeObject, String anyTypePropertyName){
		//NOTE: Returns only the first element's string value
		String strcontent=null;
		List<Object> content=null;
		List<List<Object>> allElementsContent = XsAnyTypeSolver.specialDomMarshal(parentNodeObject, anyTypePropertyName); //NEW
		if(allElementsContent.size()>0){ // Expecting only one Value
			content = allElementsContent.get(0); //First
		}
		if(content!=null){
		
		//System.out.println("=============== Content object: " + attValue.toString());
		String[] valueStruct =  content.toString().split(": ");
		String valuestring=null;
		if("[[#text".equals(valueStruct[0])){ // String content
			valuestring=valueStruct[1].split("]]")[0];
			logger.log(Level.INFO, "getAnyTypeStringContent() content string value: " + valuestring);
			strcontent = valuestring;
		} else { // Some Object content
			
			logger.log(Level.ERROR, "getAnyTypeStringContent() content unknown: " + content.toString());
		}
		} else {
			logger.log(Level.ERROR, "getAnyTypeStringContent() Attribute value content NULL ");
		}
		
		return strcontent;
	}
	
	

	
	public static <T> T getAnyTypeElementContent(Object parentNodeObject, String anyTypePropertyName,  Map<QName, String> attributes, Class<T> type){  
		/* NOTE: Returns only the first specified elements content
		 * CALL EXAMPLE:
		 * AppInfoEXTRAContentType appInfoExtra = new AppInfoEXTRAContentType();
		 * appInfoExtra = (AppInfoEXTRAContentType) XsAnyTypeSolver.getAnyTypeElementContent(parentNodeObject, anyTypePropertyName, null,
		 *					AppInfoEXTRAContentType.class);						
		 * WriterHeader header = appInfoExtra.getWriterHeader();
		 */
		
		List<Object> content=null;
		List<List<Object>> allElementsContent = XsAnyTypeSolver.specialDomMarshal(parentNodeObject, anyTypePropertyName); //NEW
		if(allElementsContent.size()>0){ // Expecting only one Value
			content = allElementsContent.get(0); //First
		}
		if(content!=null){
			
			return XsAnyTypeSolver.specialDomUnmarshal(content, attributes, type);
			
		} else 	return null;
	}
	
	/*  NEW SPECIAL MARSHAL METHOD  */
	public static List<List<Object>> specialDomMarshal(Object value, String childNodeName ) { 
		/* @Param Object value contains the xs:anyType object as it's child item.
		 * If several 'childNodeName' element matches: return object lists for all of them 
		 * i.e. List of Lists
		 * 
		 */
		logger.info("specialDomMarshal():");
	    try {
	    	 List<List<Object>> allanytyperesults = new ArrayList<List<Object>>();
	    	 List<Integer> child_node_indexes= new ArrayList<Integer>();
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
	
		        for (Integer indInt : child_node_indexes){
		        	int child_node_ind = indInt.intValue();
		        	anytypenode = nods.item(child_node_ind);		        
		        	NodeList nodes = anytypenode.getChildNodes();
		        	List<Object> oneanytyperesults = IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());
		        	allanytyperesults.add(oneanytyperesults);
		        }

		        return allanytyperesults;
		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }
		}

	

	
	/* SPECIAL UNMARSHAL METHOD (See Main2B.java)*/
	public static <T> T specialDomUnmarshal(List<Object> content, Map<QName, String> attributes, Class<T> type) {
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
