//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.18 at 12:37:13 PM EEST 
//


package siima.models.jaxb.caex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}CAEXBasicObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExternalReference" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXBasicObject"&gt;
 *                 &lt;attribute name="Path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="Alias" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InstanceHierarchy" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXObject"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="InternalElement" type="{}InternalElementType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InterfaceClassLib" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXObject"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="InterfaceClass" type="{}InterfaceFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RoleClassLib" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXObject"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RoleClass" type="{}RoleFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SystemUnitClassLib" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}CAEXObject"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="SystemUnitClass" type="{}SystemUnitFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="FileName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="SchemaVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2.15" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "externalReference",
    "instanceHierarchy",
    "interfaceClassLib",
    "roleClassLib",
    "systemUnitClassLib"
})
@XmlRootElement(name = "CAEXFile")
public class CAEXFile
    extends CAEXBasicObject
{

    @XmlElement(name = "ExternalReference")
    protected List<CAEXFile.ExternalReference> externalReference;
    @XmlElement(name = "InstanceHierarchy")
    protected List<CAEXFile.InstanceHierarchy> instanceHierarchy;
    @XmlElement(name = "InterfaceClassLib")
    protected List<CAEXFile.InterfaceClassLib> interfaceClassLib;
    @XmlElement(name = "RoleClassLib")
    protected List<CAEXFile.RoleClassLib> roleClassLib;
    @XmlElement(name = "SystemUnitClassLib")
    protected List<CAEXFile.SystemUnitClassLib> systemUnitClassLib;
    @XmlAttribute(name = "FileName", required = true)
    protected String fileName;
    @XmlAttribute(name = "SchemaVersion", required = true)
    protected String schemaVersion;

    /**
     * Gets the value of the externalReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXFile.ExternalReference }
     * 
     * 
     */
    public List<CAEXFile.ExternalReference> getExternalReference() {
        if (externalReference == null) {
            externalReference = new ArrayList<CAEXFile.ExternalReference>();
        }
        return this.externalReference;
    }

    /**
     * Gets the value of the instanceHierarchy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanceHierarchy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanceHierarchy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXFile.InstanceHierarchy }
     * 
     * 
     */
    public List<CAEXFile.InstanceHierarchy> getInstanceHierarchy() {
        if (instanceHierarchy == null) {
            instanceHierarchy = new ArrayList<CAEXFile.InstanceHierarchy>();
        }
        return this.instanceHierarchy;
    }

    /**
     * Gets the value of the interfaceClassLib property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interfaceClassLib property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceClassLib().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXFile.InterfaceClassLib }
     * 
     * 
     */
    public List<CAEXFile.InterfaceClassLib> getInterfaceClassLib() {
        if (interfaceClassLib == null) {
            interfaceClassLib = new ArrayList<CAEXFile.InterfaceClassLib>();
        }
        return this.interfaceClassLib;
    }

    /**
     * Gets the value of the roleClassLib property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleClassLib property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleClassLib().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXFile.RoleClassLib }
     * 
     * 
     */
    public List<CAEXFile.RoleClassLib> getRoleClassLib() {
        if (roleClassLib == null) {
            roleClassLib = new ArrayList<CAEXFile.RoleClassLib>();
        }
        return this.roleClassLib;
    }

    /**
     * Gets the value of the systemUnitClassLib property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the systemUnitClassLib property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSystemUnitClassLib().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAEXFile.SystemUnitClassLib }
     * 
     * 
     */
    public List<CAEXFile.SystemUnitClassLib> getSystemUnitClassLib() {
        if (systemUnitClassLib == null) {
            systemUnitClassLib = new ArrayList<CAEXFile.SystemUnitClassLib>();
        }
        return this.systemUnitClassLib;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the schemaVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaVersion() {
        if (schemaVersion == null) {
            return "2.15";
        } else {
            return schemaVersion;
        }
    }

    /**
     * Sets the value of the schemaVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaVersion(String value) {
        this.schemaVersion = value;
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
     *       &lt;attribute name="Path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="Alias" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ExternalReference
        extends CAEXBasicObject
    {

        @XmlAttribute(name = "Path", required = true)
        protected String path;
        @XmlAttribute(name = "Alias", required = true)
        protected String alias;

        /**
         * Gets the value of the path property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPath() {
            return path;
        }

        /**
         * Sets the value of the path property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPath(String value) {
            this.path = value;
        }

        /**
         * Gets the value of the alias property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlias() {
            return alias;
        }

        /**
         * Sets the value of the alias property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlias(String value) {
            this.alias = value;
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
     *     &lt;extension base="{}CAEXObject"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="InternalElement" type="{}InternalElementType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "internalElement"
    })
    public static class InstanceHierarchy
        extends CAEXObject
    {

        @XmlElement(name = "InternalElement")
        protected List<InternalElementType> internalElement;

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
     *       &lt;sequence&gt;
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
    @XmlType(name = "", propOrder = {
        "interfaceClass"
    })
    public static class InterfaceClassLib
        extends CAEXObject
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{}CAEXObject"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="RoleClass" type="{}RoleFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "roleClass"
    })
    public static class RoleClassLib
        extends CAEXObject
    {

        @XmlElement(name = "RoleClass")
        protected List<RoleFamilyType> roleClass;

        /**
         * Gets the value of the roleClass property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roleClass property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoleClass().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RoleFamilyType }
         * 
         * 
         */
        public List<RoleFamilyType> getRoleClass() {
            if (roleClass == null) {
                roleClass = new ArrayList<RoleFamilyType>();
            }
            return this.roleClass;
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
     *     &lt;extension base="{}CAEXObject"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="SystemUnitClass" type="{}SystemUnitFamilyType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "systemUnitClass"
    })
    public static class SystemUnitClassLib
        extends CAEXObject
    {

        @XmlElement(name = "SystemUnitClass")
        protected List<SystemUnitFamilyType> systemUnitClass;

        /**
         * Gets the value of the systemUnitClass property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the systemUnitClass property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSystemUnitClass().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SystemUnitFamilyType }
         * 
         * 
         */
        public List<SystemUnitFamilyType> getSystemUnitClass() {
            if (systemUnitClass == null) {
                systemUnitClass = new ArrayList<SystemUnitFamilyType>();
            }
            return this.systemUnitClass;
        }

    }

}
