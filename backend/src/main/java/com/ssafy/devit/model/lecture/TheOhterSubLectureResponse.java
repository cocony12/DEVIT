package com.ssafy.devit.model.lecture;

public class TheOhterSubLectureResponse {
	private long lectureId;
	private long subId;
	private long subHisId;
	private long order;
	private String title;
	private String playerUrl;
	private String playTime;
	private String nickname;
	private String profile;
	private int viewCount;
	private int likeCount;
	private String thumbnailUrl;
	private boolean userLikeYn;

	public TheOhterSubLectureResponse(long lectureId, long subId, long subHisId, long order, String title, String playerUrl,
			String playTime, String nickname, String profile, int viewCount, int likeCount, String thumbnailUrl, boolean userLikeYn) {
		super();
		this.lectureId = lectureId;
		this.subId = subId;
		this.subHisId = subHisId;
		this.order = order;
		this.title = title;
		this.playerUrl = playerUrl;
		this.playTime = playTime;
		this.nickname = nickname;
		this.profile = profile;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.thumbnailUrl = thumbnailUrl;
		this.userLikeYn = userLikeYn;
	}

	public TheOhterSubLectureResponse() {
		super();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlayerUrl() {
		return playerUrl;
	}

	public void setPlayerUrl(String playerUrl) {
		this.playerUrl = playerUrl;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public boolean isUserLikeYn() {
		return userLikeYn;
	}

	public void setUserLikeYn(boolean userLikeYn) {
		this.userLikeYn = userLikeYn;
	}

	public long getSubHisId() {
		return subHisId;
	}

	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
	}

	@Override
	public String toString() {
		return "TheOhterSubLectureResponse [lectureId=" + lectureId + ", subId=" + subId + ", subHisId=" + subHisId
				+ ", title=" + title + ", playerUrl=" + playerUrl + ", playTime=" + playTime + ", viewCount="
				+ viewCount + ", likeCount=" + likeCount + ", thumbnailUrl=" + thumbnailUrl + ", userLikeYn="
				+ userLikeYn + "]";
	}

}
