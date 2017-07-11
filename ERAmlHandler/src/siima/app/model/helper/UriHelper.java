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
	
	public String createInstanceUri(String instanceOwlType, String hierarchy, String iElement, String instanceName ){
		// parameters: instanceOwlType is OWL Class type (NOT JAXB)
		// These instances belong to siimaontns namespace
		// (CAEX Class definitions are from caexontns namespace)
		String uri="";
		
		if("CAEXFile".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "CF-" + instanceName;		
		} else if("InstanceHierarchy".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + instanceName;		
		} else if("Attribute".equalsIgnoreCase(instanceOwlType)){		
			uri = siimaontns + instanceOwlType + "-INST_" + "IH-" + hierarchy + "_IE-" + iElement + "_ATT-" + instanceName;		
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
		return uri;
	}


}
