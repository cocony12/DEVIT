package com.ssafy.devit.model.lecture;

public class LecturesResponse {
	private long lectureId;
	private long commonId;
	private long userId;
	private String title;
	private String content;
	private String thumbnailUrl;
	private String nickname;
	private String profile;
	private int lectureCount;
	private int viewCount;
	private int likeCount;
	private String tagName;
	private boolean userLikeYn;
	private String isOwner;

	public LecturesResponse() {

	}

	public LecturesResponse(long lectureId, long commonId, long userId, String title, String content, String thumbnailUrl,
			String nickname, String profile, int lectureCount, int viewCount, int likeCount, String tagName,
			boolean userLikeYn) {
		super();
		this.lectureId = lectureId;
		this.commonId = commonId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.thumbnailUrl = thumbnailUrl;
		this.nickname = nickname;
		this.profile = profile;
		this.lectureCount = lectureCount;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.tagName = tagName;
		this.userLikeYn = userLikeYn;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public long getLectureId() {
		return lectureId;
	}

	public void setLectureId(long lectureId) {
		this.lectureId = lectureId;
	}

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public boolean isUserLikeYn() {
		return userLikeYn;
	}

	public void setUserLikeYn(boolean userLikeYn) {
		this.userLikeYn = userLikeYn;
	}

	@Override
	public String toString() {
		return "LecturesResponse [lectureId=" + lectureId + ", commonId=" + commonId + ", userId=" + userId + ", title="
				+ title + ", thumbnailUrl=" + thumbnailUrl + ", nickname=" + nickname + ", lectureCount=" + lectureCount
				+ ", viewCount=" + viewCount + ", likeCount=" + likeCount + ", tagName=" + tagName + ", userLikeYn="
				+ userLikeYn + ", isOwner=" + isOwner + "]";
	}

	public String getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}

}
