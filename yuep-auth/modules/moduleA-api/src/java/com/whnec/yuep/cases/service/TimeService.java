package com.whnec.yuep.cases.service;


public interface TimeService {

	/**
	 * 时间消息通知
	 */
	String MSG_TIMENOTIFY = "Message.TimeNotify";

	/**
	 * 服务名
	 */
	String SERVICE_NAME = "Time.Service";

	/**
	 * 获取当前系统时间
	 * 
	 * @return 当前系统时间
	 */
	long getCurrentTime();

	/**
	 * 开始定时通知
	 * 
	 * @param interval
	 *            定时通知时间间隔
	 */
	void startTimeNotify(int interval);

	/**
	 * 停止定时通知
	 */
	void stopTimeNotify();

	/**
	 * ${link com.yuep.base.exception.YuepException}异常测试方法，调用该方法将直接抛出${link
	 * com.yuep.base.exception.YuepException}异常
	 */
	void getYuepException();

	/**
	 * 自定义异常测试方法，调用该方法将直接抛出${link
	 * com.yuep.common.exception.UserDefineException}异常
	 * 
	 * @throws com.yuep.common.exception.UserDefineException
	 */
	void getUserDefineException() throws Throwable;
}
