/* JaxbContainer.java
 * 
 * PROBLEM: XML elements with type xs:anyType are bind to java Object class, 
 * which does not allow data access. How to customize binding of these types???
 * SAMA ONGELMA:
 * http://stackoverflow.com/questions/40338721/xsdanytype-to-java-object
 * http://markmail.org/message/tjswjuamqdekkwsy
 * 
 * MY TEMPORARY SOLUTION: generating java caex classes from a modified schema, where
 * xs:anyType is replaced with xs:string
 * 
 */

package siima.app.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.models.jaxb.caex.AppInfoEXTRAContentType;
import siima.models.jaxb.caex.AppInfoEXTRAContentType.WriterHeader;
import siima.models.jaxb.caex.AttributeType;
import siima.models.jaxb.caex.CAEXBasicObject;
//import siima.models.jaxb.caex.AttributeType.AttributeValueInterface;
import siima.models.jaxb.caex.CAEXBasicObject.Copyright;
import siima.models.jaxb.caex.CAEXBasicObject.Description;
import siima.models.jaxb.caex.CAEXBasicObject.Revision;
import siima.models.jaxb.caex.CAEXBasicObject.Version;
import siima.models.jaxb.caex.CAEXFile;
import siima.models.jaxb.caex.CAEXFile.ExternalReference;
import siima.models.jaxb.caex.CAEXFile.InstanceHierarchy;
import siima.models.jaxb.caex.CAEXFile.InterfaceClassLib;
import siima.models.jaxb.caex.CAEXFile.RoleClassLib;
import siima.models.jaxb.caex.CAEXFile.SystemUnitClassLib;
import siima.models.jaxb.caex.CAEXObject;
import siima.models.jaxb.caex.ChangeMode;
import siima.models.jaxb.caex.InterfaceClassType;
import siima.models.jaxb.caex.InterfaceFamilyType;
import siima.models.jaxb.caex.InternalElementType;
import siima.models.jaxb.caex.InternalElementType.RoleRequirements;
import siima.models.jaxb.caex.RoleClassType.ExternalInterface;
import siima.models.jaxb.caex.RoleFamilyType;
import siima.models.jaxb.caex.SystemUnitClassType;
import siima.models.jaxb.caex.SystemUnitClassType.InternalLink;
import siima.models.jaxb.caex.SystemUnitClassType.SupportedRoleClass;
import siima.models.jaxb.caex.SystemUnitFamilyType;
import siima.models.jaxb.caex.TEMP_Helpper;
//import siima.models.jaxb.caex.WriterHeader;
import siima.app.control.MainAppController;
//import siima.models.jaxb.caex.WriterHeader;
import siima.app.model.tree.ElementNode;

public class JaxbContainer {
	private static final Logger logger=Logger.getLogger(JaxbContainer.class.getName());
	//Modified schema path: configure/schema/caex_2.1.5_modified/CAEX_V2.15_modified.xsd
	public static String CAEX_SCHEMA ="configure/schema/caex_2.1.5_orig/CAEX_ClassModel_V2.15.xsd";
	private String validationSchemaFile; //can be set by menu/configuration

	public Path mainFilePath; //latest loaded caex file
	public List<Path> loadedCaexFilePaths; //all loaded caex files
	public Object caexRootObject;
	
	public ElementNode ieRootElement = new ElementNode("CAEXFile:InternalElements");
	public ElementNode suclRootElement = new ElementNode("CAEXFile:SysUnitClassLibraries");
	public ElementNode roleclRootElement = new ElementNode("CAEXFile:RoleClassLibraries");
	public ElementNode ifaceclRootElement = new ElementNode("CAEXFile:InterfaceClassLibraries");
	

	public ElementNode getIfaceclRootElement() {
		return ifaceclRootElement;
	}

	public ElementNode getRoleclRootElement() {
		return roleclRootElement;
	}

	public ElementNode getSuclRootElement() {
		return suclRootElement;
	}

	public ElementNode getIeRootElement() {
		return ieRootElement;
	}

	public void setIeRootElement(ElementNode rootElement) {
		this.ieRootElement = rootElement;
	}
	

	public void clearRootElements(){
		//TODO: to be called when new project is created
		ieRootElement = new ElementNode("CAEXFile:InternalElements");
		suclRootElement = new ElementNode("CAEXFile:SysUnitClassLibraries");
		roleclRootElement = new ElementNode("CAEXFile:RoleClassLibraries");
		ifaceclRootElement = new ElementNode("CAEXFile:InterfaceClassLibraries");
		
		logger.info("clearElementTree() all root Elements cleared!");
	}
	

	public ElementNode buildElementGraphFromXML() {
		List<ElementNode> ichildren = new ArrayList<ElementNode>();
		List<ElementNode> caexSuclchildren = new ArrayList<ElementNode>();
		List<ElementNode> caexRoleclchildren = new ArrayList<ElementNode>();
		List<ElementNode> caexIfaceclchildren = new ArrayList<ElementNode>();

		CAEXFile caex = (CAEXFile) caexRootObject;
		//System.out.println("== JaxbContainer:CAEXFile:" + caex.getFileName());
		logger.info("buildElementGraphFromXML() CAEXFile:" + caex.getFileName());

		/* --------CAEXFile content objects  --------*/
		/* CAEXFile: AdditionalInformation (xs.anyType) 
		 * (TOIMII e.g. by loading Lego_example_mod2.aml (and using caex_2.1.5_orig_extended classes)
		 */
		CAEXBasicObject basicObject = (CAEXBasicObject)caex;
		CAEXBasicObject newbasic = TEMP_Helpper.insertCopyContent(basicObject); 
				
		String content = parseAnyTypeContent("CAEXBasicObject", newbasic, "additionalInformation", 1 );	
		//List<Object> addInfoList = caex.getAdditionalInformation();
		//System.out.println("========== CAEX ADD INFO: " + addInfoList.toString());
		logger.info("buildElementGraphFromXML() CAEXFile:AdditionalInformation (1):" + content);
		
		
		//ChangeMode changeMode = caex.getChangeMode();
		Copyright cright = caex.getCopyright();
		Description description = caex.getDescription();
		String schemaVersion = caex.getSchemaVersion();
		Version version = caex.getVersion();

		// Caex object lists
		List<InstanceHierarchy> instHList = caex.getInstanceHierarchy();
		List<SystemUnitClassLib> systemUnitClassLibList = caex.getSystemUnitClassLib();
		List<RoleClassLib> roleClassLibList = caex.getRoleClassLib();
		List<InterfaceClassLib> interfaceClassLibList = caex.getInterfaceClassLib();
		List<ExternalReference> extRefList = caex.getExternalReference();
		List<Revision> revisionList = caex.getRevision();

		/* Adding InstanceHierarchy into the ElementNode tree */
		if ((instHList != null) && (!instHList.isEmpty())) {
			logger.info("CAEXFile:instanceHierarchyList");
			for (InstanceHierarchy ih : instHList) {
				logger.info("CAEXFile:instanceHierarchy name:" + ih.getName());
				ElementNode ihierarchy = new ElementNode("IH:" + ih.getName());
				ihierarchy.setJaxbObject(ih);
				ihierarchy.setNodetype("InstanceHierarchy");
				ichildren.add(ihierarchy);
				List<InternalElementType> internals = ih.getInternalElement();
				// RECURSIVE CALL:
				parseInternalElementsRecursion(ihierarchy, null, internals, 0);

			}
			ElementNode.linkChildren(ieRootElement, ichildren);
		}

		/*
		 * Creating SystemUnitClassLib tree into another the ElementNode tree
		 */

		if ((systemUnitClassLibList != null) && (!systemUnitClassLibList.isEmpty())) {
			//System.out.println("== TEST: JaxbContainer:CAEXFile:systemUnitClassLibList");
			logger.info("CAEXFile:systemUnitClassLibList");
			for (SystemUnitClassLib sucl : systemUnitClassLibList) {
				//System.out.println("== TEST: JaxbContainer:SystemUnitClassLib name:" + sucl.getName());
				logger.info("CAEXFile:SystemUnitClassLib name:" + sucl.getName());
				ElementNode suchierarchy = new ElementNode("SUCLIB:" + sucl.getName());
				suchierarchy.setJaxbObject(sucl);
				suchierarchy.setNodetype("SystemUnitClassLib");
				caexSuclchildren.add(suchierarchy);
				List<SystemUnitFamilyType> suftypes = sucl.getSystemUnitClass();
				// RECURSIVE CALL:
				parseSystemUnitFamilyTypeRecursion(suchierarchy, null, suftypes, 0);

			}
			ElementNode.linkChildren(suclRootElement, caexSuclchildren);
		}

		/*
		 * Creating RoleClassLib tree into another the ElementNode tree
		 */

		if ((roleClassLibList != null) && (!roleClassLibList.isEmpty())) {
			//System.out.println("== TEST: JaxbContainer:CAEXFile:roleClassLibList");
			logger.info("CAEXFile:roleClassLibList");
			for (RoleClassLib roleclib : roleClassLibList) {
				//System.out.println("== TEST: JaxbContainer:RoleClassLib name:" + roleclib.getName());
				logger.info("CAEXFile:RoleClassLib name:" + roleclib.getName());
				ElementNode roleclasshierarchy = new ElementNode("RCLIB:" + roleclib.getName());
				roleclasshierarchy.setJaxbObject(roleclib);
				roleclasshierarchy.setNodetype("RoleClassLib");
				caexRoleclchildren.add(roleclasshierarchy);
				
				List<RoleFamilyType> roleftypes = roleclib.getRoleClass();
				// RECURSIVE CALL:
				parseRoleFamilyTypeRecursion(roleclasshierarchy, null, roleftypes, 0);

			}
			ElementNode.linkChildren(roleclRootElement, caexRoleclchildren);
		}

		/*
		 * TODO: Creating InterfaceClassLib tree into another ElementNode tree
		 */

		if ((interfaceClassLibList != null) && (!interfaceClassLibList.isEmpty())) {
			//System.out.println("== TEST: JaxbContainer:CAEXFile:interfaceClassLibList");
			logger.info("CAEXFile:interfaceClassLibList");
			for (InterfaceClassLib ifaceclib : interfaceClassLibList) {
				//System.out.println("== TEST: JaxbContainer:InterfaceClassLib name:" + ifaceclib.getName());
				logger.info("CAEXFile:InterfaceClassLib name:" + ifaceclib.getName());
				ElementNode ifaceclasshierarchy = new ElementNode("IFaceCLIB:" + ifaceclib.getName());
				ifaceclasshierarchy.setJaxbObject(ifaceclib);
				ifaceclasshierarchy.setNodetype("InterfaceClassLib");
				caexIfaceclchildren.add(ifaceclasshierarchy);
							
				List ifaceftypes = ifaceclib.getInterfaceClass();
				// RECURSIVE CALL:
				parseCAEXObjectTypesRecursion(ifaceclasshierarchy, null, ifaceftypes, 0);

			}
			ElementNode.linkChildren(ifaceclRootElement, caexIfaceclchildren);
		}
		return ieRootElement;
	}
	
	/**
	 * TODO: Recursive search of nested InterfaceFamilyType (Or CAEXObjects in general) and Element tree
	 * building
	 * 
	 * @return
	 */

	public boolean parseCAEXObjectTypesRecursion(ElementNode parentNode, CAEXObject jaxbParent,
			List<CAEXObject> parentsCaexObjectTypes, int level) {
		// Used for parsing InterfaceFamilyType hierarchy
		
		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {
			if(InterfaceFamilyType.class.isInstance(jaxbParent)){
				InterfaceFamilyType ifaceParent = (InterfaceFamilyType)jaxbParent;
				List<AttributeType> attchildrenlist=ifaceParent.getAttribute();
				ok = parseAttributeTypeRecursion(parentNode, null, attchildrenlist, level);
			}
		}
		// RECURSIVE CALL: CAEX PARENT'S CAEXOBJECT -TYPE CHILDREN 
		
		if ((parentsCaexObjectTypes != null) && (!parentsCaexObjectTypes.isEmpty())) {
			InterfaceFamilyType iface=null;
			
			for (CAEXObject caexo : parentsCaexObjectTypes) {
				List<CAEXObject> caexoChildrenlist = new ArrayList<CAEXObject>();;
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling
				//		+ ") parseCAEXObjectTypesRecursion(): CAEXObject @Name:" + caexo.getName());
					
				ElementNode kid = new ElementNode("IfaceC:" + caexo.getName());
				kid.setJaxbObject(caexo);
				if(InterfaceFamilyType.class.isInstance(caexo)){
					kid.setNodetype("InterfaceFamilyType");
					iface = (InterfaceFamilyType)caexo;
					// Deeper to children of this child element
					List ifaceChildren = iface.getInterfaceClass();
					caexoChildrenlist.addAll(ifaceChildren);
				}
				
				children.add(kid);
				// Recursive call
				ok = parseCAEXObjectTypesRecursion(kid, iface, caexoChildrenlist, level);

			}

		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}
	
	
	/**
	 * Recursive search of nested AttributeTypes and Element tree
	 * building
	 * 
	 * @return
	 */

	public boolean parseAttributeTypeRecursion(ElementNode parentNode, AttributeType jaxbParent,
			List<AttributeType> parentsAttributeTypes, int level) {
		
		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {
			
			// SEE: getBasicInfo()
			// Object value = jaxbParent.getValue();
			//if (value != null) System.out.println("JaxbContainer: ATTRIBUTE VALUE: " + value.toString());			
			
		}
		// RECURSIVE CALL: CAEX PARENT'S ATTRIBUTE -TYPE CHILDREN (ie.
		// SystemUnitClasses)
		if ((parentsAttributeTypes != null) && (!parentsAttributeTypes.isEmpty())) {
			for (AttributeType att : parentsAttributeTypes) {
				boolean istype = AttributeType.class.isInstance(att);
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling
				//		+ ") parseAttributeTypeRecursion(): AttributeType @Name:" + att.getName()
				//		+ " is AttributeType:" + istype);

				ElementNode kid = new ElementNode("ATT:" + att.getName());
				kid.setJaxbObject(att);
				kid.setNodetype("AttributeType");
				children.add(kid);

				// Deeper to children of this child element
				List<AttributeType> attchildrenlist = att.getAttribute();

				// Recursive call
				ok = parseAttributeTypeRecursion(kid, att, attchildrenlist, level);

			}

		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}
	
	/**
	 * Recursive hierarchy search of SystemUnitFamilyTypes and Element tree
	 * building
	 * 
	 * @return
	 */

	public boolean parseRoleFamilyTypeRecursion(ElementNode parentNode, RoleFamilyType jaxbParent,
			List<RoleFamilyType> parentsRoleFamilyTypes, int level) {
		// NOTE: After first round jaxbParent is always of RoleFamilyType
		// NOTE: RoleFamilyType object can have RoleFamilyType
		// children

		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {
			// CAEX ATTRIBUTES
			List<AttributeType> attributeList = jaxbParent.getAttribute();
			if ((attributeList != null) && (!attributeList.isEmpty())) {
				
				parseAttributeTypeRecursion(parentNode, null, attributeList, 0);
			}
			//TODO:
			List<ExternalInterface> extInterfaces = jaxbParent.getExternalInterface();
			String refBCPath = jaxbParent.getRefBaseClassPath();
			
			/*if (internals != null) {
				System.out.println("--JaxbContainer:SystemUnitFT has List<InternalElementType> #:" + internals.size());
				// RECURSIVE call for InternalElements
				parseInternalElementsRecursion(parentNode, null, internals, 0);
			}*/

		}

		// RECURSIVE CALL: CAEX PARENT'S ROLE-FAMILY-TYPE CHILDREN (ie.
		// SystemUnitClasses)
		if ((parentsRoleFamilyTypes != null) && (!parentsRoleFamilyTypes.isEmpty())) {
			for (RoleFamilyType rolef : parentsRoleFamilyTypes) {
				boolean istype = RoleFamilyType.class.isInstance(rolef);
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling
				//		+ ") parseRoleFamilyTypeRecursion(): RoleFamilyType @Name:" + rolef.getName()
				//		+ " is RoleFamilyType:" + istype);

				ElementNode kid = new ElementNode("ROLEFT:" + rolef.getName());
				kid.setJaxbObject(rolef);
				kid.setNodetype("RoleFamilyType");
				children.add(kid);
				// Deeper to children of this child element
				List<RoleFamilyType> rolefchildrenlist = rolef.getRoleClass();
				// Recursive call
				ok = parseRoleFamilyTypeRecursion(kid, rolef, rolefchildrenlist, level);
			}
		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}

	/**
	 * Recursive hierarchy search of SystemUnitFamilyTypes and Element tree
	 * building
	 * 
	 * @return
	 */

	public boolean parseSystemUnitFamilyTypeRecursion(ElementNode parentNode, SystemUnitFamilyType jaxbParent,
			List<SystemUnitFamilyType> parentsSystemUnitFamilyTypes, int level) {
		// NOTE: After first round jaxbParent is always of SystemUnitFamilyType
		// NOTE: SystemUnitFamilyType object can have SystemUnitFamilyType
		// children
		// AND InternalElementType children
		// => we call both recursive methods:
		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {

			// CAEX ATTRIBUTES
			List<AttributeType> attributeList = jaxbParent.getAttribute();
			if ((attributeList != null) && (!attributeList.isEmpty())) {
				
				parseAttributeTypeRecursion(parentNode, null, attributeList, 0);
			}

			List<InternalElementType> internals = jaxbParent.getInternalElement();
			if (internals != null) {
				//System.out.println("--JaxbContainer:SystemUnitFT has List<InternalElementType> #:" + internals.size());
				// RECURSIVE call for InternalElements
				parseInternalElementsRecursion(parentNode, null, internals, 0);
			}

		}

		// RECURSIVE CALL: CAEX PARENT'S SYSTEM-UNIT-FAMILY-TYPE CHILDREN (ie.
		// SystemUnitClasses)
		if ((parentsSystemUnitFamilyTypes != null) && (!parentsSystemUnitFamilyTypes.isEmpty())) {
			for (SystemUnitFamilyType suf : parentsSystemUnitFamilyTypes) {
				boolean istype = SystemUnitFamilyType.class.isInstance(suf);
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling
				//		+ ") parseSystemUnitFamilyTypeRecursion(): SystemUnitFamilyType @Name:" + suf.getName()
				//		+ " is SystemUnitFamilyType:" + istype);

				ElementNode kid = new ElementNode("SUFT:" + suf.getName());
				kid.setJaxbObject(suf);
				kid.setNodetype("SystemUnitFamilyType");
				children.add(kid);

				// Deeper to children of this child element
				List<SystemUnitFamilyType> sufchildrenlist = suf.getSystemUnitClass();

				// Recursive call
				ok = parseSystemUnitFamilyTypeRecursion(kid, suf, sufchildrenlist, level);

			}

		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}

	/**
	 * Recursive hierarchy search of InternalElementTypes and Element tree
	 * building
	 * 
	 * @return
	 */

	public boolean parseInternalElementsRecursion(ElementNode parentNode, InternalElementType jaxbParent,
			List<InternalElementType> parentsInternalElements, int level) {
		// NOTE: After first round parent is always of InternalElementType
		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {

			/*
			 * TODO String ie_id = jaxbParent.getID(); String name =
			 * jaxbParent.getName(); Description description =
			 * jaxbParent.getDescription(); String sysUnitPath =
			 * jaxbParent.getRefBaseSystemUnitPath(); RoleRequirements rolereqs
			 * = jaxbParent.getRoleRequirements(); List<SupportedRoleClass>
			 * suportedRoles = jaxbParent.getSupportedRoleClass();
			 */

			// CAEX EXT-INTERFACES
			List<InterfaceClassType> parentsExtInterfaces = jaxbParent.getExternalInterface();
			// System.out.println("");
			if ((parentsExtInterfaces != null) && (!parentsExtInterfaces.isEmpty())) {
				for (InterfaceClassType iface : parentsExtInterfaces) {
					// iface.getRefBaseClassPath();
					ElementNode parentsIfaceNode = new ElementNode("IFace:" + iface.getName());
					parentsIfaceNode.setJaxbObject(iface);
					parentsIfaceNode.setNodetype("InterfaceClassType");
					children.add(parentsIfaceNode);
				}
			}

			// CAEX ATTRIBUTES
			List<AttributeType> attributeList = jaxbParent.getAttribute();
			if ((attributeList != null) && (!attributeList.isEmpty())) {
				parseAttributeTypeRecursion(parentNode, null, attributeList, 0);
				
			}

			// CAEX PARENTS INTERNAL-LINKS
			List<InternalLink> parentsInternalLinks = jaxbParent.getInternalLink();
			if ((parentsInternalLinks != null) && (!parentsInternalLinks.isEmpty())) {
				for (InternalLink ilink : parentsInternalLinks) {
					ilink.getName();
					String partnerA = ilink.getRefPartnerSideA();
					String partnerB = ilink.getRefPartnerSideB();
					ElementNode parentslinkNode = new ElementNode("ILink:" + ilink.getName());
					parentslinkNode.setJaxbObject(ilink);
					parentslinkNode.setNodetype("InternalLink");
					children.add(parentslinkNode);
				}
			}

		}

		// RECURSIVE CALL: CAEX PARENT'S INTERNAL-ELEMENT CHILDREN
		if ((parentsInternalElements != null) && (!parentsInternalElements.isEmpty())) {
			for (InternalElementType ie : parentsInternalElements) {
				boolean istype = InternalElementType.class.isInstance(ie);
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling + ") parseInternalElements(): InternalElement @Name:"
				//		+ ie.getName() + " is InternalElementType:" + istype);

				ElementNode kid = new ElementNode("IE:" + ie.getName());
				kid.setJaxbObject(ie);
				kid.setNodetype("InternalElementType");
				children.add(kid);

				// Deeper to children of this child element
				List<InternalElementType> inter2 = ie.getInternalElement();
				// Recursive call
				ok = parseInternalElementsRecursion(kid, ie, inter2, level);

			}
		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}

	/**
	 * 
	 * @return
	 */
	public boolean loadData(Path path) {
		this.mainFilePath = path; //latest loaded caex file
		JAXBContext context;
		Schema schema;
		
		if(loadedCaexFilePaths==null) loadedCaexFilePaths = new ArrayList<Path>();
		loadedCaexFilePaths.add(path);
		
		try {
			context = JAXBContext.newInstance("siima.models.jaxb.caex");
			if (context == null) logger.log(Level.ERROR, "loadData() jaxb context is null");
				//System.out.println("VPA: JAXBContext context is null");

			Unmarshaller u = context.createUnmarshaller();
			//Validate against CAEX Schema
			 SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	            try {
	            	if(validationSchemaFile!=null){ // Configured schema 
	            		schema = sf.newSchema(new File(validationSchemaFile));
	            	} else { // Default schema
	            		schema = sf.newSchema(new File(CAEX_SCHEMA)); //
	            	}
	                u.setSchema(schema);
	                u.setEventHandler(
	                    new ValidationEventHandler() {
	                        // allow unmarshalling to continue even if there are errors
	                        public boolean handleEvent(ValidationEvent ve) {
	                        	StringBuffer msgbuff = new StringBuffer();
	                        	msgbuff.append("loadData():ValidationEventHandler()\n");
	                        	ValidationEventLocator vel = ve.getLocator();
	                        	msgbuff.append("Line:Col[" + vel.getLineNumber() +
	                                    ":" + vel.getColumnNumber() +
	                                    "]:" + ve.getMessage());
	                            // ignore warnings
	                            if (ve.getSeverity() != ValidationEvent.WARNING) {
	                                logger.log(Level.ERROR, msgbuff.toString());
	                            } else {
	                            	logger.log(Level.WARN, msgbuff.toString());
	                            }
	                            return true;
	                        }
	                    }
	                );
	            } catch (org.xml.sax.SAXException se) {
	                //System.out.println("Unable to validate due to following error.");
	                logger.log(Level.ERROR, se.getMessage());
	                se.printStackTrace();
	            }
	            // Unmarshalling main caex file
	            caexRootObject = u.unmarshal(Paths.get(mainFilePath.toUri()).toFile());
	            logger.log(Level.INFO, "loadData(): caexRootObject created by unmarshalling the main caex xml file!");
	            
	            
		} catch (JAXBException e) {

			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @return
	 */
	public boolean saveData(String filepath) {
		JAXBContext context;

		try {
			context = JAXBContext.newInstance("siima.models.jaxb.caex");
			// context = JAXBContext.newInstance("de.ovgu.mb.iaf.data.tc6_xml");
			// context =
			// JAXBContext.newInstance("de.ovgu.mb.iaf.data.collada_141");

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(caexRootObject, new File(filepath)); 
			logger.log(Level.INFO, "saveData() SAVING to file: " + filepath);
		} catch (JAXBException e) {
			 logger.log(Level.ERROR, e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * TODO: 
	 * 
	 */
	public String parseAnyTypeContent(String parentObjectType, Object parentNodeObject, String anyTypePropertyName, int propOrder  ){
	/* 
	 * Toteuta esim. Main2B.java mukaisesti:
	 * 
	 */
		System.out.println("------XsAnyTypeSolver:parseAnyTypeContent()------ ");
		
		String content=null;
		if("AttributeType".equals(parentObjectType)){
			/*  AttributeType
			 * Order| Property
			 *  	@XmlElement(name = "DefaultValue")
    		 *	1. 	protected Object defaultValue;
    		 *  	@XmlElement(name = "Value")
    		 *  2. 	protected Object value;
			 */
			
			List<Object> attValue = XsAnyTypeSolver.marshal(parentNodeObject, propOrder);
			System.out.println("=============== attValue object: " + attValue.toString());
			String[] valueStruct =  attValue.toString().split(": ");
			String valuestring=null;
			if("[[#text".equals(valueStruct[0])){ // String content
				valuestring=valueStruct[1].split("]]")[0];
				System.out.println("--XsAnyTypeSolver:parseAnyTypeContent(): object has a string value: " + valuestring);
				content = valuestring;
			} else { // Some Object content
				
				logger.log(Level.ERROR, "parseAnyTypeContent() Attribute value content unknown: " + attValue.toString());
			}
		
		} else if ("CAEXBasicObject".equals(parentObjectType)) {
			/*
			 * CAEXFile: ------ AdditionalInformation -------- (e.g.
			 * common/caex/caex_lego/Lego_example_mod2.aml) 2017-06-01 TOIMII
			 * ------------------------------------------------------------
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
			// ---- MARSHALL -----
			List<Object> addInfo = XsAnyTypeSolver.marshal(parentNodeObject, propOrder); // propOrder=1
			if ((addInfo != null) && (!addInfo.isEmpty())) {
				StringBuffer addinfobuf = new StringBuffer();
			
				String[] valueStruct = addInfo.toString().split(": ");
				String valuestring = null;
				if ("[[#text".equals(valueStruct[0])) { // String content
					valuestring = valueStruct[1].split("]]")[0];
					logger.log(Level.INFO,
							"parseAnyTypeContent() AdditionalInformation has a string value: " + valuestring);
					addinfobuf.append("\n----CAEXFile: ADDITIONAL INFORMATION-----------------");
					addinfobuf.append("\n" + valuestring);
					addinfobuf.append("\n ----------------------------------------------------");
					content = addinfobuf.toString();
					
				} else { // Some Object content (e.g. Lego_example_mod2.aml)
					logger.log(Level.INFO,
							"parseAnyTypeContent() AdditionalInformation object as string:\n" + addInfo.toString());
					
					// ---- UNMARSHALL -----
					// EXTRA content class defined for xs:anyType content
					// container
					AppInfoEXTRAContentType appInfoExtra = new AppInfoEXTRAContentType();
					appInfoExtra = (AppInfoEXTRAContentType) XsAnyTypeSolver.unmarshal(addInfo, null,
							AppInfoEXTRAContentType.class);
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

					} else
						System.out.println("parseAnyTypeContent: WriterHeader header is NULL: ");

				}
			}
		}
		return content;
	}
	
	/**
	 * Returns the root object of application data in this container.
	 * 
	 * @return
	 */
	public Object getCaexRootObject() {
		return caexRootObject;
	}

	public Path getMainFilePath() {
		return mainFilePath;
	}
	
	
	public void setMainFilePath(Path mainFilePath) {
		this.mainFilePath = mainFilePath;
	}

	public List<Path> getLoadedCaexFilePaths() {
		return loadedCaexFilePaths;
	}

	public void setLoadedCaexFilePaths(List<Path> loadedCaexFilePaths) {
		this.loadedCaexFilePaths = loadedCaexFilePaths;
	}

	public void setValidationSchemaFile(String validationSchemaFile) {
		this.validationSchemaFile = validationSchemaFile;
	}

	

	/*
	 * INFO methods
	 * 
	 */

	public String getBasicInfo(Object nodeobject) {

		StringBuffer infobuff = new StringBuffer();
		
		/*
		 * CAEXBasicObject: "AdditionalInformation" type="xs:anyType"
		 * AttributeType: "DefaultValue" type="xs:anyType"
		 * AttributeType: "Value" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredMaxValue" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredValue" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredMinValue" type="xs:anyType"
		 * AttributeValueRequirementType:NominalScaledType: "RequiredValue" type="xs:anyType"
		 * 
		 * 
		 * @XmlRootElement(name = "CAEXFile")
			public class CAEXFile
    		extends CAEXBasicObject
		 * 
		 * public class CAEXBasicObject {

    		@XmlElement(name = "Description")
    		protected CAEXBasicObject.Description description;
    		@XmlElement(name = "Version")
    		protected CAEXBasicObject.Version version;
    		@XmlElement(name = "Revision")
    		protected List<CAEXBasicObject.Revision> revision;
    		@XmlElement(name = "Copyright")
    		protected CAEXBasicObject.Copyright copyright;
    		@XmlElement(name = "AdditionalInformation")
    		protected List<Object> additionalInformation;
    		@XmlAttribute(name = "ChangeMode")
    		protected ChangeMode changeMode;
		 * 
		 * 
		 */
		
		

		if (nodeobject != null) {
			if (InternalElementType.class.isInstance(nodeobject)) {
				InternalElementType element = (InternalElementType) nodeobject;
				/*
				 * TODO: RoleRequirements
				 * rolereqs = jaxbParent.getRoleRequirements();
				 * TODO:
				 * List<SupportedRoleClass> suportedRoles =
				 * jaxbParent.getSupportedRoleClass(); 
				 * Description:Allows the association to a RoleClass which this SystemUnitClass can play. 
				 * A SystemUnitClass may reference multiple roles
				 */

				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "InternalElementType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseSystemUnitPath: " + element.getRefBaseSystemUnitPath());

			} else if (SystemUnitFamilyType.class.isInstance(nodeobject)) {
				SystemUnitFamilyType element = (SystemUnitFamilyType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "SystemUnitFamilyType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseClassPath: " + element.getRefBaseClassPath());
				

			} else if (RoleFamilyType.class.isInstance(nodeobject)) {
				RoleFamilyType element = (RoleFamilyType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "RoleFamilyType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseClassPath: " + element.getRefBaseClassPath());

			} else if (InterfaceFamilyType.class.isInstance(nodeobject)) {
				InterfaceFamilyType element = (InterfaceFamilyType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "InterfaceFamilyType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseClassPath: " + element.getRefBaseClassPath());

			} else if (InterfaceClassType.class.isInstance(nodeobject)) {
				InterfaceClassType element = (InterfaceClassType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "InterfaceClassType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseClassPath: " + element.getRefBaseClassPath());

			} else if (InternalLink.class.isInstance(nodeobject)) {
				InternalLink element = (InternalLink) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName()); //"InterfaceClassType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefPartnerSideA: " + element.getRefPartnerSideA());
				infobuff.append("\nRefPartnerSideB: " + element.getRefPartnerSideB());

			} else if (AttributeType.class.isInstance(nodeobject)) {
				/*  AttributeType
				 * Order| Property
				 *  	@XmlElement(name = "DefaultValue")
	    		 *	1. 	protected Object defaultValue;
	    		 *  	@XmlElement(name = "Value")
	    		 *  2. 	protected Object value;
				 */				
				
				AttributeType element = (AttributeType) nodeobject;
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "AttributeType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nUNIT: \t" + element.getUnit());

				String attdatatype = element.getAttributeDataType();
				if (attdatatype != null)
					infobuff.append("\nDATATYPE: \t" + attdatatype);				
				// IN ORIG CAEX Schema type Object: Object defvalue = element.getDefaultValue();
				// (VPA: IN modified CAEX schema: type String)
				Object defvalue = element.getDefaultValue();
				if (defvalue != null){
					//**** using anyType parser ******
					String content = parseAnyTypeContent("AttributeType", nodeobject, "defaultValue", 1 );					
					infobuff.append("\nDEFAULT VALUE: \t" + defvalue.toString());
					infobuff.append("\nDEFAULT VALUE content: " + content);		
				}			
				// IN ORIG CAEX Schema type Object: Object value = element.getValue();
				// (VPA: IN modified CAEX schema: type String)
				Object value = element.getValue();
				
				if (value != null){ 

					//**** using anyType parser ******
					String content = parseAnyTypeContent("AttributeType", nodeobject, "value", 2 );					
					infobuff.append("\nVALUE: \t" + value.toString());
					infobuff.append("\nVALUE content: " + content);				
					
				}
			}

		}

		return infobuff.toString();
	}


}
