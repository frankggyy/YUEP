package com.whnec.yuep.cases.service;


public interface TimeService {

	/**
	 * ʱ����Ϣ֪ͨ
	 */
	String MSG_TIMENOTIFY = "Message.TimeNotify";

	/**
	 * ������
	 */
	String SERVICE_NAME = "Time.Service";

	/**
	 * ��ȡ��ǰϵͳʱ��
	 * 
	 * @return ��ǰϵͳʱ��
	 */
	long getCurrentTime();

	/**
	 * ��ʼ��ʱ֪ͨ
	 * 
	 * @param interval
	 *            ��ʱ֪ͨʱ����
	 */
	void startTimeNotify(int interval);

	/**
	 * ֹͣ��ʱ֪ͨ
	 */
	void stopTimeNotify();

	/**
	 * ${link com.yuep.base.exception.YuepException}�쳣���Է��������ø÷�����ֱ���׳�${link
	 * com.yuep.base.exception.YuepException}�쳣
	 */
	void getYuepException();

	/**
	 * �Զ����쳣���Է��������ø÷�����ֱ���׳�${link
	 * com.yuep.common.exception.UserDefineException}�쳣
	 * 
	 * @throws com.yuep.common.exception.UserDefineException
	 */
	void getUserDefineException() throws Throwable;
}
