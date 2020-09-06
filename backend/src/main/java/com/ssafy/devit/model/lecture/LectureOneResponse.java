package com.ssafy.devit.model.lecture;

import java.util.Date;

public class LectureOneResponse {
	private long lectureId;
	private long userId;
	private long commonId;
	private String title;
	private String content;
	private String thumbnailUrl;
	private String nickname;
	private String profile;
	private Date created;
	private int type;
	private int lectureCount;
	private int viewCount;
	private int likeCount;
	private String tagName;
	private boolean userLikeYn;
	private boolean manageYn;

	public LectureOneResponse() {

	}

	public boolean isManageYn() {
		return manageYn;
	}

	public void setManageYn(boolean manageYn) {
		this.manageYn = manageYn;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "LectureOneResponse [lectureId=" + lectureId + ", userId=" + userId + ", commonId=" + commonId
				+ ", title=" + title + ", content=" + content + ", thumbnailUrl=" + thumbnailUrl + ", nickname="
				+ nickname + ", created=" + created + ", type=" + type + ", lectureCount=" + lectureCount
				+ ", viewCount=" + viewCount + ", likeCount=" + likeCount + ", tagName=" + tagName + ", userLikeYn="
				+ userLikeYn + "]";
	}

}
