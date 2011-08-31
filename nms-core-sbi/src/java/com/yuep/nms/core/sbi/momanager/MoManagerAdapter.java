/**
 * 
 */
package com.yuep.nms.core.sbi.momanager;

import java.util.List;

import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.momanager.service.MoManager;

/**
 * mo管理实现类
 * @author Owner
 *
 */
public class MoManagerAdapter implements MoManager {

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.String)
	 */
	@Override
	public Mo createManagedDomain(MoNaming parent, String domainName,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.model.ManagedNodeProperty, java.lang.String)
	 */
	@Override
	public Mo createManagedNode(MoNaming parent,
			ManagedNodeProperty managedNodeProperty, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming)
	 */
	@Override
	public void deleteManagedDomain(MoNaming domainNaming) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming)
	 */
	@Override
	public void deleteManagedNode(MoNaming nm) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#getAllMos()
	 */
	@Override
	public List<Mo> getAllMos() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#getChildrenMos(com.yuep.nms.core.common.mocore.service.MoFilter, com.yuep.nms.core.common.mocore.naming.MoNaming[])
	 */
	@Override
	public List<Mo> getChildrenMos(MoFilter moFilter, MoNaming... parents) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
	 */
	@Override
	public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#getMo(com.yuep.nms.core.common.mocore.naming.MoNaming)
	 */
	@Override
	public Mo getMo(MoNaming moNaming) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#getMos(com.yuep.nms.core.common.mocore.service.MoFilter)
	 */
	@Override
	public List<Mo> getMos(MoFilter moFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuep.nms.core.common.momanager.service.MoManager#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
	 */
	@Override
	public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
		// TODO Auto-generated method stub

	}

}
