package org.cheetyan.weibospider.taskthread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cheetyan.weibospider.model.sina.Status;
import org.cheetyan.weibospider.plugins.Plugin;
import org.cheetyan.weibospider.spider.GetPublicTlAll;
import org.cheetyan.weibospider.spider.Timeline;
import org.cheetyan.weibospider.taskmodel.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinaTaskPublicThread implements Runnable {
	private Task t;
	private List<Plugin> pluginList;
	private GetPublicTlAll getpublictl;
	
	private static Logger logger = LoggerFactory.getLogger(SinaTaskPublicThread.class.getName());

	public SinaTaskPublicThread(Task t,List<Plugin> pluginList,Timeline tm) {
		this.t = t;
		this.pluginList = pluginList;
		this.getpublictl = new GetPublicTlAll(tm,t);
	}

	public void run() {
		getpublictl.setT(t);
		List<Status> st = new ArrayList<Status>();
		do {
			try {
				st = getpublictl.returnall();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (st != null && !st.isEmpty()) {
				this.save(st);
			}
			logger.info("save count : " + getpublictl.getCount());
			// sleep
			try {
				logger.info("sleep---------------");
				Thread.sleep(t.getMinute() * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (t.getCount() == -1 ? true : getpublictl.getCount() <= t.getCount());
		logger.info("task finished");
	}

	private void save(List<Status> statuslist) {
		for (Status st : statuslist) {
			for (Plugin s : pluginList) {
				s.save(t, st);
				s.save(t, st.getUser());
			}
		}
	}

	public Task getT() {
		return t;
	}

	public void setT(Task t) {
		this.t = t;
	}

	public List<Plugin> getPluginList() {
		return pluginList;
	}

	public void setPluginList(List<Plugin> pluginList) {
		this.pluginList = pluginList;
	}

	public GetPublicTlAll getGetpublictl() {
		return getpublictl;
	}

	public void setGetpublictl(GetPublicTlAll getpublictl) {
		this.getpublictl = getpublictl;
	}
	
}
