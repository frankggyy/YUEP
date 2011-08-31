package com.yuep.nms.core.common.sbi.service;

import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.model.SbiInitialParameter;


/**
 * <p>
 * Title: SbiSysMgr
 * </p>
 * <p>
 * SBI网元管理接口
 * </p>
 * 
 * @author pl
 * created 2011-4-16 下午04:20:40
 * modified [who date description]
 * check [who date description]
 */
public interface SbiSysMgr {
    /**
     * 提供给Server调用，检查是否ping通
     */
    public void ping();

    /**
     * 关闭sbi，直接进程退出
     */
    public void shutdownSbi();

    /**
     * 初始化SBI参数
     * @param parameter
     */
    public void initParameters(SbiInitialParameter parameter);

    /**
     * 添加网元
     * @param ne
     */
    public void addNe(NeLoginParameter ne);

    /**
     * 删除网元
     * @param ne
     */
    public void deleteNe(NeLoginParameter ne);

    /**
     * 更新网元通讯参数
     * @param ne
     */
    public void updateNeLoginParam(NeLoginParameter ne);

}
