//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.07 at 02:10:06 PM CST 
//


package org.shaolin.bmdp.datamodel.page;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UITableSelectModeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UITableSelectModeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Normal"/>
 *     &lt;enumeration value="Single"/>
 *     &lt;enumeration value="Multiple"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UITableSelectModeType")
@XmlEnum
public enum UITableSelectModeType {

    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("Single")
    SINGLE("Single"),
    @XmlEnumValue("Multiple")
    MULTIPLE("Multiple");
    private final String value;

    UITableSelectModeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UITableSelectModeType fromValue(String v) {
        for (UITableSelectModeType c: UITableSelectModeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
