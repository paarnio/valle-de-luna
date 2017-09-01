//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.30 at 09:43:56 AM EEST 
//


package siima.models.jaxb.caex3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines base structures for a SystemUnit class definition.
 * 
 * <p>Java class for SystemUnitClassType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SystemUnitClassType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXObject"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="Attribute" type="{}AttributeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ExternalInterface" type="{}InterfaceClassType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="InternalElement" type="{}InternalElementType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SupportedRoleClass" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXBasicObject"&gt;
 *                 &lt;sequence minOccurs="0"&gt;
 *                   &lt;element name="MappingObject" type="{}MappingType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="RefRoleClassPath" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InternalLink" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXObject"&gt;
 *                 &lt;attribute name="RefPartnerSideA" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="RefPartnerSideB" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "SystemUnitClassType", propOrder = {
    "attribute",
    "externalInterface",
    "internalElement",
    "supportedRoleClass",
    "internalLink"
})
@XmlSeeAlso({
    SystemUnitFamilyType.class,
    InternalElementType.class
})
public class SystemUnitClassType
    extends CAEXObject
{

    @XmlElement(name = "Attribute")
    protected List<AttributeType> attribute;
    @XmlElement(name = "ExternalInterface")
    protected List<InterfaceClassType> externalInterface;
    @XmlElement(name = "InternalElement")
    protected List<InternalElementType> internalElement;
    @XmlElement(name = "SupportedRoleClass")
    protected List<SystemUnitClassType.SupportedRoleClass> supportedRoleClass;
    @XmlElement(name = "InternalLink")
    protected List<SystemUnitClassType.InternalLink> internalLink;

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
     * Gets the value of the externalInterface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalInterface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalInterface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InterfaceClassType }
     * 
     * 
     */
    public List<InterfaceClassType> getExternalInterface() {
        if (externalInterface == null) {
            externalInterface = new ArrayList<InterfaceClassType>();
        }
        return this.externalInterface;
    }

    /**
     * Gets the value of the internalElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the internalElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInternalElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InternalElementType }
     * 
     * 
     */
    public List<InternalElementType> getInternalElement() {
        if (internalElement == null) {
            internalElement = new ArrayList<InternalElementType>();
        }
        return this.internalElement;
    }

    /**
     * Gets the value of the supportedRoleClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedRoleClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedRoleClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SystemUnitClassType.SupportedRoleClass }
     * 
     * 
     */
    public List<SystemUnitClassType.SupportedRoleClass> getSupportedRoleClass() {
        if (supportedRoleClass == null) {
            supportedRoleClass = new ArrayList<SystemUnitClassType.SupportedRoleClass>();
        }
        return this.supportedRoleClass;
    }

    /**
     * Gets the value of the internalLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the internalLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInternalLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SystemUnitClassType.InternalLink }
     * 
     * 
     */
    public List<SystemUnitClassType.InternalLink> getInternalLink() {
        if (internalLink == null) {
            internalLink = new ArrayList<SystemUnitClassType.InternalLink>();
        }
        return this.internalLink;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{}CAEXObject"&gt;
     *       &lt;attribute name="RefPartnerSideA" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="RefPartnerSideB" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class InternalLink
        extends CAEXObject
    {

        @XmlAttribute(name = "RefPartnerSideA", required = true)
        protected String refPartnerSideA;
        @XmlAttribute(name = "RefPartnerSideB", required = true)
        protected String refPartnerSideB;

        /**
         * Gets the value of the refPartnerSideA property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRefPartnerSideA() {
            return refPartnerSideA;
        }

        /**
         * Sets the value of the refPartnerSideA property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRefPartnerSideA(String value) {
            this.refPartnerSideA = value;
        }

        /**
         * Gets the value of the refPartnerSideB property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRefPartnerSideB() {
            return refPartnerSideB;
        }

        /**
         * Sets the value of the refPartnerSideB property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRefPartnerSideB(String value) {
            this.refPartnerSideB = value;
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
     *       &lt;sequence minOccurs="0"&gt;
     *         &lt;element name="MappingObject" type="{}MappingType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="RefRoleClassPath" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "mappingObject"
    })
    public static class SupportedRoleClass
        extends CAEXBasicObject
    {

        @XmlElement(name = "MappingObject")
        protected MappingType mappingObject;
        @XmlAttribute(name = "RefRoleClassPath", required = true)
        protected String refRoleClassPath;

        /**
         * Gets the value of the mappingObject property.
         * 
         * @return
         *     possible object is
         *     {@link MappingType }
         *     
         */
        public MappingType getMappingObject() {
            return mappingObject;
        }

        /**
         * Sets the value of the mappingObject property.
         * 
         * @param value
         *     allowed object is
         *     {@link MappingType }
         *     
         */
        public void setMappingObject(MappingType value) {
            this.mappingObject = value;
        }

        /**
         * Gets the value of the refRoleClassPath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRefRoleClassPath() {
            return refRoleClassPath;
        }

        /**
         * Sets the value of the refRoleClassPath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRefRoleClassPath(String value) {
            this.refRoleClassPath = value;
        }

    }

}