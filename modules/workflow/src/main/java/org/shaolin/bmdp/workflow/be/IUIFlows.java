/*
 *
 * This file is automatically generated on Fri Aug 07 00:35:53 CST 2015
 */

    
package org.shaolin.bmdp.workflow.be;
        
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.shaolin.bmdp.datamodel.bediagram.*;
import org.shaolin.bmdp.datamodel.common.*;
import org.shaolin.bmdp.runtime.be.IBusinessEntity;
import org.shaolin.bmdp.runtime.be.IExtensibleEntity;
import org.shaolin.bmdp.runtime.be.IPersistentEntity;
import org.shaolin.bmdp.runtime.be.IHistoryEntity;
import org.shaolin.bmdp.runtime.be.BEExtensionInfo;

import org.shaolin.bmdp.runtime.spi.IConstantService;

import org.shaolin.bmdp.runtime.AppContext;

import org.shaolin.bmdp.runtime.ce.CEUtil;

import org.shaolin.bmdp.workflow.ce.*;


/**
 * null
 * 
 * This code is generated automatically, any change will be replaced after rebuild.
 * 
 *
 */

public interface IUIFlows 
    extends IPersistentEntity, IExtensibleEntity
{
    public final static String ENTITY_NAME = "org.shaolin.bmdp.workflow.be.UIFlows";
    
 
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
     *  get flow
     *
     *  @return flow
     */
    public java.lang.String getFlow();

    /**
     *  get moduleItemId
     *
     *  @return moduleItemId
     */
    public long getModuleItemId();

    /**
     *  get moduleType
     *
     *  @return moduleType
     */
    public ModuleType getModuleType();

    /**
     *  set id
     */
    public void setId(long id);

    /**
     *  set name
     */
    public void setName(java.lang.String name);

    /**
     *  set flow
     */
    public void setFlow(java.lang.String flow);

    /**
     *  set moduleItemId
     */
    public void setModuleItemId(long moduleItemId);

    /**
     *  set moduleType
     */
    public void setModuleType(ModuleType moduleType);


}

        