//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.18 at 12:37:13 PM EEST 
//


package siima.models.jaxb.caex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangeMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChangeMode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="state"/&gt;
 *     &lt;enumeration value="create"/&gt;
 *     &lt;enumeration value="delete"/&gt;
 *     &lt;enumeration value="change"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ChangeMode")
@XmlEnum
public enum ChangeMode {

    @XmlEnumValue("state")
    STATE("state"),
    @XmlEnumValue("create")
    CREATE("create"),
    @XmlEnumValue("delete")
    DELETE("delete"),
    @XmlEnumValue("change")
    CHANGE("change");
    private final String value;

    ChangeMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChangeMode fromValue(String v) {
        for (ChangeMode c: ChangeMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
