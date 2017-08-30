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
import javax.xml.bind.annotation.XmlType;


/**
 * Defines base structures for definition of value requirements of an attribute.
 * 
 * <p>Java class for AttributeValueRequirementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributeValueRequirementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXBasicObject"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="OrdinalScaledType"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence minOccurs="0"&gt;
 *                   &lt;element name="RequiredMaxValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *                   &lt;element name="RequiredValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *                   &lt;element name="RequiredMinValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="NominalScaledType"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence minOccurs="0"&gt;
 *                   &lt;element name="RequiredValue" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="UnknownType"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence minOccurs="0"&gt;
 *                   &lt;element name="Requirements" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeValueRequirementType", propOrder = {
    "ordinalScaledType",
    "nominalScaledType",
    "unknownType"
})
public class AttributeValueRequirementType
    extends CAEXBasicObject
{

    @XmlElement(name = "OrdinalScaledType")
    protected AttributeValueRequirementType.OrdinalScaledType ordinalScaledType;
    @XmlElement(name = "NominalScaledType")
    protected AttributeValueRequirementType.NominalScaledType nominalScaledType;
    @XmlElement(name = "UnknownType")
    protected AttributeValueRequirementType.UnknownType unknownType;
    @XmlAttribute(name = "Name", required = true)
    protected String name;

    /**
     * Gets the value of the ordinalScaledType property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueRequirementType.OrdinalScaledType }
     *     
     */
    public AttributeValueRequirementType.OrdinalScaledType getOrdinalScaledType() {
        return ordinalScaledType;
    }

    /**
     * Sets the value of the ordinalScaledType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueRequirementType.OrdinalScaledType }
     *     
     */
    public void setOrdinalScaledType(AttributeValueRequirementType.OrdinalScaledType value) {
        this.ordinalScaledType = value;
    }

    /**
     * Gets the value of the nominalScaledType property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueRequirementType.NominalScaledType }
     *     
     */
    public AttributeValueRequirementType.NominalScaledType getNominalScaledType() {
        return nominalScaledType;
    }

    /**
     * Sets the value of the nominalScaledType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueRequirementType.NominalScaledType }
     *     
     */
    public void setNominalScaledType(AttributeValueRequirementType.NominalScaledType value) {
        this.nominalScaledType = value;
    }

    /**
     * Gets the value of the unknownType property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueRequirementType.UnknownType }
     *     
     */
    public AttributeValueRequirementType.UnknownType getUnknownType() {
        return unknownType;
    }

    /**
     * Sets the value of the unknownType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueRequirementType.UnknownType }
     *     
     */
    public void setUnknownType(AttributeValueRequirementType.UnknownType value) {
        this.unknownType = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence minOccurs="0"&gt;
     *         &lt;element name="RequiredValue" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "requiredValue"
    })
    public static class NominalScaledType {

        @XmlElement(name = "RequiredValue")
        protected List<Object> requiredValue;

        /**
         * Gets the value of the requiredValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the requiredValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRequiredValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getRequiredValue() {
            if (requiredValue == null) {
                requiredValue = new ArrayList<Object>();
            }
            return this.requiredValue;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence minOccurs="0"&gt;
     *         &lt;element name="RequiredMaxValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
     *         &lt;element name="RequiredValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
     *         &lt;element name="RequiredMinValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "requiredMaxValue",
        "requiredValue",
        "requiredMinValue"
    })
    public static class OrdinalScaledType {

        @XmlElement(name = "RequiredMaxValue")
        protected Object requiredMaxValue;
        @XmlElement(name = "RequiredValue")
        protected Object requiredValue;
        @XmlElement(name = "RequiredMinValue")
        protected Object requiredMinValue;

        /**
         * Gets the value of the requiredMaxValue property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getRequiredMaxValue() {
            return requiredMaxValue;
        }

        /**
         * Sets the value of the requiredMaxValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setRequiredMaxValue(Object value) {
            this.requiredMaxValue = value;
        }

        /**
         * Gets the value of the requiredValue property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getRequiredValue() {
            return requiredValue;
        }

        /**
         * Sets the value of the requiredValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setRequiredValue(Object value) {
            this.requiredValue = value;
        }

        /**
         * Gets the value of the requiredMinValue property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getRequiredMinValue() {
            return requiredMinValue;
        }

        /**
         * Sets the value of the requiredMinValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setRequiredMinValue(Object value) {
            this.requiredMinValue = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence minOccurs="0"&gt;
     *         &lt;element name="Requirements" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "requirements"
    })
    public static class UnknownType {

        @XmlElement(name = "Requirements")
        protected String requirements;

        /**
         * Gets the value of the requirements property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequirements() {
            return requirements;
        }

        /**
         * Sets the value of the requirements property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequirements(String value) {
            this.requirements = value;
        }

    }

}
