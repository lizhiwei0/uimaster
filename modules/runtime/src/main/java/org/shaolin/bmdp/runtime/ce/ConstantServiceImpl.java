package org.shaolin.bmdp.runtime.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.shaolin.bmdp.datamodel.bediagram.BEDiagram;
import org.shaolin.bmdp.datamodel.bediagram.ConstantEntityType;
import org.shaolin.bmdp.datamodel.common.DiagramType;
import org.shaolin.bmdp.i18n.ExceptionConstants;
import org.shaolin.bmdp.runtime.cache.CacheManager;
import org.shaolin.bmdp.runtime.cache.ICache;
import org.shaolin.bmdp.runtime.entity.EntityAddedEvent;
import org.shaolin.bmdp.runtime.entity.EntityManager;
import org.shaolin.bmdp.runtime.entity.EntityNotFoundException;
import org.shaolin.bmdp.runtime.entity.EntityUpdatedEvent;
import org.shaolin.bmdp.runtime.entity.IEntityEventListener;
import org.shaolin.bmdp.runtime.spi.IConstantService;
import org.shaolin.bmdp.runtime.spi.ILifeCycleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constant Runtime Service for every single application.
 * 
 * @author wushaol
 *
 */
public class ConstantServiceImpl implements Serializable, IConstantService, IEntityEventListener<ConstantEntityType, BEDiagram>, ILifeCycleProvider {

	private static final long serialVersionUID = 7967098596510060235L;

	private static final Logger logger = LoggerFactory.getLogger(ConstantServiceImpl.class);
	
	private final ICache<String, IConstantEntity> serverConstantMap;
	
	private final List hierarchy = new ArrayList();
	
	private HierarchyAccessor accesor;
	
	public ConstantServiceImpl() {
		serverConstantMap = CacheManager.getInstance().getCache(
				"__sys_constants_cache", String.class, IConstantEntity.class);
	}
	
	public void setHierarchyAccessor(HierarchyAccessor accesor) {
		this.accesor = accesor;
	}
	
	/**
	 * Import data from data base, all constant entities can be overrided.
	 * 
	 * @param constants
	 */
	public void importData(IConstantEntity[] constants, List h) {
		for (IConstantEntity ce: constants) {
			logger.info("Load the constant entity: " + ce.getEntityName() + " from DB data.");
			serverConstantMap.put(ce.getEntityName(), ce);
		}
		this.hierarchy.addAll(h);
	}
	
	public void reloadData(IConstantEntity constant) {
		logger.info("Reload the constant: " + constant.getEntityName());
		this.serverConstantMap.put(constant.getEntityName(), constant);
	}
	
	public void reloadHierarchy(List hierarchy) {
		hierarchy.clear();
		hierarchy.addAll(hierarchy);
	}
	
	public List<IConstantEntity> getAppConstants(IConstantEntity condition, int offset, int count) {
		List<IConstantEntity> temp = new ArrayList<IConstantEntity>(serverConstantMap.getValues());
		
		int start = offset * count;
		int end = start + count;
		List<IConstantEntity> result = new ArrayList<IConstantEntity>(count);
		for (; (start < temp.size() && start < end); start++) {
			result.add(temp.get(start));
		}
		return result;
	}
	
	public List<IConstantEntity> getServerConstants(IConstantEntity condition, int offset, int count) {
		List<IConstantEntity> temp = new ArrayList<IConstantEntity>(serverConstantMap.getValues());
		
		int start = offset * count;
		int end = start + count;
		List<IConstantEntity> result = new ArrayList<IConstantEntity>(count);
		for (; (start < temp.size() && start < end); start++) {
			result.add(temp.get(start));
		}
		return result;
	}
	
	public int getServerConstantCount(IConstantEntity condition) {
		return serverConstantMap.size();
	}
	
	public IConstantEntity getConstantEntity(String ceName) {
		return this.loadFromCache(ceName, true);
	}
	
	public boolean hasConstantEntity(String ceName) {
		return this.loadFromCache(ceName, false) != null;
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
	}

	@Override
	public void notify(EntityAddedEvent<ConstantEntityType, BEDiagram> event) {
		loadFromCache(event.getEntity().getEntityName(), true);
	}

	@Override
	public void notify(EntityUpdatedEvent<ConstantEntityType, BEDiagram> event) {
	}

	@Override
	public void notifyLoadFinish(DiagramType diagram) {
	}

	@Override
	public void notifyAllLoadFinish() {
	}

	@Override
	public Class<ConstantEntityType> getEventType() {
		return ConstantEntityType.class;
	}
	
	private IConstantEntity loadFromCache(String ceName, boolean needException) {
		if (serverConstantMap.containsKey(ceName)) {
			return serverConstantMap.get(ceName);
		} else {
			try {
				Class<?> clazz = Class.forName(ceName, false, Thread.currentThread().getContextClassLoader());
				IConstantEntity ceEntity = (IConstantEntity)clazz.newInstance();
				serverConstantMap.put(ceEntity.getEntityName(), ceEntity);
				return ceEntity;
			} catch (Exception e) {
				if (needException) {
					throw new EntityNotFoundException(
							ExceptionConstants.EBOS_COMMON_002, e,
							new Object[] { ceName });
				}
			}
			return null;
		}
	}

	@Override
	public void startService() {
		
	}

	@Override
	public boolean readyToStop() {
		return true;
	}

	@Override
	public void stopService() {
		serverConstantMap.clear();
	}

	@Override
	public void reload() {
		
	}

	@Override
	public int getRunLevel() {
		return 0;
	}

	@Override
	public IConstantEntity getConstantItem(String ceName, int intValue) {
		IConstantEntity ce = this.getConstantEntity(ceName);
		if (ce == null) {
			throw new IllegalArgumentException(ceName + " is not existed!");
		}
		
		List<IConstantEntity> items = ce.getConstantList();
		for (IConstantEntity i : items) {
			if (i.getIntValue() == intValue) {
				return i;
			}
		}
		throw new IllegalArgumentException(ceName + "." + intValue + " item is not existed!");
	}
	
	@Override
	public void updateConstantItem(IConstantEntity item) {
		IConstantEntity ce = this.getConstantEntity(item.getEntityName());
		if (ce == null) {
			throw new IllegalArgumentException(item.getEntityName() + " is not existed!");
		}
		
		List<IConstantEntity> items = ce.getConstantList();
		for (int i=0; i<items.size(); i ++) {
			if (items.get(i).getIntValue() == item.getIntValue()) {
				items.set(i, item);
				break;
			}
		}
	}

	@Override
	public void addConstantItem(IConstantEntity item) {
		IConstantEntity ce = this.getConstantEntity(item.getEntityName());
		if (ce == null) {
			throw new IllegalArgumentException(item.getEntityName() + " is not existed!");
		}
		
		List<IConstantEntity> items = ce.getConstantList();
		for (int i=0; i<items.size(); i ++) {
			if (items.get(i).getIntValue() == item.getIntValue()) {
				items.set(i, item);
				return;
			}
		}
		items.add(item);
	}
	
	@Override
	public void addConstantItem(IConstantEntity item, IConstantEntity child) {
		addConstantItem(item);
		this.reloadData(child);
	}

	@Override
	public void removeConstantItem(IConstantEntity item) {
		IConstantEntity ce = this.getConstantEntity(item.getEntityName());
		if (ce == null) {
			throw new IllegalArgumentException(item.getEntityName() + " is not existed!");
		}
		
		List<IConstantEntity> items = ce.getConstantList();
		for (int i=0; i<items.size(); i ++) {
			if (items.get(i).getIntValue() == item.getIntValue()) {
				items.remove(i);
				break;
			}
		}
	}
	
	
	@Override
	public IConstantEntity getChildren(IConstantEntity item) {
		return accesor.getChild(hierarchy, item.getEntityName(), item.getIntValue());
	}

	@Override
	public IConstantEntity getChildren(String ceName, int intValue) {
		return accesor.getChild(hierarchy, ceName, intValue);
	}

}
