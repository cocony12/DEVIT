package com.ssafy.devit.model.lecture;

import io.swagger.annotations.ApiModelProperty;

public class LectureSubOneResponse {
	@ApiModelProperty(example = "프로젝트 ID")
	private long lectureId;
	@ApiModelProperty(example = "강의 ID")
	private long subId;
	@ApiModelProperty(example = "히스토리 ID")
	private long subHisId;
	@ApiModelProperty(example = "순서")
	private int order;
	@ApiModelProperty(example = "제목")
	private String title;
	@ApiModelProperty(example = "조회 수")
	private int viewCount;
	@ApiModelProperty(example = "좋아요 수")
	private int likeCount;
	@ApiModelProperty(example = "사용자 프로필")
	private String profile;
	@ApiModelProperty(example = "사용자 닉네임")
	private String nickname;
	@ApiModelProperty(example = "영상 시간")
	private String playTime;
	@ApiModelProperty(example = "영상 위치")
	private String playerUrl;
	@ApiModelProperty(example = "위키")
	private String wikiContent;
	@ApiModelProperty(example = "위키 HTML")
	private String wikiContentHtml;
	@ApiModelProperty(example = "태그들")
	private String tagName;
	@ApiModelProperty(example = "썸네일")
	private String thumbnailUrl;
	@ApiModelProperty(example = "좋아요 여부")
	private boolean userLikeYn;
	@ApiModelProperty(example = "강의 수")
	private int lectureCount;
	@ApiModelProperty(example = "이전 강의 여부")
	private boolean prePage;
	@ApiModelProperty(example = "다음 강의 여부")
	private boolean tailPage;

	public boolean isPrePage() {
		return prePage;
	}

	public void setPrePage(boolean prePage) {
		this.prePage = prePage;
	}

	public boolean isTailPage() {
		return tailPage;
	}

	public void setTailPage(boolean tailPage) {
		this.tailPage = tailPage;
	}

	public LectureSubOneResponse() {
		super();
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}

	public long getSubHisId() {
		return subHisId;
	}

	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getPlayerUrl() {
		return playerUrl;
	}

	public void setPlayerUrl(String playerUrl) {
		this.playerUrl = playerUrl;
	}

	public String getWikiContent() {
		return wikiContent;
	}

	public void setWikiContent(String wikiContent) {
		this.wikiContent = wikiContent;
	}

	public String getWikiContentHtml() {
		return wikiContentHtml;
	}

	public void setWikiContentHtml(String wikiContentHtml) {
		this.wikiContentHtml = wikiContentHtml;
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "LectureSubOneResponse [lectureId=" + lectureId + ", subId=" + subId + ", order=" + order + ", title="
				+ title + ", viewCount=" + viewCount + ", likeCount=" + likeCount + ", nickname=" + nickname
				+ ", playTime=" + playTime + ", playerUrl=" + playerUrl + ", wikiContent=" + wikiContent
				+ ", wikiContentHtml=" + wikiContentHtml + ", tagName=" + tagName + ", userLikeYn=" + userLikeYn + "]";
	}

}
