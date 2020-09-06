package com.ssafy.devit.model.common;

import io.swagger.annotations.ApiModelProperty;

public class SearchInfoIndexResponse {
	@ApiModelProperty(example = "대표 프로젝트 ID")
	private long lectureId;
	@ApiModelProperty(example = "목차 ID")
	private long subId;
	@ApiModelProperty(example = "순서")
	private int order;
	@ApiModelProperty(example = "대표 프로젝트 제목")
	private String title;
	@ApiModelProperty(example = "공통 ID")
	private long commonId;
	@ApiModelProperty(example = "동영상 URL")
	private String playerUrl;
	@ApiModelProperty(example = "동영상 시간")
	private String playTime;
	@ApiModelProperty(example = "목차 썸네일")
	private String thumbnailUrl;
	@ApiModelProperty(example = "목차 조회 수")
	private int viewCount;
	@ApiModelProperty(example = "목차 좋아요 수")
	private int likeCount;
	@ApiModelProperty(example = "사용자 좋아요 처리 여부")
	private boolean userLikeYn;
	@ApiModelProperty(example = "해당 API 요청시 반환되는 목차 총 개수")
	private int totalCount;
	@ApiModelProperty(example = "태그들")
	private String tagName;
	@ApiModelProperty(example = "목차를 생성한 사용자 닉네임")
	private String nickname;
	@ApiModelProperty(example = "목차를 생성한 사용자 프로필")
	private String profile;

	public SearchInfoIndexResponse() {
		super();
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	public boolean isUserLikeYn() {
		return userLikeYn;
	}

	public void setUserLikeYn(boolean userLikeYn) {
		this.userLikeYn = userLikeYn;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	@Override
	public String toString() {
		return "SearchInfoIndexResponse [lectureId=" + lectureId + ", subId=" + subId + ", order=" + order + ", title="
				+ title + ", commonId=" + commonId + ", playerUrl=" + playerUrl + ", playTime=" + playTime
				+ ", thumbnailUrl=" + thumbnailUrl + ", viewCount=" + viewCount + ", likeCount=" + likeCount
				+ ", userLikeYn=" + userLikeYn + ", totalCount=" + totalCount + ", tagName=" + tagName + ", nickname="
				+ nickname + ", profile=" + profile + "]";
	}

}
