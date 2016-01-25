package org.cheetyan.weibospider.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.cheetyan.weibospider.download.DownloadPic;
import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.tx.PagingTX;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.StatusTXWapper;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.taskmodel.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetUserTlAllTX {
	
	private Task t;
	private TimelineTX tmtx;
	//
	private int pageper = new PagingTX().getReqnum();
	private PagingTX pagea = new PagingTX(); // returnpageper using it
	private int pagei = 0; // returnpageper using it
	private String timestamp = "0";

	private String taskname;
	private DownloadPic dt = null;// download pic
	private boolean downloadpic = false;

	private int count = 0;

	private static Logger log = LoggerFactory.getLogger(GetUserTlAllTX.class.getName());

	public GetUserTlAllTX(Task t,TimelineTX tmtx) {
		this.t = t;
		this.tmtx = tmtx;
		if (t.isDownloadPicture())
			dt = new DownloadPic(taskname);
	}

	public UserTX getUserInfo() throws IOException {
		try {
			return tmtx.getUserTXByName(t.getUsernames().get(0));
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<StatusTX> returnperpage() throws IOException {
		List<StatusTX> returnstatus = new ArrayList<StatusTX>();
		StatusTXWapper statustx = null;
		try {
			statustx = tmtx.getUserTimelineTXByName(t.getUsernames().get(0), pagea);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (statustx.getTotalNumber() != 0) {
			log.info("该用户" + t.getUsernames().get(0) + "原始微博总数" + statustx.getTotalNumber());
		} else {
			log.info("该用户" + t.getUsernames().get(0) + "微博已经采集完");
		}
		List<StatusTX> sttxlist = statustx.getStatuses();
		if (sttxlist != null && !sttxlist.isEmpty()) {
			for (int i = 0; i < sttxlist.size(); i++) {

				StatusTX stx = sttxlist.get(i);
				if (i == sttxlist.size() - 1) {
					timestamp = stx.getTimestamp();
				}
				String stext = stx.getText();
				// logger.info(stext);
				log.info(stext);
				if (downloadpic) {
					// begin saving pic
					if (stx.getImage() != null && stx.getImage().length() > 4) {
						String statusid = stx.getId();
						dt.setFilename(statusid);
						String urla = stx.getImage().substring(2, stx.getImage().length() - 2);
						// logger.info(urla);
						try {
							dt.write(urla + "/" + "160", urla + "/" + "460", urla + "/" + "2000");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}// end saving pic

				if (t.getKeywords()!= null && !t.getKeywords().isEmpty()) {
					for (String keyw : t.getKeywords()) {
						// if (stext.indexOf(keyw) != -1) {
						if (Pattern.compile(keyw).matcher(stext).find()) {

							returnstatus.add(stx);
							count++;

							continue;
						} else {
							// logger.info(stext + "不含关键词" + keyw);

						}
					}
				} else {
					returnstatus.add(stx);
					count++;
				}
			}
		} else {
			log.info("未采到该页数据！");
			return returnstatus;
		}
		return returnstatus;
	}

	public List<StatusTX> returnpagestream() throws IOException {
		List<StatusTX> statusa = new ArrayList<StatusTX>();
		pagea.setPageflag(1);
		pagea.setPagetime(Integer.parseInt(timestamp));
		statusa = this.returnperpage();
		return statusa;

	}

	public TimelineTX getTmtx() {
		return tmtx;
	}

	public void setTmtx(TimelineTX tmtx) {
		this.tmtx = tmtx;
	}

	public PagingTX getPagea() {
		return pagea;
	}

	public void setPagea(PagingTX pagea) {
		this.pagea = pagea;
	}

	public int getPagei() {
		return pagei;
	}

	public void setPagei(int pagei) {
		this.pagei = pagei;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
/*
	public static void main(String[] args) throws  IOException, InterruptedException {
		// TODO Auto-generated method stub
		GetUserTlAllTX userall = new GetUserTlAllTX();
		userall.setUsername("gupiaoleida");
		List<StatusTX> st = new ArrayList<StatusTX>();
		do {
			st = userall.returnpagestream();
			System.out.println(st);
			Thread.sleep(500);
			System.out.println("sleep---------------");
		} while (!st.isEmpty());

	}*/

}
