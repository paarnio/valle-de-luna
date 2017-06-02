//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.10 at 04:40:43 ip. EEST 
//


package siima.models.jaxb.caex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * CAEX basis object that comprises a basic set of attributes and header information which exist for all CAEX elements.
 * 
 * <p>Java class for CAEXBasicObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CAEXBasicObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{}Header" minOccurs="0"/>
 *       &lt;attribute name="ChangeMode" type="{}ChangeMode" default="state" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAEXBasicObject", propOrder = {
    "description",
    "version",
    "revision",
    "copyright",
    "additionalInformation"
})
@XmlSeeAlso({
    CAEXBasicObject.Revision.class,
    CAEXFile.ExternalReference.class,
    AttributeValueRequirementType.class,
    siima.models.jaxb.caex.AttributeType.RefSemantic.class,
    MappingType.AttributeNameMapping.class,
    MappingType.InterfaceNameMapping.class,
    MappingType.class,
    siima.models.jaxb.caex.InternalElementType.RoleRequirements.class,
    siima.models.jaxb.caex.SystemUnitClassType.SupportedRoleClass.class,
    CAEXObject.class,
    CAEXFile.class
})
public class CAEXBasicObject {

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
    protected String changeMode;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link CAEXBasicObject.Description }
     *     
     */
    public CAEXBasicObject.Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link CAEXBasicObject.Description }
     *     
     */
    public void setDescription(CAEXBasicObject.Description value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link CAEXBasicObject.Version }
     *     
     */
    public CAEXBasicObject.Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link CAEXBasicObject.Version }
     *     
     */
    public void setVersion(CAEXBasicObject.Version value) {
        this.version = value;
    }

    public boolean isSetVersion() {
        return (this.version!= null);
    }

    /**
     * Gets the value of the revision property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the revision property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRevision().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXBasicObject.Revision }
     * 
     * 
     */
    public List<CAEXBasicObject.Revision> getRevision() {
        if (revision == null) {
            revision = new ArrayList<CAEXBasicObject.Revision>();
        }
        return this.revision;
    }

    public boolean isSetRevision() {
        return ((this.revision!= null)&&(!this.revision.isEmpty()));
    }

    public void unsetRevision() {
        this.revision = null;
    }

    /**
     * Gets the value of the copyright property.
     * 
     * @return
     *     possible object is
     *     {@link CAEXBasicObject.Copyright }
     *     
     */
    public CAEXBasicObject.Copyright getCopyright() {
        return copyright;
    }

    /**
     * Sets the value of the copyright property.
     * 
     * @param value
     *     allowed object is
     *     {@link CAEXBasicObject.Copyright }
     *     
     */
    public void setCopyright(CAEXBasicObject.Copyright value) {
        this.copyright = value;
    }

    public boolean isSetCopyright() {
        return (this.copyright!= null);
    }

    /**
     * Gets the value of the additionalInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAdditionalInformation() {
        if (additionalInformation == null) {
            additionalInformation = new ArrayList<Object>();
        }
        return this.additionalInformation;
    }

    public boolean isSetAdditionalInformation() {
        return ((this.additionalInformation!= null)&&(!this.additionalInformation.isEmpty()));
    }

    public void unsetAdditionalInformation() {
        this.additionalInformation = null;
    }

    /**
     * Gets the value of the changeMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeMode() {
        if (changeMode == null) {
            return "state";
        } else {
            return changeMode;
        }
    }

    /**
     * Sets the value of the changeMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeMode(String value) {
        this.changeMode = value;
    }

    public boolean isSetChangeMode() {
        return (this.changeMode!= null);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ChangeMode" type="{}ChangeMode" default="state" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Copyright {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ChangeMode")
        protected String changeMode;

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

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the changeMode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChangeMode() {
            if (changeMode == null) {
                return "state";
            } else {
                return changeMode;
            }
        }

        /**
         * Sets the value of the changeMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChangeMode(String value) {
            this.changeMode = value;
        }

        public boolean isSetChangeMode() {
            return (this.changeMode!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ChangeMode" type="{}ChangeMode" default="state" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Description {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ChangeMode")
        protected String changeMode;

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

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the changeMode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChangeMode() {
            if (changeMode == null) {
                return "state";
            } else {
                return changeMode;
            }
        }

        /**
         * Sets the value of the changeMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChangeMode(String value) {
            this.changeMode = value;
        }

        public boolean isSetChangeMode() {
            return (this.changeMode!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}CAEXBasicObject">
     *       &lt;sequence>
     *         &lt;element name="RevisionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="OldVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="NewVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="AuthorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "revisionDate",
        "oldVersion",
        "newVersion",
        "authorName",
        "comment"
    })
    public static class Revision
        extends CAEXBasicObject
    {

        @XmlElement(name = "RevisionDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar revisionDate;
        @XmlElement(name = "OldVersion")
        protected String oldVersion;
        @XmlElement(name = "NewVersion")
        protected String newVersion;
        @XmlElement(name = "AuthorName", required = true)
        protected String authorName;
        @XmlElement(name = "Comment")
        protected String comment;

        /**
         * Gets the value of the revisionDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRevisionDate() {
            return revisionDate;
        }

        /**
         * Sets the value of the revisionDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRevisionDate(XMLGregorianCalendar value) {
            this.revisionDate = value;
        }

        public boolean isSetRevisionDate() {
            return (this.revisionDate!= null);
        }

        /**
         * Gets the value of the oldVersion property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOldVersion() {
            return oldVersion;
        }

        /**
         * Sets the value of the oldVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOldVersion(String value) {
            this.oldVersion = value;
        }

        public boolean isSetOldVersion() {
            return (this.oldVersion!= null);
        }

        /**
         * Gets the value of the newVersion property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNewVersion() {
            return newVersion;
        }

        /**
         * Sets the value of the newVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNewVersion(String value) {
            this.newVersion = value;
        }

        public boolean isSetNewVersion() {
            return (this.newVersion!= null);
        }

        /**
         * Gets the value of the authorName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAuthorName() {
            return authorName;
        }

        /**
         * Sets the value of the authorName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAuthorName(String value) {
            this.authorName = value;
        }

        public boolean isSetAuthorName() {
            return (this.authorName!= null);
        }

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComment(String value) {
            this.comment = value;
        }

        public boolean isSetComment() {
            return (this.comment!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ChangeMode" type="{}ChangeMode" default="state" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Version {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ChangeMode")
        protected String changeMode;

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

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the changeMode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChangeMode() {
            if (changeMode == null) {
                return "state";
            } else {
                return changeMode;
            }
        }

        /**
         * Sets the value of the changeMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChangeMode(String value) {
            this.changeMode = value;
        }

        public boolean isSetChangeMode() {
            return (this.changeMode!= null);
        }

    }

}
