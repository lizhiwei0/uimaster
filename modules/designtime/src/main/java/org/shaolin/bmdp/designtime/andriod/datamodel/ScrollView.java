//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.28 at 04:10:22 PM CST 
//


package org.shaolin.bmdp.designtime.andriod.datamodel;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScrollView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScrollView">
 *   &lt;complexContent>
 *     &lt;extension base="{}FrameLayout">
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}fillViewport"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScrollView")
@XmlRootElement(name = "ScrollView")
public class ScrollView
    extends FrameLayout
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "fillViewport", namespace = "http://schemas.android.com/apk/res/android")
    protected String fillViewport;

    /**
     * Gets the value of the fillViewport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillViewport() {
        return fillViewport;
    }

    /**
     * Sets the value of the fillViewport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillViewport(String value) {
        this.fillViewport = value;
    }

}
