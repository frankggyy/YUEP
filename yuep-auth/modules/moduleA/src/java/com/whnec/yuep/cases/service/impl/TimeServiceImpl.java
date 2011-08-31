package com.whnec.yuep.cases.service.impl;

import java.io.Serializable;
import java.util.Date;

import com.whnec.yuep.cases.exception.UserDefineException;
import com.yuep.base.exception.YuepException;
import com.yuep.base.scheduler.FixedIntervalSchedulerJob;
import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.base.scheduler.SchedulerExecutorFactoryImpl;
import com.yuep.base.scheduler.SchedulerJob;
import com.yuep.core.bootstrap.def.logger.Logger;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.logger.def.YuepLoggerFactory;
import com.yuep.core.message.def.BaseMessage;

public class TimeServiceImpl implements
		com.whnec.yuep.cases.service.TimeService {
	private static final String JOB_NAME = "Time";
	private static final String JOB_GROUPNAME = "Notify";
	private Logger logger;

	private SchedulerExecutor schedulerExecutor = new SchedulerExecutorFactoryImpl()
			.createSchedulerExecutor();

	@Override
	public long getCurrentTime() {
		getLogger().info("Method:getCurrentTime invoked.");
		return System.currentTimeMillis();
	}

	@Override
	public void startTimeNotify(int interval) {
		getLogger().info("Method:startTimeNotify invoked.");
		schedulerExecutor.startSchedulerJob(new TimeNotifyJob(null, null,
				interval));
	}

	@Override
	public void stopTimeNotify() {
		getLogger().info("Method:StopTimeNotify invoked.");
		SchedulerJob job = schedulerExecutor.getSchedulerJob(JOB_NAME,
				JOB_GROUPNAME);
		if (job != null
				&& schedulerExecutor.isSchedulerJobShutdown(JOB_NAME,
						JOB_GROUPNAME)) {
			schedulerExecutor.shutdownSchedulerJob(JOB_NAME, JOB_GROUPNAME);
		}
	}

	@Override
	public void getYuepException() {
		getLogger().info("Method:getYuepException invoked.");
		throw new YuepException(20001,
				new Exception("root cause of exception"), toString());
	}

	@Override
	public void getUserDefineException() throws Throwable {
		getLogger().info("Method:getUserDefineException invoked.");
		throw new UserDefineException("wrapped exception", new Exception(
				"root cause of exception"));
	}

	private final Logger getLogger() {
		if (logger == null) {
			YuepLoggerFactory factory = CoreContext.getInstance().local()
					.getService("yuepLoggerFactory", YuepLoggerFactory.class);
			logger = factory.getLogger("TimeService");
		}
		return logger;
	}

	private final class TimeNotifyJob extends FixedIntervalSchedulerJob
			implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9096435478181793484L;

		public TimeNotifyJob(Date starttime, Date endtime, int interval) {
			super(JOB_NAME, JOB_GROUPNAME, starttime, endtime, interval);
		}

		@Override
		public void execute() {
			CoreContext.getInstance().publish(MSG_TIMENOTIFY,
					new BaseMessage(MSG_TIMENOTIFY, "Time Notify Message"));
		}
	}
}
