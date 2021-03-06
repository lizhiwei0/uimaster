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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.shaolin.bmdp.datamodel.common.ExpressionType;
import org.shaolin.bmdp.datamodel.common.NameExpressionType;


/**
 * <p>Java class for ExpressionParamType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExpressionParamType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bmdp.shaolin.org/datamodel/Page}DataParamType">
 *       &lt;sequence>
 *         &lt;element name="expression" type="{http://bmdp.shaolin.org/datamodel/Common}ExpressionType"/>
 *         &lt;element name="uiToDataExpression" type="{http://bmdp.shaolin.org/datamodel/Common}NameExpressionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpressionParamType", propOrder = {
    "expression",
    "uiToDataExpression"
})
public class ExpressionParamType
    extends DataParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ExpressionType expression;
    protected NameExpressionType uiToDataExpression;

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionType }
     *     
     */
    public ExpressionType getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionType }
     *     
     */
    public void setExpression(ExpressionType value) {
        this.expression = value;
    }

    /**
     * Gets the value of the uiToDataExpression property.
     * 
     * @return
     *     possible object is
     *     {@link NameExpressionType }
     *     
     */
    public NameExpressionType getUiToDataExpression() {
        return uiToDataExpression;
    }

    /**
     * Sets the value of the uiToDataExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameExpressionType }
     *     
     */
    public void setUiToDataExpression(NameExpressionType value) {
        this.uiToDataExpression = value;
    }

}
