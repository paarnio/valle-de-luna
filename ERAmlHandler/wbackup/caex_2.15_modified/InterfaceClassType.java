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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Shall be used for InterfaceClass definition, provides base structures for an interface class definition.
 * 
 * <p>Java class for InterfaceClassType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterfaceClassType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXObject"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="Attribute" type="{}AttributeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="RefBaseClassPath" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceClassType", propOrder = {
    "attribute"
})
@XmlSeeAlso({
    InterfaceFamilyType.class,
    siima.models.jaxb.caex.RoleClassType.ExternalInterface.class
})
public class InterfaceClassType
    extends CAEXObject
{

    @XmlElement(name = "Attribute")
    protected List<AttributeType> attribute;
    @XmlAttribute(name = "RefBaseClassPath")
    protected String refBaseClassPath;

    /**
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType }
     * 
     * 
     */
    public List<AttributeType> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<AttributeType>();
        }
        return this.attribute;
    }

    /**
     * Gets the value of the refBaseClassPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefBaseClassPath() {
        return refBaseClassPath;
    }

    /**
     * Sets the value of the refBaseClassPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefBaseClassPath(String value) {
        this.refBaseClassPath = value;
    }

}
