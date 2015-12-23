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
 * <p>Java class for SearchView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchView">
 *   &lt;complexContent>
 *     &lt;extension base="{}LinearLayout">
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}iconifiedByDefault"/>
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}imeOptions"/>
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}inputType"/>
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}maxWidth"/>
 *       &lt;attribute ref="{http://schemas.android.com/apk/res/android}queryHint"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchView")
@XmlRootElement(name = "SearchView")
public class SearchView
    extends LinearLayout
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "iconifiedByDefault", namespace = "http://schemas.android.com/apk/res/android")
    protected String iconifiedByDefault;
    @XmlAttribute(name = "imeOptions", namespace = "http://schemas.android.com/apk/res/android")
    protected String imeOptions;
    @XmlAttribute(name = "inputType", namespace = "http://schemas.android.com/apk/res/android")
    protected String inputType;
    @XmlAttribute(name = "maxWidth", namespace = "http://schemas.android.com/apk/res/android")
    protected String maxWidth;
    @XmlAttribute(name = "queryHint", namespace = "http://schemas.android.com/apk/res/android")
    protected String queryHint;

    /**
     * Gets the value of the iconifiedByDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconifiedByDefault() {
        return iconifiedByDefault;
    }

    /**
     * Sets the value of the iconifiedByDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconifiedByDefault(String value) {
        this.iconifiedByDefault = value;
    }

    /**
     * Gets the value of the imeOptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImeOptions() {
        return imeOptions;
    }

    /**
     * Sets the value of the imeOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImeOptions(String value) {
        this.imeOptions = value;
    }

    /**
     * Gets the value of the inputType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * Sets the value of the inputType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputType(String value) {
        this.inputType = value;
    }

    /**
     * Gets the value of the maxWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxWidth() {
        return maxWidth;
    }

    /**
     * Sets the value of the maxWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxWidth(String value) {
        this.maxWidth = value;
    }

    /**
     * Gets the value of the queryHint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryHint() {
        return queryHint;
    }

    /**
     * Sets the value of the queryHint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryHint(String value) {
        this.queryHint = value;
    }

}
