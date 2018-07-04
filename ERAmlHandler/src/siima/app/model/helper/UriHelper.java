/* UriHelper.java
 * This Class is loaded into Velocity Context and
 * used in Velocity template (.vm) to create Uri's for the Caex ontology individuals.
 * CALLING MY OWN CLASS METHOD: 
 * SEE: https://stackoverflow.com/questions/20786403/calling-a-java-method-in-velocity-template
 * 
 */
package siima.app.model.helper;

public class UriHelper {
	public String caexontns = "http://data.ifs.tuwien.ac.at/aml/ontology#";
	public String siimaontns ="http://siima.net/ontologies/2017/caex/";
	
	/* OTHER NS:
	 * 	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
		xmlns:owl="http://www.w3.org/2002/07/owl#"
    	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
		xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
	 */
	
	public String createInstanceUri(String libcategory, String instanceOwlType, String hierarchy, String iElement, String instanceName ){
		/* 2018-07-02 libcategory = InstanceHierarchy | SystemUnitClassLib | RoleClassLib | InterfaceClassLib | AttributeTypeLib
		 * EFECTS: Uri of the Attribute element will be set differently depending on library category
		 * parameters: instanceOwlType is OWL Class type (NOT JAXB)
		 * These instances belong to siimaontns namespace
		 * (CAEX Class definitions are from caexontns namespace)
		 * 
		 */
		String uri="";
		
		if("CAEXFile".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "CF-" + instanceName;		
		} else if("InstanceHierarchy".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + instanceName;		
		} else if("InternalElement".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + instanceName;		
		} else if("ExternalInterface".equalsIgnoreCase(instanceOwlType)){ // subClass of InterfaceClass		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_EXTINT-" + instanceName;		
		} else if("InternalLink".equalsIgnoreCase(instanceOwlType)){ // subClass of CAEXObject		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy +  "_ILINK-" + instanceName; //NO: "_IE-" + iElement +		
		} else if("RoleRequirements".equalsIgnoreCase(instanceOwlType)){ // subClass of CAEXBasicObject		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_RR-" + instanceName;
			System.out.println("????????????URIHELP: RoleRequirements " + uri);
		}
		
		
		if("SystemUnitClassLib".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "SUCLIB-" + instanceName;		
		} else 	if("SystemUnitClass".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "SUCLIB-" + hierarchy + "_SUC-" + instanceName;		
		} 
		
		if("RoleClassLib".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "ROLECLIB-" + instanceName;		
		} else 	if("RoleClass".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "ROLECLIB-" + hierarchy + "_ROLEC-" + instanceName;		
		} 
		
		if("InterfaceClassLib".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IFACECLIB-" + instanceName;		
		} else 	if("InterfaceClass".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IFACECLIB-" + hierarchy + "_IFACEC-" + instanceName;		
		} 
		
		// 2018-07-01 added for caex 3
		if("AttributeTypeLib".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "ATTTYPELIB-" + instanceName;		
		} else 	if("AttributeType".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "ATTTYPELIB-" + hierarchy + "_ATTTYPE-" + instanceName;		
		} // NOTE: Attribute URI defined for InstanceHierarchy
		
		/*
		 *  Attribute
		 *  Note: All libraries may contain Attributes;
		 *  Uri will differ by some interim prefixes
		 */
		
		if("Attribute".equalsIgnoreCase(instanceOwlType)){
			
			if("InstanceHierarchy".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_ATT-" + instanceName;
			} else if("AttributeTypeLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ATTTYPELIB-" + hierarchy + "_ATTTYPE-" + iElement + "_ATT-" + instanceName;
			} else if("SystemUnitClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "SUCLIB-" + hierarchy + "_SUC-" + iElement + "_ATT-" + instanceName;
			} else if("RoleClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ROLECLIB-" + hierarchy + "_ROLEC-" + iElement + "_ATT-" + instanceName;
			} else if("InterfaceClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IFACECLIB-" + hierarchy + "_IFACEC-" + iElement + "_ATT-" + instanceName;
			} else {
				uri = siimaontns + instanceOwlType + "-INST_" + "XXX-" + hierarchy + "_XX-" + iElement + "_ATT-" + instanceName;
			}
		}
		
		/*
		 *  Attribute constraint is type AttributeValueRequirement
		 *  Note: All libraries may contain Attributes which may contain Constraints;
		 *  Uri will differ by some interim prefixes
		 */
		
		
		if("AttributeValueRequirement".equalsIgnoreCase(instanceOwlType)){
			
			if("InstanceHierarchy".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_ATTVREQ-" + instanceName;
			} else if("AttributeTypeLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ATTTYPELIB-" + hierarchy + "_ATTTYPE-" + iElement + "_ATTVREQ-" + instanceName;
			} else if("SystemUnitClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "SUCLIB-" + hierarchy + "_SUC-" + iElement + "_ATTVREQ-" + instanceName;
			} else if("RoleClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ROLECLIB-" + hierarchy + "_ROLEC-" + iElement + "_ATTVREQ-" + instanceName;
			} else if("InterfaceClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IFACECLIB-" + hierarchy + "_IFACEC-" + iElement + "_ATTVREQ-" + instanceName;
			} else {
				uri = siimaontns + instanceOwlType + "-INST_" + "XXX-" + hierarchy + "_XX-" + iElement + "_ATTVREQ-" + instanceName;
			}
		}
		
		if(("OrdinalScaled".equalsIgnoreCase(instanceOwlType))||("NominalScaled".equalsIgnoreCase(instanceOwlType))||("UnknownType".equalsIgnoreCase(instanceOwlType))){
			
			if("InstanceHierarchy".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_ATTVREQSCALED-" + instanceName;
			} else if("AttributeTypeLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ATTTYPELIB-" + hierarchy + "_ATTTYPE-" + iElement + "_ATTVREQSCALED-" + instanceName;
			} else if("SystemUnitClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "SUCLIB-" + hierarchy + "_SUC-" + iElement + "_ATTVREQSCALED-" + instanceName;
			} else if("RoleClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "ROLECLIB-" + hierarchy + "_ROLEC-" + iElement + "_ATTVREQSCALED-" + instanceName;
			} else if("InterfaceClassLib".equalsIgnoreCase(libcategory)){
				uri = siimaontns + instanceOwlType + "-INST_" + "IFACECLIB-" + hierarchy + "_IFACEC-" + iElement + "_ATTVREQSCALED-" + instanceName;
			} else {
				uri = siimaontns + instanceOwlType + "-INST_" + "XXX-" + hierarchy + "_XX-" + iElement + "_ATTVREQSCALED-" + instanceName;
			}
		}
		
		return uri;
	}


}
