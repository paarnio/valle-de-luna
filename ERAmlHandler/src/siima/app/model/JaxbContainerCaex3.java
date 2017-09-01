/* JaxbContainerCaex3.java
 * A new JaxbContainer for CAEX 3.0 version.
 * (JaxbContainer is made for CAEX version 2.15)
 * NOTE: Jaxb 3.0 classes now in package: siima.models.jaxb.caex3.
 * 
 * Required changes marked with: 
 * //---- CAEX 3.0 REQUIRED CHANGES
 * //---- CAEX 3.0 REQUIRED ADDITION
 * CHANGES made e.g.:
 *  1. For CAEX Schema v3.0: InternalElement's method getRoleRequirements() returns 
 *  a list of RoleRequirements!
 *  2. Element name changed to InterfaceIDMapping (in 2.15 InterfaceNameMapping)
 *  3. In loadData() context = JAXBContext.newInstance("siima.models.jaxb.caex3");
 * ADDITIONS MADE e.g.
 *  1. Caex 3.0 has a new library: AttributeTypeLib
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

import siima.models.jaxb.caex3.AppInfoEXTRAContentType;
import siima.models.jaxb.caex3.AppInfoEXTRAContentType.WriterHeader;
import siima.models.jaxb.caex3.AttributeFamilyType;
import siima.models.jaxb.caex3.AttributeType;
import siima.models.jaxb.caex3.CAEXBasicObject;
//import siima.models.jaxb.caex3.AttributeType.AttributeValueInterface;
import siima.models.jaxb.caex3.CAEXBasicObject.Copyright;
import siima.models.jaxb.caex3.CAEXBasicObject.Description;
import siima.models.jaxb.caex3.CAEXBasicObject.Revision;
import siima.models.jaxb.caex3.CAEXBasicObject.Version;
import siima.models.jaxb.caex3.CAEXFile;
import siima.models.jaxb.caex3.CAEXFile.AttributeTypeLib;
import siima.models.jaxb.caex3.CAEXFile.ExternalReference;
import siima.models.jaxb.caex3.CAEXFile.InstanceHierarchy;
import siima.models.jaxb.caex3.CAEXFile.InterfaceClassLib;
import siima.models.jaxb.caex3.CAEXFile.RoleClassLib;
import siima.models.jaxb.caex3.CAEXFile.SystemUnitClassLib;
import siima.models.jaxb.caex3.CAEXObject;
import siima.models.jaxb.caex3.ChangeMode;
import siima.models.jaxb.caex3.InterfaceClassType;
import siima.models.jaxb.caex3.InterfaceFamilyType;
import siima.models.jaxb.caex3.InternalElementType;
import siima.models.jaxb.caex3.RoleClassType;
import siima.models.jaxb.caex3.InternalElementType.RoleRequirements;
import siima.models.jaxb.caex3.MappingType;
import siima.models.jaxb.caex3.RoleClassType.ExternalInterface;
import siima.models.jaxb.caex3.RoleFamilyType;
import siima.models.jaxb.caex3.SystemUnitClassType;
import siima.models.jaxb.caex3.SystemUnitClassType.InternalLink;
import siima.models.jaxb.caex3.SystemUnitClassType.SupportedRoleClass;
import siima.models.jaxb.caex3.SystemUnitFamilyType;
import siima.models.jaxb.caex3.TEMP_Helpper;
//import siima.models.jaxb.caex3.WriterHeader;
import siima.app.control.MainAppController;
//import siima.models.jaxb.caex3.WriterHeader;
import siima.app.model.tree.ElementNode;

public class JaxbContainerCaex3  implements JaxbContainerInterface {
	private static final Logger logger=Logger.getLogger(JaxbContainer.class.getName());
	//Modified schema path: configure/schema/caex_2.15_mod/CAEX_V2.15_modified.xsd
	public static String CAEX_SCHEMA ="configure/schema/caex_2.15_orig/CAEX_ClassModel_V2.15.xsd";
	private String validationSchemaFile; //can be set by menu/configuration

	public Path mainFilePath; //latest loaded caex file
	public List<Path> loadedCaexFilePaths; //all loaded caex files
	public Object caexRootObject;
	public int caexFileCount = 0;
	
	public ElementNode ieRootElement = new ElementNode("CAEXFile:InternalElements");
	public ElementNode suclRootElement = new ElementNode("CAEXFile:SysUnitClassLibraries");
	public ElementNode roleclRootElement = new ElementNode("CAEXFile:RoleClassLibraries");
	public ElementNode ifaceclRootElement = new ElementNode("CAEXFile:InterfaceClassLibraries");
	//---- CAEX 3.0 REQUIRED ADDITION
	public ElementNode attributetlRootElement = new ElementNode("CAEXFile:AttributeTypeLibraries");

	//---- CAEX 3.0 REQUIRED ADDITION	
	public ElementNode getAttributetlRootElement() {
		return attributetlRootElement;
	}

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
	
	public int getCaexFileCount() {
		return caexFileCount;
	}

	public void setCaexFileCount(int caexFileCount) {
		this.caexFileCount = caexFileCount;
	}

	public void clearRootElements(){
		//TODO: to be called when new project is created
		ieRootElement = new ElementNode("CAEXFiles:InternalElements");
		suclRootElement = new ElementNode("CAEXFiles:SysUnitClassLibraries");
		roleclRootElement = new ElementNode("CAEXFiles:RoleClassLibraries");
		ifaceclRootElement = new ElementNode("CAEXFiles:InterfaceClassLibraries");
		//---- CAEX 3.0 REQUIRED ADDITION
		attributetlRootElement = new ElementNode("CAEXFile:AttributeTypeLibraries");
		
		logger.info("clearElementTree() all root Elements cleared!");
	}
	

	public ElementNode buildElementGraphFromXML() {
		List<ElementNode> ichildren = new ArrayList<ElementNode>();
		List<ElementNode> caexSuclchildren = new ArrayList<ElementNode>();
		List<ElementNode> caexRoleclchildren = new ArrayList<ElementNode>();
		List<ElementNode> caexIfaceclchildren = new ArrayList<ElementNode>();
		//---- CAEX 3.0 REQUIRED ADDITION
		List<ElementNode> attributetlchildren = new ArrayList<ElementNode>();
		
		CAEXFile caex = (CAEXFile) caexRootObject;
		String caexfilename = caex.getFileName();
		//System.out.println("== JaxbContainer:CAEXFile:" + caex.getFileName());
		logger.info("buildElementGraphFromXML() CAEXFile:" + caex.getFileName());

		/* --------CAEXFile content objects  --------*/
		/* CAEXFile: AdditionalInformation (xs.anyType) 
		 * (TOIMII e.g. by loading Lego_example_mod2.aml (and using caex_2.15_orig_extended classes)
		 * CAEXFile is an extension of CAEXBasicObject (containing AdditionalInformation) and
		 * we need an object of that class with caex object's data content
		 * in order to recover the xs:anyType content of AdditionalInformation
		 */
		CAEXBasicObject basicObject = (CAEXBasicObject)caex;
		
		CAEXBasicObject newbasic = TEMP_Helpper.insertCopyContent(basicObject); 		
		// If several "AdditionalInformation" elements select the first (1)
		String addInfoContent = XsAnyTypeSolver.parseAnyTypeContent("CAEXBasicObject", newbasic, "AdditionalInformation", 1 );	
		//List<Object> addInfoList = caex.getAdditionalInformation();
		//System.out.println("========== CAEX ADD INFO: " + addInfoList.toString());
		logger.info("buildElementGraphFromXML() CAEXFile:AdditionalInformation (1):" + addInfoContent);
		
		int fileNr = this.getCaexFileCount() + 1;
		this.setCaexFileCount(fileNr);
		StringBuffer fileinfobuf = new StringBuffer();
		ChangeMode changeMode = caex.getChangeMode();
		Copyright cright = caex.getCopyright();
		Description description = caex.getDescription();
		String schemaVersion = caex.getSchemaVersion();
		Version version = caex.getVersion();
		
		fileinfobuf.append("\n___CAEXFile_BASIC_INFO___");
		fileinfobuf.append("\nINFO: CAEXFile_" + fileNr + " Name: " + caexfilename);
		fileinfobuf.append("\nINFO: Description: " + caex.getDescription());
		fileinfobuf.append("\nINFO: SchemaVersion: " + caex.getSchemaVersion());
		fileinfobuf.append("\nINFO: Version: " + caex.getVersion());
		fileinfobuf.append("\nINFO: ChangeMode: " + caex.getChangeMode());
		fileinfobuf.append("\nINFO: Copyright: " + caex.getCopyright());
		fileinfobuf.append("\nADD_INFO:");
		fileinfobuf.append(addInfoContent);
		
		fileinfobuf.append("\nEXTERNAL_REFERENCES:");
		List<ExternalReference> extrefs = caex.getExternalReference();
		int refi =0;
		for(ExternalReference eref : extrefs){
			++refi;
			fileinfobuf.append("\n(EREF-" + refi + ") PATH:" + eref.getPath());
			fileinfobuf.append("\n(EREF-" + refi + ") ALIAS:" + eref.getAlias());
		}
		
		String fileJaxbObject = fileinfobuf.toString();
		
		// InstanceHierarchy tree
		ElementNode ihCaexFileElement = new ElementNode("CAEXFile_" + fileNr + ":" + caexfilename);
		ihCaexFileElement.setNodetype("CAEXFile");
		ihCaexFileElement.setJaxbObject(fileJaxbObject);
		List<ElementNode> ihCaexFileElementList = new ArrayList<ElementNode>();
		ihCaexFileElementList.add(ihCaexFileElement);
		
		// SystemUnitClassLib tree
		ElementNode sucLibCaexFileElement = new ElementNode("CAEXFile_" + fileNr + ":" + caexfilename);
		sucLibCaexFileElement.setNodetype("CAEXFile");
		sucLibCaexFileElement.setJaxbObject(fileJaxbObject);
		List<ElementNode> sucLibCaexFileElementList = new ArrayList<ElementNode>();
		sucLibCaexFileElementList.add(sucLibCaexFileElement);
		
		// RoleClassLib tree
		ElementNode rcLibCaexFileElement = new ElementNode("CAEXFile_" + fileNr + ":" + caexfilename);
		rcLibCaexFileElement.setNodetype("CAEXFile");
		rcLibCaexFileElement.setJaxbObject(fileJaxbObject); 
		List<ElementNode> rcLibCaexFileElementList = new ArrayList<ElementNode>();
		rcLibCaexFileElementList.add(rcLibCaexFileElement);
		
		// InterfaceClassLib tree
		ElementNode icLibCaexFileElement = new ElementNode("CAEXFile_" + fileNr + ":" + caexfilename);
		icLibCaexFileElement.setNodetype("CAEXFile");
		icLibCaexFileElement.setJaxbObject(fileJaxbObject); 
		List<ElementNode> icLibCaexFileElementList = new ArrayList<ElementNode>();
		icLibCaexFileElementList.add(icLibCaexFileElement);
		
		// ---- CAEX 3.0 REQUIRED ADDITION
		// AttributeTypeLib tree
		ElementNode atLibCaexFileElement = new ElementNode("CAEXFile_" + fileNr + ":" + caexfilename);
		atLibCaexFileElement.setNodetype("CAEXFile");
		atLibCaexFileElement.setJaxbObject(fileJaxbObject);
		List<ElementNode> atLibCaexFileElementList = new ArrayList<ElementNode>();
		atLibCaexFileElementList.add(atLibCaexFileElement);

		// CAEXFile's hierarchy lists
		List<InstanceHierarchy> instHList = caex.getInstanceHierarchy();
		List<SystemUnitClassLib> systemUnitClassLibList = caex.getSystemUnitClassLib();
		List<RoleClassLib> roleClassLibList = caex.getRoleClassLib();
		List<InterfaceClassLib> interfaceClassLibList = caex.getInterfaceClassLib();
		//List<ExternalReference> extRefList = caex.getExternalReference();
		//List<Revision> revisionList = caex.getRevision();
		// ---- CAEX 3.0 REQUIRED ADDITION
		List<AttributeTypeLib> attributeTypeLibList = caex.getAttributeTypeLib();
		
		/*
		 * Creating InstanceHierarchy tree into ElementNode tree
		 */
		
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
			//ElementNode.linkChildren(ieRootElement, caexFileElementList);
			//ElementNode.linkChildren(caexFileElement, ichildren);
			
			ElementNode.linkChildren(ieRootElement, ihCaexFileElementList);
			ElementNode.linkChildren(ihCaexFileElement, ichildren);
			
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
			//ElementNode.linkChildren(suclRootElement, caexFileElementList);
			//ElementNode.linkChildren(caexFileElement, caexSuclchildren);
			
			ElementNode.linkChildren(suclRootElement, sucLibCaexFileElementList);
			ElementNode.linkChildren(sucLibCaexFileElement, caexSuclchildren);
			
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
			//ElementNode.linkChildren(roleclRootElement, caexFileElementList );
			//ElementNode.linkChildren(caexFileElement, caexRoleclchildren);
			
			ElementNode.linkChildren(roleclRootElement, rcLibCaexFileElementList );
			ElementNode.linkChildren(rcLibCaexFileElement, caexRoleclchildren);
			
		}

		/*
		 * Creating InterfaceClassLib tree into another ElementNode tree
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
				// RECURSIVE CALL: (name was parseCAEXObjectTypesRecursion() in JaxbContainer for 2.15)
				parseInterfaceFamilyTypeRecursion(ifaceclasshierarchy, null, ifaceftypes, 0);
				

			}
			//ElementNode.linkChildren(ifaceclRootElement, caexFileElementList);
			//ElementNode.linkChildren(caexFileElement, caexIfaceclchildren);
			
			ElementNode.linkChildren(ifaceclRootElement, icLibCaexFileElementList);
			ElementNode.linkChildren(icLibCaexFileElement, caexIfaceclchildren);
			
		}
		// ---- CAEX 3.0 REQUIRED ADDITION
		/*
		 * Creating AttributeTypeLib tree into another ElementNode tree
		 * TODO: testing
		 */

		if ((attributeTypeLibList != null) && (!attributeTypeLibList.isEmpty())) {
			System.out.println("== TEST: JaxbContainer:CAEXFile:attributeTypeLibList");
			logger.info("CAEXFile:attributeTypeLibList");
			for (AttributeTypeLib attrtlib : attributeTypeLibList) {
				System.out.println("== TEST: JaxbContainer:AttributeTypeLib name:" + attrtlib.getName());
				logger.info("CAEXFile:AttributeTypeLib name:" + attrtlib.getName());
				ElementNode attrtypehierarchy = new ElementNode("ATypeLIB:" + attrtlib.getName());
				attrtypehierarchy.setJaxbObject(attrtlib);
				attrtypehierarchy.setNodetype("AttributeTypeLib");
				attributetlchildren.add(attrtypehierarchy);
							
				List attributetypes = attrtlib.getAttributeType();
				// RECURSIVE CALL:  
				parseAttributeFamilyTypeRecursion(attrtypehierarchy, null, attributetypes, 0);

			}
						
			ElementNode.linkChildren(attributetlRootElement, atLibCaexFileElementList);
			ElementNode.linkChildren(atLibCaexFileElement, attributetlchildren);
			
		}
		
		
		return ieRootElement;
	}
	
	/**
	 * ---- CAEX 3.0 REQUIRED ADDITION
	 * Recursive search of nested AttributeFAMILYTypes (In AttributeTypeLib) and Element tree
	 * building
	 * 
	 * @return
	 */

	private boolean parseAttributeFamilyTypeRecursion(ElementNode parentNode, AttributeFamilyType jaxbParent,
			List<AttributeFamilyType> parentsAttributeFamilyTypes, int level) {
		//AttributeFAMILYTypes In AttributeTypeLib
		
		boolean ok = true;
		int sibling = 0;
		level += 1;
		List<ElementNode> children = new ArrayList<ElementNode>();

		if (jaxbParent != null) {
		/* Element AttributeType (which is of type AttributeFamilyType)
		 * can contain nested AttributeType elements and Attribute elements.
		 */
		// RECURSIVE CALL: CAEX PARENT'S ATTRIBUTE-TYPE CHILDREN (ie. Attribute elements)
			if(AttributeFamilyType.class.isInstance(jaxbParent)){
				AttributeFamilyType attrtypeParent = (AttributeFamilyType)jaxbParent;
				List<AttributeType> attchildrenlist=attrtypeParent.getAttribute();
				ok = parseAttributeTypeRecursion(parentNode, null, attchildrenlist, level);
			}
			
		}
		// RECURSIVE CALL: CAEX PARENT'S ATTRIBUTE-FAMILY-TYPE CHILDREN (ie. AttributeType elements)
		if ((parentsAttributeFamilyTypes != null) && (!parentsAttributeFamilyTypes.isEmpty())) {
			for (AttributeFamilyType attft : parentsAttributeFamilyTypes) {
				boolean istype = AttributeFamilyType.class.isInstance(attft);
				sibling += 1;
				//System.out.println("(" + level + ":" + sibling
				//		+ ") parseAttributeFamilyTypeRecursion(): AttributeFamilyType @Name:" + attft.getName()
				//		+ " is AttributeFamilyType:" + istype);

				ElementNode kid = new ElementNode("ATTFT:" + attft.getName());
				kid.setJaxbObject(attft);
				kid.setNodetype("AttributeFamilyType");
				children.add(kid);

				// Deeper to children of this child element
				List<AttributeFamilyType> attftchildrenlist = attft.getAttributeType();

				// Recursive call
				ok = parseAttributeFamilyTypeRecursion(kid, attft, attftchildrenlist, level);

			}

		}
		ElementNode.linkChildren(parentNode, children);
		level -= 1;
		return ok;
	}

	
	
	
	
	/**
	 * TODO: Recursive search of nested InterfaceFamilyType (Or CAEXObjects in general) and Element tree
	 * building
	 * (method name was parseCAEXObjectTypesRecursion() in JaxbContainer for 2.15)
	 * @return
	 */

	private boolean parseInterfaceFamilyTypeRecursion(ElementNode parentNode, CAEXObject jaxbParent,
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
				ok = parseInterfaceFamilyTypeRecursion(kid, iface, caexoChildrenlist, level);

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

	private boolean parseAttributeTypeRecursion(ElementNode parentNode, AttributeType jaxbParent,
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

	private boolean parseRoleFamilyTypeRecursion(ElementNode parentNode, RoleFamilyType jaxbParent,
			List<RoleFamilyType> parentsRoleFamilyTypes, int level) {
		// NOTE: After first round jaxbParent is always of RoleFamilyType
		// NOTE: RoleFamilyType object can have RoleFamilyType children
		// NOTE: RoleFamilyType extends RoleClassType

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
			
			/* CAEX EXT-INTERFACES
			 * RoleClassType.ExternalInterface extends InterfaceClassType
			 */
			List<ExternalInterface> parentsExtInterfaces = jaxbParent.getExternalInterface();
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
					
			
			
			//String refBCPath = jaxbParent.getRefBaseClassPath();
			
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

	private boolean parseSystemUnitFamilyTypeRecursion(ElementNode parentNode, SystemUnitFamilyType jaxbParent,
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

	private boolean parseInternalElementsRecursion(ElementNode parentNode, InternalElementType jaxbParent,
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

			/* CAEX EXT-INTERFACES
			 * RoleClassType.ExternalInterface extends InterfaceClassType
			 */
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
			
			/*
			 *  ROLE_REQUIREMENTS
			 *  For CAEX Schema v3.0: in CAEX v 3.0 getRoleRequirements() returns a list of RoleRequirements!
			 *  public List<InternalElementType.RoleRequirements> getRoleRequirements()
			 */
			//---- CAEX 3.0 REQUIRED CHANGES
			//(Caex 2.15: RoleRequirements rolerequirements = jaxbParent.getRoleRequirements();)
			List<RoleRequirements> rolerequirementsList = jaxbParent.getRoleRequirements();
			
			if(rolerequirementsList!=null){
			for(RoleRequirements rolerequirements : rolerequirementsList){
			if(rolerequirements!=null){
			ElementNode parentsRoleReqsNode = new ElementNode("ROLEREQS OF:" + parentNode.getName());
			parentsRoleReqsNode.setJaxbObject(rolerequirements);
			parentsRoleReqsNode.setNodetype("RoleRequirements");
			//String refbaserole = rolerequirements.getRefBaseRoleClassPath();		
			
			List<AttributeType> rolereqAttributeList = rolerequirements.getAttribute();
			if ((rolereqAttributeList != null) && (!rolereqAttributeList.isEmpty())) {
				parseAttributeTypeRecursion(parentsRoleReqsNode, null, rolereqAttributeList, 0);				
			}
			List<InterfaceClassType> rolereqExtInterfaceList = rolerequirements.getExternalInterface();
			List<ElementNode> rolereqChildren = new ArrayList<ElementNode>();
			if ((rolereqExtInterfaceList != null) && (!rolereqExtInterfaceList.isEmpty())) {
				for (InterfaceClassType iface : rolereqExtInterfaceList) {
					// iface.getRefBaseClassPath();
					ElementNode rolereqIfaceNode = new ElementNode("IFace:" + iface.getName());
					rolereqIfaceNode.setJaxbObject(iface);
					rolereqIfaceNode.setNodetype("InterfaceClassType");
					rolereqChildren.add(rolereqIfaceNode);
					
				}
								
			}
					
			ElementNode.linkChildren(parentsRoleReqsNode, rolereqChildren);
			children.add(parentsRoleReqsNode);
			}
			}
			} //end if(rolerequirementsList!=null) 
			
			
			// SUPPORTED ROLES (SystemUnitClassType.SupportedRoleClass extends CAEXBasicObject)
			List<SupportedRoleClass> suportedRoles = jaxbParent.getSupportedRoleClass();
			if(suportedRoles!=null){
				for (SupportedRoleClass supprole : suportedRoles) {
					ElementNode supproleNode = new ElementNode("SUPPORTED_ROLE_OF" + parentNode.getName());
					supproleNode.setJaxbObject(supprole);
					supproleNode.setNodetype("SupportedRoleClass");
					//String rrcPath = supprole.getRefRoleClassPath();
					
					List<ElementNode> supproleChildren = new ArrayList<ElementNode>();
					//MappingType extends CAEXBasicObject
					MappingType mapping = supprole.getMappingObject();
					ElementNode mappingNode = new ElementNode("MAPPING_TYPE_OF" + supproleNode.getName());
					mappingNode.setJaxbObject(mapping);
					mappingNode.setNodetype("MappingType");
					supproleChildren.add(mappingNode);					
					ElementNode.linkChildren(supproleNode, supproleChildren);
					children.add(supproleNode);
				}
			}
			
			
		} // END if (jaxbParent != null)

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
			//---- CAEX 3.0 REQUIRED CHANGES
			//context = JAXBContext.newInstance("siima.models.jaxb.caex");
			context = JAXBContext.newInstance("siima.models.jaxb.caex3");
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

		try {//CEAX 3.0 Changes
			context = JAXBContext.newInstance("siima.models.jaxb.caex3");
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
	 * parseAnyTypeContent() 
	 * MOVED TO XsAnyTypeSolver as a static method
	 */
	public String XXXparseAnyTypeContent(String parentObjectType, Object parentNodeObject, String anyTypePropertyName,
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

			content = XsAnyTypeSolver.getAnyTypeStringContent(parentNodeObject, anyTypePropertyName); // NEW
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
			String strContent = XsAnyTypeSolver.getAnyTypeStringContent(parentNodeObject, anyTypePropertyName); // NEW

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
				appInfoExtra = (AppInfoEXTRAContentType) XsAnyTypeSolver.getAnyTypeElementContent(parentNodeObject,
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
		
		/* xs:anyType objects:
		 * ------------------
		 * CAEXBasicObject: "AdditionalInformation" type="xs:anyType"
		 * AttributeType: "DefaultValue" type="xs:anyType"
		 * AttributeType: "Value" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredMaxValue" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredValue" type="xs:anyType"
		 * AttributeValueRequirementType:OrdinalScaledType: "RequiredMinValue" type="xs:anyType"
		 * AttributeValueRequirementType:NominalScaledType: "RequiredValue" type="xs:anyType"
		 * -------------------
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
		 */
				

		if (nodeobject != null) {
								
			if (ExternalReference.class.isInstance(nodeobject)) {
				ExternalReference element = (ExternalReference) nodeobject;
				
				infobuff.append("\nNAME: \t" + "(no name)");
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nPATH: \t" + element.getPath());
				infobuff.append("\nALIAS: \t" + element.getAlias());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else if (InstanceHierarchy.class.isInstance(nodeobject)) {
				InstanceHierarchy element = (InstanceHierarchy) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else if (SystemUnitClassLib.class.isInstance(nodeobject)) {
				SystemUnitClassLib element = (SystemUnitClassLib) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else  if (RoleClassLib.class.isInstance(nodeobject)) {
				RoleClassLib element = (RoleClassLib) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else if (InterfaceClassLib.class.isInstance(nodeobject)) {
				InterfaceClassLib element = (InterfaceClassLib) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else if (AttributeTypeLib.class.isInstance(nodeobject)) {
				//---- CAEX 3.0 REQUIRED ADDITION
				AttributeTypeLib element = (AttributeTypeLib) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				Version version = element.getVersion();
				if (version != null)
					infobuff.append("\nVERSION: " + version.getValue());
				

			} else if (InternalElementType.class.isInstance(nodeobject)) {
				InternalElementType element = (InternalElementType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "InternalElementType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());
				infobuff.append("\nRefBaseSystemUnitPath: " + element.getRefBaseSystemUnitPath());

			} else if (RoleRequirements.class.isInstance(nodeobject)) {
				RoleRequirements element = (RoleRequirements) nodeobject;
				
				infobuff.append("\nNAME: \t" + "(RoleRequirements)");
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nREF_BASEROLE_CLASSPATH: " + element.getRefBaseRoleClassPath());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());			

			} else if (SupportedRoleClass.class.isInstance(nodeobject)) {
				SupportedRoleClass element = (SupportedRoleClass) nodeobject;
				
				infobuff.append("\nNAME: \t" + "(SupportedRoleClass)");
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				infobuff.append("\nREF_ROLE_CLASSPATH: " + element.getRefRoleClassPath());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());			

			} else if (MappingType.class.isInstance(nodeobject)) {
				MappingType element = (MappingType) nodeobject;
				
				infobuff.append("\nNAME: \t" + "(MappingType)");
				infobuff.append("\nTYPE: \t" + element.getClass().getSimpleName());
				if(!element.getAttributeNameMapping().isEmpty())
					infobuff.append("\nCONTAINS: AttributeNameMapping: true");
				/*---- CAEX 3.0 REQUIRED CHANGES
				 * Element name changed to InterfaceIDMapping (in 2.15 InterfaceNameMapping)
				 */
				
				if(!element.getInterfaceIDMapping().isEmpty())
					infobuff.append("\nCONTAINS: InterfaceNameMapping: true");
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());			

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

			} else if (AttributeFamilyType.class.isInstance(nodeobject)) {
				//---- CAEX 3.0 REQUIRED ADDITION
				AttributeFamilyType element = (AttributeFamilyType) nodeobject;
				
				infobuff.append("\nNAME: \t" + element.getName());
				infobuff.append("\nTYPE: \t" + "AttributeFamilyType");
				infobuff.append("\nGUID: \t" + element.getID());
				Description description = element.getDescription();
				if (description != null)
					infobuff.append("\nDESCRIPTION: \t" + description.getValue());

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
					String content = XsAnyTypeSolver.parseAnyTypeContent("AttributeType", nodeobject, "DefaultValue", 1 );					
					infobuff.append("\nDEFAULT VALUE: \t" + defvalue.toString());
					infobuff.append("\nDEFAULT VALUE content: " + content);		
				}			
				// IN ORIG CAEX Schema type Object: Object value = element.getValue();
				// (VPA: IN modified CAEX schema: type String)
				Object value = element.getValue();
				
				if (value != null){ 

					//**** using anyType parser ******
					String content = XsAnyTypeSolver.parseAnyTypeContent("AttributeType", nodeobject, "Value", 1 );					
					infobuff.append("\nVALUE: \t" + value.toString());
					infobuff.append("\nVALUE content: " + content);				
					
				}
			}

		}

		return infobuff.toString();
	}


}
