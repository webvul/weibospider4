package org.cheetyan.weibospider.plugins;

import java.util.HashMap;
import java.util.Map;

import org.cheetyan.weibospider.model.sina.Status;
import org.cheetyan.weibospider.model.sina.User;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.plugins.idol.DREAddData;
import org.cheetyan.weibospider.plugins.idol.IDXContent;
import org.cheetyan.weibospider.plugins.idol.IDXNode;
import org.cheetyan.weibospider.taskmodel.Task;

public class IDOLPlugin implements Plugin {
	public static final int maxSize = 10;
	public static final String database = "weibo";
	private volatile IDXContent idxContent;

	public IDOLPlugin() {
		idxContent = new IDXContent();
	}

	@Override
	public void close() {
		if (!idxContent.getIdxNodes().isEmpty()) {
			DREAddData.post(idxContent.toString(), database);
		}
		idxContent.getIdxNodes().clear();
	}

	@Override
	public void save(Task t, Status st) {
		idxContent.getIdxNodes().add(getIDXNodeFromStatus(st));
		if (idxContent.getIdxNodes().size() >= 10) {
			DREAddData.post(idxContent.toString(), database);
		}
		idxContent.getIdxNodes().clear();
	}

	@Override
	public void save(Task t, User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(Task t, StatusTX st) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(Task t, UserTX usertx) {
		// TODO Auto-generated method stub
	}

	private static IDXNode getIDXNodeFromStatus(Status status) {
		IDXNode idxNode = new IDXNode();
		// DREREFERENCE
		idxNode.setDreReference(status.getId().toString());
		// DREDATE
		idxNode.setDreDate(String.valueOf(status.getCreatedAt().getTime() / 1000));
		// DREDBNAME
		idxNode.setDreDbName("Weibo");
		// DRETITLE
		idxNode.setDreTitle(status.getText());
		// DRECONTENT
		idxNode.setDreContent(status.getText());
		// DREFIELD
		Map<String, String> dreFields = new HashMap<String, String>();
		dreFields.put("MID", status.getMid());
		dreFields.put("WEIBO_USER_ID", String.valueOf(status.getUser().getId()));
		dreFields.put("WEIBO_USER_NAME", status.getUser().getName());
		dreFields.put("WEIBO_USER_PROVINCE", String.valueOf(status.getUser().getProvince()));
		dreFields.put("WEIBO_USER_CITY", String.valueOf(status.getUser().getCity()));
		dreFields.put("WEIBO_USER_LOCATION", status.getUser().getLocation());
		dreFields.put("WEIBO_USER_DESCRIPTION", status.getUser().getDescription());
		dreFields.put("WEIBO_USER_URL", status.getUser().getUrl());
		dreFields.put("WEIBO_USER_PROFILEIMAGEURL", status.getUser().getProfileImageUrl());
		dreFields.put("WEIBO_USER_DOMAIN", status.getUser().getDomain());
		dreFields.put("WEIBO_USER_GENDER", status.getUser().getGender());
		dreFields.put("WEIBO_USER_FOLLOWERSCOUNT", String.valueOf(status.getUser().getFollowersCount()));
		dreFields.put("WEIBO_USER_FRIENDSCOUNT", String.valueOf(status.getUser().getFriendsCount()));
		dreFields.put("WEIBO_USER_STATUSESCOUNT", String.valueOf(status.getUser().getStatusesCount()));
		dreFields.put("WEIBO_USER_FAVOURITESCOUNT", String.valueOf(status.getUser().getFavouritesCount()));
		dreFields.put("WEIBO_USER_CREATEDAT", String.valueOf(status.getUser().getCreatedAt().getTime() / 1000));
		dreFields.put("WEIBO_USER_VERIFIED", String.valueOf(status.getUser().isVerified()));
		dreFields.put("WEIBO_USER_AVATARLARGE", status.getUser().getAvatarLarge());
		dreFields.put("WEIBO_USER_BIFOLLOWERSCOUNT", String.valueOf(status.getUser().getBiFollowersCount()));
		dreFields.put("WEIBO_USER_REMARK", status.getUser().getRemark());
		dreFields.put("WEIBO_USER_LANG", status.getUser().getLang());
		dreFields.put("WEIBO_USER_VERIFIEDREASON", status.getUser().getVerifiedReason());
		dreFields.put("WEIBO_REPOSTS_COUNT", String.valueOf(status.getRepostsCount()));
		dreFields.put("WEIBO_COMMENTS_COUNT", String.valueOf(status.getCommentsCount()));
		dreFields.put("WEIBO_SOURCE", status.getSource().toString());
		dreFields.put("WEIBO_GEO", status.getGeo());
		dreFields.put("WEIBO_LATITUE", String.valueOf(status.getLatitude()));
		dreFields.put("WEIBO_LONGITUDE", String.valueOf(status.getLongitude()));
		dreFields.put("WEIBO_THUMBNAILPIC", status.getThumbnailPic());
		dreFields.put("WEIBO_BMIDDLEPIC", status.getBmiddlePic());
		dreFields.put("WEIBO_ORIGINALPIC", status.getOriginalPic());
		dreFields.put("SPIDERDOMAIN", "weibo.com");
		dreFields.put("SPIDERDATE", String.valueOf(System.currentTimeMillis() / 1000));
		idxNode.setDreFields(dreFields);
		return idxNode;
	}
}
