/*
 * $Id: SmCoreService.java, 2011-3-24 上午11:19:32 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

/**
 * <p>
 * Title: SmCoreService
 * </p>
 * <p>
 * Description:安全内核服务（面向server内部模块）
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:19:32
 * modified [who date description]
 * check [who date description]
 */
public interface SmCoreService extends AuthenticationService,PermissionService,MgmtScopeService,UserProfileService,OperationLogService,SmCoreManagerService{

}
