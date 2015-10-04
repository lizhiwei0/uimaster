//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.04 at 03:49:42 PM CST 
//


package org.shaolin.bmdp.datamodel.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UIMatrixType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UIMatrixType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bmdp.shaolin.org/datamodel/Page}UIComponentType">
 *       &lt;sequence>
 *         &lt;element name="init" type="{http://bmdp.shaolin.org/datamodel/Page}ExpressionPropertyType"/>
 *         &lt;element name="coordination" type="{http://bmdp.shaolin.org/datamodel/Page}ExpressionPropertyType"/>
 *         &lt;element name="column" type="{http://bmdp.shaolin.org/datamodel/Page}UITableColumnType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="actionGroup" type="{http://bmdp.shaolin.org/datamodel/Page}UITableActionGroupType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UIMatrixType", propOrder = {
    "init",
    "coordination",
    "columns",
    "actionGroups"
})
public class UIMatrixType
    extends UIComponentType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ExpressionPropertyType init;
    @XmlElement(required = true)
    protected ExpressionPropertyType coordination;
    @XmlElement(name = "column")
    protected List<UITableColumnType> columns;
    @XmlElement(name = "actionGroup")
    protected List<UITableActionGroupType> actionGroups;

    /**
     * Gets the value of the init property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionPropertyType }
     *     
     */
    public ExpressionPropertyType getInit() {
        return init;
    }

    /**
     * Sets the value of the init property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionPropertyType }
     *     
     */
    public void setInit(ExpressionPropertyType value) {
        this.init = value;
    }

    /**
     * Gets the value of the coordination property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionPropertyType }
     *     
     */
    public ExpressionPropertyType getCoordination() {
        return coordination;
    }

    /**
     * Sets the value of the coordination property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionPropertyType }
     *     
     */
    public void setCoordination(ExpressionPropertyType value) {
        this.coordination = value;
    }

    /**
     * Gets the value of the columns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the columns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColumns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UITableColumnType }
     * 
     * 
     */
    public List<UITableColumnType> getColumns() {
        if (columns == null) {
            columns = new ArrayList<UITableColumnType>();
        }
        return this.columns;
    }

    /**
     * Gets the value of the actionGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UITableActionGroupType }
     * 
     * 
     */
    public List<UITableActionGroupType> getActionGroups() {
        if (actionGroups == null) {
            actionGroups = new ArrayList<UITableActionGroupType>();
        }
        return this.actionGroups;
    }

}