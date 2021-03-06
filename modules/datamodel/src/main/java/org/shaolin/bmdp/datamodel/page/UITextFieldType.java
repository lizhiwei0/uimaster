//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.20 at 04:07:50 PM CST 
//


package org.shaolin.bmdp.datamodel.page;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UITextFieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UITextFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bmdp.shaolin.org/datamodel/Page}UITextComponentType">
 *       &lt;sequence>
 *         &lt;element name="maxLength" type="{http://bmdp.shaolin.org/datamodel/Page}PropertyValueType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UITextFieldType", propOrder = {
    "maxLength"
})
@XmlSeeAlso({
    UIPasswordFieldType.class
})
public class UITextFieldType
    extends UITextComponentType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected PropertyValueType maxLength;

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyValueType }
     *     
     */
    public PropertyValueType getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyValueType }
     *     
     */
    public void setMaxLength(PropertyValueType value) {
        this.maxLength = value;
    }

}
