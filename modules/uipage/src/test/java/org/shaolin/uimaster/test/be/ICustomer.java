/*
 *
 * This file is automatically generated on Mon Jun 16 22:29:19 CST 2014
 */

    
package org.shaolin.uimaster.test.be;
        
import org.shaolin.bmdp.runtime.be.IBusinessEntity;
import org.shaolin.bmdp.runtime.be.IExtensibleEntity;
import org.shaolin.uimaster.test.ce.Gender;


/**
 * null
 * 
 * This code is generated automatically, any change will be replaced after rebuild.
 * 
 *
 */

public interface ICustomer 
    extends IBusinessEntity, IExtensibleEntity
{
    public final static String ENTITY_NAME = "org.shaolin.uimaster.test.be.Customer";
    
 
    /**
     *  get id
     *
     *  @return id
     */
    public long getId();

    /**
     *  get name
     *
     *  @return name
     */
    public java.lang.String getName();

    /**
     *  set id
     */
    public void setId(long id);

    /**
     *  set name
     */
    public void setName(java.lang.String name);

    public Gender getGender() ;

	public void setGender(Gender gender);
}

        