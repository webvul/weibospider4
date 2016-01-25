/**
 * 
 */
package org.cheetyan.weibospider.taskthread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.plugins.Plugin;
import org.cheetyan.weibospider.spider.GetPublicTlAllTX;
import org.cheetyan.weibospider.spider.TimelineTX;
import org.cheetyan.weibospider.taskmodel.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class TXTaskPublicThread implements Runnable {
	private Task t;
	private List<Plugin> pluginList;
	private GetPublicTlAllTX getpublictl;
	//
	private Logger logger = LoggerFactory.getLogger(TXTaskPublicThread.class.getName());

	public TXTaskPublicThread(Task t,List<Plugin> pluginList,TimelineTX tmtx) {
		this.t = t;
		this.pluginList = pluginList;
		this.getpublictl = new GetPublicTlAllTX(t,tmtx);
	}

	public void run() {
		getpublictl.setT(t);
		List<StatusTX> st = new ArrayList<StatusTX>();
		List<UserTX> usertxlist = null;
		do {
			Map<List<StatusTX>, List<UserTX>> map = null;
			try {
				map = getpublictl.returnpagestream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (Map.Entry<List<StatusTX>, List<UserTX>> entry : map.entrySet()) {
				st = entry.getKey();
				usertxlist = entry.getValue();
			}
			int size = st.size();
			for (int i = 0; i < size; i++) {
				if (st.get(i) != null)
					this.savestatustx(st.get(i));
				if (usertxlist.get(i) != null)
					this.saveusertx(usertxlist.get(i));
			}
			// sleep
			try {
				logger.info("sleep---------------");
				Thread.sleep(t.getMinute() * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (t.getCount() == -1 ? true : getpublictl.getCount() <= t.getCount());
		logger.info("task finished");
	}

	private void savestatustx(StatusTX st) {
		for (Plugin plugin : pluginList)
			plugin.save(t, st);
	}

	private void saveusertx(UserTX usertx) {
		for (Plugin plugin : pluginList)
			plugin.save(t, usertx);
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

	public GetPublicTlAllTX getGetpublictl() {
		return getpublictl;
	}

	public void setGetpublictl(GetPublicTlAllTX getpublictl) {
		this.getpublictl = getpublictl;
	}
	
}
