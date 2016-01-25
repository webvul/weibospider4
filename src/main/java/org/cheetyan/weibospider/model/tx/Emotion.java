package org.cheetyan.weibospider.model.tx;

import java.util.ArrayList;
import java.util.List;

import javax.json.*;

import org.cheetyan.weibospider.model.sina.WeiboException;


/**
 * @author SinaWeibo
 * 
 */
public class Emotion {
	private static final long serialVersionUID = -4096813631691846494L;
	private String phrase; // 表情使用的替代文字
	private String type; // 表情类型，image为普通图片表情，magic为魔法表情
	private String url; // 表情图片存放的位置
	private boolean hot; // 是否为热门表情
	private boolean common; // 是否是常用表情
	private String value;
	private String category; // 表情分类
	private String picid;
	private String icon;

	public Emotion(JsonObject json) throws WeiboException {

		phrase = json.getString("phrase");
		type = json.getString("type");
		url = json.getString("url");
		hot = json.getBoolean("hot");
		category = json.getString("category");
		common = json.getBoolean("common");
		value = json.getString("value");
		picid = json.getString("picid");
		icon = json.getString("icon");

	}

	public static List<Emotion> constructEmotions(JsonArray res) throws WeiboException {

		// JsonArray list = res.asJsonArray();
		JsonArray list = res;
		int size = list.size();
		List<Emotion> emotions = new ArrayList<Emotion>(size);
		for (int i = 0; i < size; i++) {
			emotions.add(new Emotion(list.getJsonObject(i)));
		}
		return emotions;

	}

	public Emotion() {
		super();
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Emotion [phrase=" + phrase + ", type=" + type + ", url=" + url + ", hot=" + hot + ", common=" + common + ", value=" + value
				+ ", category=" + category + ", picid=" + picid + ", icon=" + icon + "]";
	}

}
