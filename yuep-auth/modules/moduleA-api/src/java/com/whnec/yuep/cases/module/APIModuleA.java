/**
 * 
 */
package com.whnec.yuep.cases.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreUtils;

/**
 * @author wanghu
 * 
 */
public final class APIModuleA extends DefaultModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
	 */
	@Override
	public void start() {
	    CoreUtils.initModuleLogger(this);

		getLogger().info(getModuleName() + " start.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
	 */
	@Override
	public void stop() {

		getLogger().info(getModuleName() + " stopped.");
	}

}
