//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.10 at 10:25:13 AM EEST 
//


package siima.models.jaxb.caex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines base structures for a hierarchical InterfaceClass tree. The hierarchical structure of an interface library has organizational character only.  
 * 
 * <p>Java class for InterfaceFamilyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterfaceFamilyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}InterfaceClassType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="InterfaceClass" type="{}InterfaceFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceFamilyType", propOrder = {
    "interfaceClass"
})
public class InterfaceFamilyType
    extends InterfaceClassType
{

    @XmlElement(name = "InterfaceClass")
    protected List<InterfaceFamilyType> interfaceClass;

    /**
     * Gets the value of the interfaceClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interfaceClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InterfaceFamilyType }
     * 
     * 
     */
    public List<InterfaceFamilyType> getInterfaceClass() {
        if (interfaceClass == null) {
            interfaceClass = new ArrayList<InterfaceFamilyType>();
        }
        return this.interfaceClass;
    }

}
