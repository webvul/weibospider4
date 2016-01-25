/**
 * 
 */
package org.cheetyan.weibospider.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.cheetyan.weibospider.download.DownloadPic;
import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.StatusTXWapper;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.taskmodel.Task;
import org.cheetyan.weibospider.taskmodel.TxTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cheetyan
 * 
 */
public class GetPublicTlAllTX {

	private Task t;
	private TimelineTX tmtx;
	//
	private int pos = 0;
	private int reqnum = 100; // 100 records per page
	private DownloadPic dt;// whether to download pic
	private int count = 0;
	private static Logger log = LoggerFactory.getLogger(GetPublicTlAllTX.class.getName());

	public GetPublicTlAllTX(Task t,TimelineTX tmtx) {
		this.t=t;
		this.tmtx=tmtx;
		if (t.isDownloadPicture())
			dt = new DownloadPic(t.getName());
	}

	private UserTX getUserInfo(String username) {
		try {
			log.trace("username : " + username);
			return tmtx.getUserTXByName(username);
		} catch (WeiboException | IOException e) {
			// TODO Auto-generated catch block
			log.error(username);
			e.printStackTrace();
		}
		return null;
	}

	public Map<List<StatusTX>, List<UserTX>> returnperpage() throws IOException {
		Map<List<StatusTX>, List<UserTX>> map = new HashMap<List<StatusTX>, List<UserTX>>();
		List<UserTX> usertxlist = new ArrayList<UserTX>();
		List<StatusTX> returnstatus = new ArrayList<StatusTX>();
		StatusTXWapper statustx = null;
		try {
			statustx = tmtx.getPublicTimelineTX(pos, reqnum);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<StatusTX> sttxlist = statustx.getStatuses();
		if (sttxlist == null) {
			log.warn("warn: 获取腾讯微博当前页总数  0");
			return map;
		}
		log.info("获取腾讯微博当前页总数" + sttxlist.size());
		if (sttxlist != null && !sttxlist.isEmpty()) {
			for (int i = 0; i < sttxlist.size(); i++) {
				StatusTX stx = sttxlist.get(i);
				String stext = stx.getText();
				if (t.isDownloadPicture()) {
					if (stx.getImage() != null && stx.getImage().length() > 4) {
						String statusid = stx.getId();
						dt.setFilename(statusid);
						String urla = stx.getImage().substring(2, stx.getImage().length() - 2);
						try {
							dt.write(urla + "/" + "160", urla + "/" + "460", urla + "/" + "2000");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}// end saving pic
				if (t.getKeywords() != null && !t.getKeywords().isEmpty()) {
					for (String keyw : t.getKeywords()) {
						// if (stext.indexOf(keyw) != -1) {
						if (Pattern.compile(keyw).matcher(stext).find()) {
							returnstatus.add(stx);
							count++;
							log.trace(stext + " 包含关键词  " + keyw);
							break;
						} else {
							log.trace(stext + " 不含关键词  " + keyw);
						}
					}
				} else {
					returnstatus.add(stx);
					count++;
				}
			}
		} else {
			log.info("未采到该页数据！");
			return map;
		}
		usertxlist = getuserlist(returnstatus);
		map.put(returnstatus, usertxlist);
		return map;
	}

	public List<UserTX> getuserlist(List<StatusTX> statuslist) throws IOException {
		List<UserTX> userlist = new ArrayList<UserTX>();
		for (StatusTX status : statuslist) {
			String name = status.getName();
			userlist.add(getUserInfo(name));
		}
		return userlist;
	}

	public Map<List<StatusTX>, List<UserTX>> returnpagestream() throws IOException {
		// this.setPos(pos + reqnum);
		return this.returnperpage();
	}

	/**
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *            the pos to set
	 */
	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * @return the reqnum
	 */
	public int getReqnum() {
		return reqnum;
	}

	/**
	 * @param reqnum
	 *            the reqnum to set
	 */
	public void setReqnum(int reqnum) {
		this.reqnum = reqnum;
	}

	public TimelineTX getTmtx() {
		return tmtx;
	}

	public void setTmtx(TimelineTX tmtx) {
		this.tmtx = tmtx;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Task getT() {
		return t;
	}

	public void setT(Task t) {
		this.t = t;
	}
	
}
