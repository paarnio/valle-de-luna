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
import javax.xml.bind.annotation.XmlType;


/**
 * Base element for AttributeNameMapping and InterfaceNameMapping.
 * 
 * <p>Java class for MappingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MappingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXBasicObject"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="AttributeNameMapping" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXBasicObject"&gt;
 *                 &lt;attribute name="SystemUnitAttributeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="RoleAttributeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InterfaceNameMapping" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXBasicObject"&gt;
 *                 &lt;attribute name="SystemUnitInterfaceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="RoleInterfaceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MappingType", propOrder = {
    "attributeNameMapping",
    "interfaceNameMapping"
})
public class MappingType
    extends CAEXBasicObject
{

    @XmlElement(name = "AttributeNameMapping")
    protected List<MappingType.AttributeNameMapping> attributeNameMapping;
    @XmlElement(name = "InterfaceNameMapping")
    protected List<MappingType.InterfaceNameMapping> interfaceNameMapping;

    /**
     * Gets the value of the attributeNameMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeNameMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeNameMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappingType.AttributeNameMapping }
     * 
     * 
     */
    public List<MappingType.AttributeNameMapping> getAttributeNameMapping() {
        if (attributeNameMapping == null) {
            attributeNameMapping = new ArrayList<MappingType.AttributeNameMapping>();
        }
        return this.attributeNameMapping;
    }

    /**
     * Gets the value of the interfaceNameMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interfaceNameMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceNameMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappingType.InterfaceNameMapping }
     * 
     * 
     */
    public List<MappingType.InterfaceNameMapping> getInterfaceNameMapping() {
        if (interfaceNameMapping == null) {
            interfaceNameMapping = new ArrayList<MappingType.InterfaceNameMapping>();
        }
        return this.interfaceNameMapping;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{}CAEXBasicObject"&gt;
     *       &lt;attribute name="SystemUnitAttributeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="RoleAttributeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AttributeNameMapping
        extends CAEXBasicObject
    {

        @XmlAttribute(name = "SystemUnitAttributeName", required = true)
        protected String systemUnitAttributeName;
        @XmlAttribute(name = "RoleAttributeName", required = true)
        protected String roleAttributeName;

        /**
         * Gets the value of the systemUnitAttributeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSystemUnitAttributeName() {
            return systemUnitAttributeName;
        }

        /**
         * Sets the value of the systemUnitAttributeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSystemUnitAttributeName(String value) {
            this.systemUnitAttributeName = value;
        }

        /**
         * Gets the value of the roleAttributeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoleAttributeName() {
            return roleAttributeName;
        }

        /**
         * Sets the value of the roleAttributeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoleAttributeName(String value) {
            this.roleAttributeName = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{}CAEXBasicObject"&gt;
     *       &lt;attribute name="SystemUnitInterfaceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="RoleInterfaceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class InterfaceNameMapping
        extends CAEXBasicObject
    {

        @XmlAttribute(name = "SystemUnitInterfaceName", required = true)
        protected String systemUnitInterfaceName;
        @XmlAttribute(name = "RoleInterfaceName", required = true)
        protected String roleInterfaceName;

        /**
         * Gets the value of the systemUnitInterfaceName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSystemUnitInterfaceName() {
            return systemUnitInterfaceName;
        }

        /**
         * Sets the value of the systemUnitInterfaceName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSystemUnitInterfaceName(String value) {
            this.systemUnitInterfaceName = value;
        }

        /**
         * Gets the value of the roleInterfaceName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoleInterfaceName() {
            return roleInterfaceName;
        }

        /**
         * Sets the value of the roleInterfaceName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoleInterfaceName(String value) {
            this.roleInterfaceName = value;
        }

    }

}
