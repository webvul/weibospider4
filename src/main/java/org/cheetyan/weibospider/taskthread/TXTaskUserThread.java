package org.cheetyan.weibospider.taskthread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.plugins.Plugin;
import org.cheetyan.weibospider.spider.GetUserTlAllTX;
import org.cheetyan.weibospider.spider.TimelineTX;
import org.cheetyan.weibospider.taskmodel.Task;

public class TXTaskUserThread implements Runnable {
	private Task t;
	private List<Plugin> pluginList;
	private GetUserTlAllTX getusertl;
	//
	private List<String> usernames = new ArrayList<String>();
	private List<String> keywords = new ArrayList<String>();
	private int minute;
	private String taskname;

	public TXTaskUserThread(Task t,List<Plugin> pluginList,TimelineTX tmtx) {
		this.t = t;
		this.usernames = t.getUsernames();
		this.keywords = t.getKeywords();
		this.minute = t.getMinute();
		this.taskname = t.getName();
		this.pluginList = pluginList;
		getusertl = new GetUserTlAllTX(t,tmtx);
	}
	public void run() {
		if (!usernames.isEmpty()) {
			while (true) {
				for (String us : usernames) {
					List<StatusTX> st = new ArrayList<StatusTX>();
					UserTX usertx = null;
					try {
						usertx = getusertl.getUserInfo();
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.saveusertx(usertx);
					do {
						try {
							st = getusertl.returnpagestream();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// System.out.println(st);
						for(StatusTX statustx:st)
							this.savestatustx(statustx);
						// Thread.sleep(500);
						// System.out.println("sleep---------------");
					} while (!st.isEmpty());
				}
				try {
					Thread.sleep(minute * 60 * 1000);
					System.out.println("sleep---------------");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			return;
		}
	}
	
	private void savestatustx(StatusTX st) {
		for (Plugin plugin : pluginList)
			plugin.save(t, st);
	}

	private void saveusertx(UserTX usertx) {
		for (Plugin plugin : pluginList)
			plugin.save(t, usertx);
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
}
