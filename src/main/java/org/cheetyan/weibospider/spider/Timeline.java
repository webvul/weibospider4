/**
 * 
 */
package org.cheetyan.weibospider.spider;

import java.io.IOException;

import javax.annotation.Resource;
import javax.json.JsonObject;

import org.apache.http.client.methods.HttpGet;
import org.cheetyan.weibospider.model.sina.Status;
import org.cheetyan.weibospider.model.sina.StatusWapper;
import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.util.HttpClientPoolUtil;
import org.cheetyan.weibospider.model.util.JsonUtil;
import org.cheetyan.weibospider.taskmodel.SinaTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cheetyan
 * 
 */
public class Timeline {
	private static Logger logger = LoggerFactory.getLogger(Timeline.class.getName());
	private SinaTasks sinaTasks;
	private String url;
	
	public Timeline(){}

	public Timeline(SinaTasks sinaTasks) {
		this.sinaTasks = sinaTasks;
		init();
	}
	
	private void init() {
		url = sinaTasks.getBaseURL() + "statuses/public_timeline.json" + "?access_token=" + sinaTasks.getAccesstoken();
	}

	public StatusWapper getPublicTimeline() throws WeiboException, IOException {
		return Status.constructWapperStatus(get(url));
	}

	public StatusWapper getPublicTimeline(int count, int baseApp) throws WeiboException, IOException {
		baseApp = 0;
		return Status.constructWapperStatus(get(url + "&count=" + String.valueOf(count) + "&base_app=" + String.valueOf(baseApp)));
	}

	public StatusWapper getUserTimelineByName(String screen_name, org.cheetyan.weibospider.model.sina.Paging page, Integer base_app, Integer feature) throws WeiboException {
		throw new UnsupportedOperationException("not implementd yet");
	}

	public StatusWapper getUserTimelineByName(String username) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	private static JsonObject get(String url) throws IOException {
		logger.info("start get url : " + url);
		HttpGet get = new HttpGet(url);
		String s = HttpClientPoolUtil.execute(get);
		return JsonUtil.JsonObject(s);
	}

	public SinaTasks getSinaTasks() {
		return sinaTasks;
	}

	public void setSinaTasks(SinaTasks sinaTasks) {
		this.sinaTasks = sinaTasks;
	}
	
}
