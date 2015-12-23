//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.27 at 12:53:47 PM CST 
//


package org.shaolin.bmdp.datamodel.bediagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.shaolin.bmdp.datamodel.common.EntityType;


/**
 * <p>Java class for BusinessEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessEntityType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bmdp.shaolin.org/datamodel/Common}EntityType">
 *       &lt;sequence>
 *         &lt;element name="parentObject" type="{http://bmdp.shaolin.org/datamodel/BEDiagram}ObjectRefType" minOccurs="0"/>
 *         &lt;element name="member" type="{http://bmdp.shaolin.org/datamodel/BEDiagram}MemberType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="persistentConfig" type="{http://bmdp.shaolin.org/datamodel/BEDiagram}PersistentConfigType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="validatorConfig" type="{http://bmdp.shaolin.org/datamodel/BEDiagram}ValidatorConfigType" minOccurs="0"/>
 *         &lt;element name="searchMethod" type="{http://bmdp.shaolin.org/datamodel/BEDiagram}SearchMethodType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="needEquals" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="needHistory" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="needPersist" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="needTask" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="needUIEntity" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="needUITableEditor" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="extensible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessEntityType", propOrder = {
    "parentObject",
    "members",
    "persistentConfigs",
    "validatorConfig",
    "searchMethods"
})
public class BusinessEntityType
    extends EntityType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ObjectRefType parentObject;
    @XmlElement(name = "member")
    protected List<MemberType> members;
    @XmlElement(name = "persistentConfig")
    protected List<PersistentConfigType> persistentConfigs;
    protected ValidatorConfigType validatorConfig;
    @XmlElement(name = "searchMethod")
    protected List<SearchMethodType> searchMethods;
    @XmlAttribute(name = "needEquals")
    protected Boolean needEquals;
    @XmlAttribute(name = "needHistory")
    protected Boolean needHistory;
    @XmlAttribute(name = "needPersist")
    protected Boolean needPersist;
    @XmlAttribute(name = "needTask")
    protected Boolean needTask;
    @XmlAttribute(name = "needUIEntity")
    protected Boolean needUIEntity;
    @XmlAttribute(name = "needUITableEditor")
    protected Boolean needUITableEditor;
    @XmlAttribute(name = "extensible")
    protected Boolean extensible;
    @XmlAttribute(name = "abbreviation")
    protected String abbreviation;

    /**
     * Gets the value of the parentObject property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRefType }
     *     
     */
    public ObjectRefType getParentObject() {
        return parentObject;
    }

    /**
     * Sets the value of the parentObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRefType }
     *     
     */
    public void setParentObject(ObjectRefType value) {
        this.parentObject = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the members property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMembers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MemberType }
     * 
     * 
     */
    public List<MemberType> getMembers() {
        if (members == null) {
            members = new ArrayList<MemberType>();
        }
        return this.members;
    }

    /**
     * Gets the value of the persistentConfigs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the persistentConfigs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersistentConfigs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PersistentConfigType }
     * 
     * 
     */
    public List<PersistentConfigType> getPersistentConfigs() {
        if (persistentConfigs == null) {
            persistentConfigs = new ArrayList<PersistentConfigType>();
        }
        return this.persistentConfigs;
    }

    /**
     * Gets the value of the validatorConfig property.
     * 
     * @return
     *     possible object is
     *     {@link ValidatorConfigType }
     *     
     */
    public ValidatorConfigType getValidatorConfig() {
        return validatorConfig;
    }

    /**
     * Sets the value of the validatorConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatorConfigType }
     *     
     */
    public void setValidatorConfig(ValidatorConfigType value) {
        this.validatorConfig = value;
    }

    /**
     * Gets the value of the searchMethods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchMethods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchMethods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchMethodType }
     * 
     * 
     */
    public List<SearchMethodType> getSearchMethods() {
        if (searchMethods == null) {
            searchMethods = new ArrayList<SearchMethodType>();
        }
        return this.searchMethods;
    }

    /**
     * Gets the value of the needEquals property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedEquals() {
        if (needEquals == null) {
            return true;
        } else {
            return needEquals;
        }
    }

    /**
     * Sets the value of the needEquals property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedEquals(Boolean value) {
        this.needEquals = value;
    }

    /**
     * Gets the value of the needHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedHistory() {
        if (needHistory == null) {
            return false;
        } else {
            return needHistory;
        }
    }

    /**
     * Sets the value of the needHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedHistory(Boolean value) {
        this.needHistory = value;
    }

    /**
     * Gets the value of the needPersist property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedPersist() {
        if (needPersist == null) {
            return false;
        } else {
            return needPersist;
        }
    }

    /**
     * Sets the value of the needPersist property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedPersist(Boolean value) {
        this.needPersist = value;
    }

    /**
     * Gets the value of the needTask property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedTask() {
        if (needTask == null) {
            return false;
        } else {
            return needTask;
        }
    }

    /**
     * Sets the value of the needTask property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedTask(Boolean value) {
        this.needTask = value;
    }

    /**
     * Gets the value of the needUIEntity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedUIEntity() {
        if (needUIEntity == null) {
            return false;
        } else {
            return needUIEntity;
        }
    }

    /**
     * Sets the value of the needUIEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedUIEntity(Boolean value) {
        this.needUIEntity = value;
    }

    /**
     * Gets the value of the needUITableEditor property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNeedUITableEditor() {
        if (needUITableEditor == null) {
            return false;
        } else {
            return needUITableEditor;
        }
    }

    /**
     * Sets the value of the needUITableEditor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedUITableEditor(Boolean value) {
        this.needUITableEditor = value;
    }

    /**
     * Gets the value of the extensible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExtensible() {
        if (extensible == null) {
            return true;
        } else {
            return extensible;
        }
    }

    /**
     * Sets the value of the extensible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExtensible(Boolean value) {
        this.extensible = value;
    }

    /**
     * Gets the value of the abbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the value of the abbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbreviation(String value) {
        this.abbreviation = value;
    }

}
