/**
 * 
 */
package org.cheetyan.weibospider.spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.json.JsonObject;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.tx.PagingTX;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.StatusTXWapper;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.model.util.HttpClientPoolUtil;
import org.cheetyan.weibospider.model.util.JsonUtil;
import org.cheetyan.weibospider.taskmodel.SinaTasks;
import org.cheetyan.weibospider.taskmodel.TxTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cheetyan
 * 
 */

public class TimelineTX {
	private static Logger logger = LoggerFactory.getLogger(TimelineTX.class.getName());
	private ArrayList<BasicNameValuePair> ppslist; // for post method
	//@Resource
	private TxTasks txTasks;

	public TimelineTX() {
	}
	public void init() {
		ppslist = new ArrayList<>(); 
		Map<String, String> paramap = txTasks.getMap();
		try {
			paramap.put("clientip", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> key = paramap.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (s != null && paramap.get(s) != null)
				ppslist.add(new BasicNameValuePair(s, paramap.get(s)));
		}
	}

	public StatusTXWapper getUserTimelineTXByName(String name, PagingTX page) throws WeiboException, IOException {
		List<BasicNameValuePair> pps = (List<BasicNameValuePair>) ppslist.clone();
		pps.add(new BasicNameValuePair("name", name));
		return StatusTX.constructWapperStatusTX(getTX(txTasks.getBaseURL() + "statuses/user_timeline", pps, page));
	}

	public UserTX getUserTXByName(String name) throws WeiboException, IOException {
		List<BasicNameValuePair> pps = (List<BasicNameValuePair>) ppslist.clone();
		PagingTX page = new PagingTX();
		pps.add(new BasicNameValuePair("name", name));
		logger.trace("page: " + page + " pps: " + pps + " ");
		return UserTX.constructResult(getTX(txTasks.getBaseURL() + "user/other_info", pps, page));
	}

	public StatusTXWapper getPublicTimelineTX(int pos, int reqnum) throws WeiboException, IOException {
		List<BasicNameValuePair> pps = (List<BasicNameValuePair>) ppslist.clone();
		pps.add(new BasicNameValuePair("pos", String.valueOf(pos)));
		pps.add(new BasicNameValuePair("reqnum", String.valueOf(reqnum)));
		pps.add(new BasicNameValuePair("pageflag", String.valueOf(0)));
		return StatusTX.constructWapperStatusTX(getTX(txTasks.getBaseURL() + "statuses/public_timeline", pps, null));
	}

	private static JsonObject getTX(String url, List<BasicNameValuePair> params, PagingTX paging) throws WeiboException, IOException {
		//logger.info(url + " : " + paging + " : " + params);
		if (null != paging) {
			return getTX(url, merge(params, paging));
		} else {
			return getTX(url, params);
		}
	}

	private static JsonObject getTX(String url, List<BasicNameValuePair> params) throws IOException {
		// HttpPost host = new HttpPost(url);
		// host.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		String url1 = PostToGet(url, params);
		logger.info(url1);
		HttpGet host = new HttpGet(url1);
		return JsonUtil.JsonObject(HttpClientPoolUtil.execute(host));
	}

	// it sucks
	private static String PostToGet(String url, List<BasicNameValuePair> params) throws UnsupportedEncodingException {
		int i = 0;
		for (BasicNameValuePair b : params) {
			if (i == 0) {
				url = url + "?" + b.getName() + "=" + java.net.URLEncoder.encode(b.getValue(), "UTF-8");
				i = 1;
			} else {
				url = url + "&" + b.getName() + "=" + java.net.URLEncoder.encode(b.getValue(), "UTF-8");
			}
		}
		return url;
	}

	private static List<BasicNameValuePair> merge(List<BasicNameValuePair> params, PagingTX paging) {
		List<BasicNameValuePair> pagingParams = params;
		if (-1 != paging.getPageflag()) {
			pagingParams.add(new BasicNameValuePair("pageflag", String.valueOf(paging.getPageflag())));
		}
		if (-1 != paging.getPagetime()) {
			pagingParams.add(new BasicNameValuePair("pagetime", String.valueOf(paging.getPagetime())));
		}
		if (-1 != paging.getReqnum()) {
			pagingParams.add(new BasicNameValuePair("reqnum", String.valueOf(paging.getReqnum())));
		}
		if (-1 != paging.getLastid()) {
			pagingParams.add(new BasicNameValuePair("lastid", String.valueOf(paging.getLastid())));
		}
		return pagingParams;
	}

	public TxTasks getTxTasks() {
		return txTasks;
	}

	public void setTxTasks(TxTasks txTasks) {
		this.txTasks = txTasks;
	}
	
}
