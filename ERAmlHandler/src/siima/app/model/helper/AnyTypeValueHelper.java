/* AnyTypeValueHelper.java
 * This Class is loaded into Velocity Context and
 * used in Velocity template (.vm) to access content value of Jaxb objects
 * based on xs:anyType elements, e.g. Attributes Value element. 
 * Needed in Caex ontology generation by Velocity.
 * CALLING MY OWN CLASS METHOD: 
 * SEE: https://stackoverflow.com/questions/20786403/calling-a-java-method-in-velocity-template
 * 
 */

package siima.app.model.helper;

import siima.app.model.JaxbContainer;
import siima.models.jaxb.caex.AttributeType;

public class AnyTypeValueHelper {
	public JaxbContainer jaxbc;
	
	public AnyTypeValueHelper(JaxbContainer jaxbcontainer){
		
		this.jaxbc = jaxbcontainer;
	}
	
	
	public String getAnyTypeContent(String nodetype, Object nodeobject, String asked_content_name) {
		// See also: JaxbContainer getBasicInfo()
		String content = null;

		if (nodeobject != null) {
			if (AttributeType.class.isInstance(nodeobject)) {
				/*
				 * AttributeType Order| Property
				 * 
				 * @XmlElement(name = "DefaultValue") 1. protected Object
				 * defaultValue;
				 * 
				 * @XmlElement(name = "Value") 2. protected Object value;
				 */

				AttributeType element = (AttributeType) nodeobject;
				if ("DefaultValue".equalsIgnoreCase(asked_content_name)) {
					Object defvalue = element.getDefaultValue();
					if (defvalue != null) {
						// **** using anyType parser ******
						content = this.jaxbc.parseAnyTypeContent("AttributeType", nodeobject, "DefaultValue", 1);
						// infobuff.append("\nDEFAULT VALUE: \t" +
						// defvalue.toString());
						// infobuff.append("\nDEFAULT VALUE content: " +
						// content);
					}
					// IN ORIG CAEX Schema type Object: Object value =
					// element.getValue();

				} else if ("Value".equalsIgnoreCase(asked_content_name)) {
					Object value = element.getValue();

					if (value != null) {

						// **** using anyType parser ******
						content = this.jaxbc.parseAnyTypeContent("AttributeType", nodeobject, "Value", 1);
						// infobuff.append("\nVALUE: \t" + value.toString());
						// infobuff.append("\nVALUE content: " + content);

					}
				}
			}
		}
		return content;
	}

}
