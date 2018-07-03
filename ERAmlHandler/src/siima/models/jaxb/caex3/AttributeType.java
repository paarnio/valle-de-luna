//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.03 at 01:27:51 PM EEST 
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
 * Defines base structures for attribute definitions.
 * 
 * <p>Java class for AttributeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXObject"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="DefaultValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RefSemantic" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXBasicObject"&gt;
 *                 &lt;attribute name="CorrespondingAttributePath" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Constraint" type="{}AttributeValueRequirementType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Attribute" type="{}AttributeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Unit" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="AttributeDataType"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="RefAttributeType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeType", propOrder = {
    "defaultValue",
    "value",
    "refSemantic",
    "constraint",
    "attribute"
})
@XmlSeeAlso({
    AttributeFamilyType.class
})
public class AttributeType
    extends CAEXObject
{

    @XmlElement(name = "DefaultValue")
    protected String defaultValue;
    @XmlElement(name = "Value")
    protected String value;
    @XmlElement(name = "RefSemantic")
    protected List<AttributeType.RefSemantic> refSemantic;
    @XmlElement(name = "Constraint")
    protected List<AttributeValueRequirementType> constraint;
    @XmlElement(name = "Attribute")
    protected List<AttributeType> attribute;
    @XmlAttribute(name = "Unit")
    protected String unit;
    @XmlAttribute(name = "AttributeDataType")
    protected String attributeDataType;
    @XmlAttribute(name = "RefAttributeType")
    protected String refAttributeType;

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the refSemantic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the refSemantic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRefSemantic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType.RefSemantic }
     * 
     * 
     */
    public List<AttributeType.RefSemantic> getRefSemantic() {
        if (refSemantic == null) {
            refSemantic = new ArrayList<AttributeType.RefSemantic>();
        }
        return this.refSemantic;
    }

    /**
     * Gets the value of the constraint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeValueRequirementType }
     * 
     * 
     */
    public List<AttributeValueRequirementType> getConstraint() {
        if (constraint == null) {
            constraint = new ArrayList<AttributeValueRequirementType>();
        }
        return this.constraint;
    }

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
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the attributeDataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeDataType() {
        return attributeDataType;
    }

    /**
     * Sets the value of the attributeDataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeDataType(String value) {
        this.attributeDataType = value;
    }

    /**
     * Gets the value of the refAttributeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefAttributeType() {
        return refAttributeType;
    }

    /**
     * Sets the value of the refAttributeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefAttributeType(String value) {
        this.refAttributeType = value;
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
     *       &lt;attribute name="CorrespondingAttributePath" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class RefSemantic
        extends CAEXBasicObject
    {

        @XmlAttribute(name = "CorrespondingAttributePath", required = true)
        protected String correspondingAttributePath;

        /**
         * Gets the value of the correspondingAttributePath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrespondingAttributePath() {
            return correspondingAttributePath;
        }

        /**
         * Sets the value of the correspondingAttributePath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrespondingAttributePath(String value) {
            this.correspondingAttributePath = value;
        }

    }

}
