package org.cheetyan.weibospider.model.tx;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.sina.WeiboResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Entity
public class StatusTX implements java.io.Serializable {

	private static final long serialVersionUID = -8795691786466526420L;
	private static Logger logger = LoggerFactory.getLogger(StatusTX.class.getName());

	private String name;
	private String openid; // 用户唯一id
	private String nick;
	private int self;
	private int type;
	private String location;
	private String country_code;
	private String province_code;
	private String city_code;
	private int isvip;
	private String emotiontype;
	private String emotionurl;

	private String timestamp;
	@Id
	private String id; // StatusTX id
	// private String mid; //微博MID
	// private long idstr; //保留字段，请勿使用
	@Column(length = 1000)
	private String text; // 微博内容
	@Transient
	private SourceTX sourcetx; // 微博来源
	// private boolean favorited; //是否已收藏
	// private boolean truncated;
	// private long inReplyToStatusTXId; //回复ID
	// private long inReplyToUserId; //回复人ID
	// private String inReplyToScreenName; //回复人昵称
	// private String thumbnailPic; //微博内容中的图片的缩略地址
	@Column(length = 1000)
	private String image;
	// private String bmiddlePic; //中型图片
	// private String originalPic; //原始图片
	private String origtext; // 转发的博文，内容为StatusTX，如果不是转发，则没有此字段
	private String geo; // 地理信息，保存经纬度，没有时不返回此字段
	// private double latitude = -1; //纬度
	// private double longitude = -1; //经度
	// private int repostsCount; //转发数
	private int count;
	// private int commentsCount; //评论数
	private int mcount;
	// private String annotations; //元数据，没有时不返回此字段
	// private int mlevel;
	// private Visible visible;
	@Embedded
	private Comp comp;
	@Embedded
	private Edu edu;

	public StatusTX() {

	}

	private void constructJson(JsonObject json) throws WeiboException {
		try {
			timestamp = json.getJsonNumber("timestamp").toString();
			id = json.getString("id");
			name = json.getString("name");
			openid = json.getString("openid");
			nick = json.getString("nick");
			location = json.getString("location");
			country_code = json.getString("country_code");
			province_code = json.getString("province_code");
			city_code = json.getString("city_code");
			isvip = json.getInt("isvip");
			self = json.getInt("self");
			type = json.getInt("type");

			text = WeiboResponseUtil.withNonBmpStripped(json.getString("text"));
			origtext = WeiboResponseUtil.withNonBmpStripped(json.getString("origtext"));

			// image = json.getJsonArray("image").toString();
			if (!json.isNull("image")) {
				image = json.getJsonArray("image").toString();
			} else {
				image = null;
			}

			count = json.getInt("count");
			mcount = json.getInt("mcount");

			if (json.containsKey("comp"))
				comp = new Comp(json.getJsonObject("comp"));
			if (json.containsKey("edu")) {
				edu = new Edu(json.getJsonObject("edu"));
			}

			geo = json.getString("geo");

		} catch (JsonException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
		}
	}

	public StatusTX(JsonObject json) throws WeiboException, JsonException {
		constructJson(json);
	}

	public StatusTX(String str) throws WeiboException, JsonException {

		JsonReader jsonReader = Json.createReader(new StringReader(str));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		constructJson(json);
	}

	public static StatusTXWapper constructWapperStatusTX(JsonObject res) throws WeiboException {
		logger.trace(res.toString());
		JsonObject jsonStatusTX = res; // asJsonArray();
		JsonArray statuses = null;
		JsonObject data = null;
		try {
			if (!jsonStatusTX.isNull("data")) {

				data = jsonStatusTX.getJsonObject("data");
				statuses = data.getJsonArray("info");
			}
			/*
			 * if (!jsonStatusTX.isNull("source")) { statuses =
			 * jsonStatusTX.getJsonArray("source"); }
			 */
			if (statuses == null)
				return new StatusTXWapper();
			int size = statuses.size();
			List<StatusTX> status = new ArrayList<StatusTX>(size);
			for (int i = 0; i < size; i++) {
				status.add(new StatusTX(statuses.getJsonObject(i)));
			}

			long totalnum = 0;
			if (data.containsKey("totalnum")) {
				totalnum = data.getJsonNumber("totalnum").longValue();
			}
			int hasnext = data.getInt("hasnext");

			return new StatusTXWapper(status, totalnum, hasnext);
		} catch (JsonException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getSelf() {
		return self;
	}

	public void setSelf(int self) {
		this.self = self;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public int getIsvip() {
		return isvip;
	}

	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}

	public String getEmotiontype() {
		return emotiontype;
	}

	public void setEmotiontype(String emotiontype) {
		this.emotiontype = emotiontype;
	}

	public String getEmotionurl() {
		return emotionurl;
	}

	public void setEmotionurl(String emotionurl) {
		this.emotionurl = emotionurl;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SourceTX getSourcetx() {
		return sourcetx;
	}

	public void setSourcetx(SourceTX sourcetx) {
		this.sourcetx = sourcetx;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOrigtext() {
		return origtext;
	}

	public void setOrigtext(String origtext) {
		this.origtext = origtext;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMcount() {
		return mcount;
	}

	public void setMcount(int mcount) {
		this.mcount = mcount;
	}

	public Comp getComp() {
		return comp;
	}

	public void setComp(Comp comp) {
		this.comp = comp;
	}

	public Edu getEdu() {
		return edu;
	}

	public void setEdu(Edu edu) {
		this.edu = edu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusTX other = (StatusTX) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusTX [name=" + name + ", openid=" + openid + ", nick=" + nick + ", self=" + self + ", type=" + type + ", location=" + location
				+ ", country_code=" + country_code + ", province_code=" + province_code + ", city_code=" + city_code + ", isvip=" + isvip
				+ ", emotiontype=" + emotiontype + ", emotionurl=" + emotionurl + ", timestamp=" + timestamp + ", id=" + id + ", text=" + text
				+ ", sourcetx=" + sourcetx + ", image=" + image + ", origtext=" + origtext + ", geo=" + geo + ", count=" + count + ", mcount="
				+ mcount + ", comp=" + comp + ", edu=" + edu + "]";
	}

}
