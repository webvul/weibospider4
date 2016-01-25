package org.cheetyan.weibospider.model.tx;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.cheetyan.weibospider.model.sina.WeiboException;
import org.cheetyan.weibospider.model.sina.WeiboResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data class representing Basic user information element
 */
@Entity
public class UserTX implements java.io.Serializable {
	private static final long serialVersionUID = -1730556459248261817L;
	private static Logger logger = LoggerFactory.getLogger(UserTX.class.getName());
	@Id
	private String openid; // *用户UID,must have,not null,string,"openid":"83CD2ACF850B5131E22D3EB130172BF5"
	private String name; // 微博昵称,must have,"name":"wdy1783378977",
	private String nick; // "nick":"海盗",
	private int birth_day; // int,must have,not null,can be 0(means null),
	private int birth_month; // the same to dat
	private int birth_year; // the same to day
	@Embedded
	private Comp comp; // can be null
	@Embedded
	private Edu edu; // can be null
	private String city_code;
	private String country_code; // string , must have,json string to int
	private String industry_code; //
	private String introduction; // 个人介绍
	private int tweetnum; // weibo number
	private String verifyinfo; // 认证信息
	private int sex;
	private long regtime;// 注册时间,"regtime":1296811317,must have,not null ,json long
	private String location;
	private int isrealname;// shimingrenzheng,1 renzheng,2 burenzheng
	private int isvip;// need money

	/**
	 * //private String name; //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持) private int province; //省份编码（参考省份编码表） private int city; //城市编码（参考城市编码表） private String location; //地址 private String description; //个人描述 private String url; //用户博客地址 private
	 * String profileImageUrl; //自定义图像 private String userDomain; //用户个性化URL private String gender; //性别,m--男，f--女,n--未知 private int followersCount; //粉丝数 private int friendsCount; //关注数 private int statusesCount; //微博数 private int favouritesCount;
	 * //收藏数 private Date createdAt; //创建时间 private boolean following; //保留字段,是否已关注(此特性暂不支持) private boolean verified; //加V标示，是否微博认证用户 private int verifiedType; //认证类型 private boolean allowAllActMsg; //是否允许所有人给我发私信 private boolean allowAllComment;
	 * //是否允许所有人对我的微博进行评论 private boolean followMe; //此用户是否关注我 private String avatarLarge; //大头像地址 private int onlineStatus; //用户在线状态 private Status status = null; //用户最新一条微博 private int biFollowersCount; //互粉数 private String remark;
	 * //备注信息，在查询用户关系时提供此字段。 private String lang; //用户语言版本 private String verifiedReason; //认证原因 private String weihao; //微號 private String statusId;
	 */
	public UserTX(JsonObject json) throws WeiboException {
		init(json);
	}

	public UserTX() {
		super();
	}

	private void init(JsonObject json) throws WeiboException {
		if (json != null) {
			try {
				openid = json.getString("openid");
				name = json.getString("name");
				nick = json.getString("nick");
				birth_day = json.getInt("birth_day");
				birth_month = json.getInt("birth_month");
				birth_year = json.getInt("birth_year");
				city_code = json.getString("city_code");
				country_code = json.getString("country_code");
				industry_code = String.valueOf(json.getJsonNumber("industry_code"));
				tweetnum = json.getInt("tweetnum");
				sex = json.getInt("sex");
				introduction = WeiboResponseUtil.withNonBmpStripped(json.getString("introduction"));
				verifyinfo = json.getString("verifyinfo");
				regtime = json.getJsonNumber("regtime").longValue();
				location = json.getString("location");
				isrealname = json.getInt("isrealname");
				isvip = json.getInt("isvip");
				if (!json.isNull("comp"))
					comp = new Comp(json.getJsonArray("comp").getJsonObject(0));
				if (!json.isNull("edu")) {
					edu = new Edu(json.getJsonArray("edu").getJsonObject(0));
				}
			} catch (JsonException jsone) {
				throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
			}
		}
	}

	public static UserTX constructResult(JsonObject res) throws WeiboException {
		logger.trace(res.toString());
		try {
			JsonObject data = null;
			if (!res.isNull("data"))
				data = res.getJsonObject("data");
			UserTX usertx = new UserTX(data);
			return usertx;
		} catch (JsonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the isrealname
	 */
	public int getIsrealname() {
		return isrealname;
	}

	/**
	 * @param isrealname
	 *            the isrealname to set
	 */
	public void setIsrealname(int isrealname) {
		this.isrealname = isrealname;
	}

	/**
	 * @return the isvip
	 */
	public int getIsvip() {
		return isvip;
	}

	/**
	 * @param isvip
	 *            the isvip to set
	 */
	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getBirth_day() {
		return birth_day;
	}

	public void setBirth_day(int birth_day) {
		this.birth_day = birth_day;
	}

	public int getBirth_month() {
		return birth_month;
	}

	public void setBirth_month(int birth_month) {
		this.birth_month = birth_month;
	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
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

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getIndustry_code() {
		return industry_code;
	}

	public void setIndustry_code(String industry_code) {
		this.industry_code = industry_code;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getTweetnum() {
		return tweetnum;
	}

	public void setTweetnum(int tweetnum) {
		this.tweetnum = tweetnum;
	}

	public String getVerifyinfo() {
		return verifyinfo;
	}

	public void setVerifyinfo(String verifyinfo) {
		this.verifyinfo = verifyinfo;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public long getRegtime() {
		return regtime;
	}

	public void setRegtime(long regtime) {
		this.regtime = regtime;
	}

	@Override
	public String toString() {
		return "UserTX [openid=" + openid + ", name=" + name + ", nick=" + nick + ", birth_day=" + birth_day + ", birth_month=" + birth_month + ", birth_year=" + birth_year + ", comp=" + comp + ", edu=" + edu + ", city_code=" + city_code
				+ ", country_code=" + country_code + ", industry_code=" + industry_code + ", introduction=" + introduction + ", tweetnum=" + tweetnum + ", verifyinfo=" + verifyinfo + ", sex=" + sex + ", regtime=" + regtime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openid == null) ? 0 : openid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserTX other = (UserTX) obj;
		if (openid == null) {
			if (other.openid != null)
				return false;
		} else if (!openid.equals(other.openid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
