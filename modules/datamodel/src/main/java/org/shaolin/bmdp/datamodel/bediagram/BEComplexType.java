//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.27 at 02:34:46 PM CST 
//


package org.shaolin.bmdp.datamodel.bediagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BEComplexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BEComplexType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bmdp.shaolin.org/datamodel/BEDiagram}BEType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BEComplexType")
@XmlSeeAlso({
    CEObjRefType.class,
    ObjectRefType.class,
    BECollectionType.class
})
public class BEComplexType
    extends BEType
    implements Serializable
{

    private final static long serialVersionUID = 1L;

}
