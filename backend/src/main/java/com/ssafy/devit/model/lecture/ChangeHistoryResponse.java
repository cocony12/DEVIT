package com.ssafy.devit.model.lecture;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class ChangeHistoryResponse {
	@ApiModelProperty(example = "프로젝트 ID")
	private long lectureId;
	@ApiModelProperty(example = "강의 ID")
	private long subId;
	@ApiModelProperty(example = "요청 ID")
	private long subHisId;
	@ApiModelProperty(example = "요청 생성일")
	private Date created;
	@ApiModelProperty(example = "요청 승인/거절 일")
	private Date modified;
	@ApiModelProperty(example = "요청 제목")
	private String title;
	@ApiModelProperty(example = "요청 썸네일")
	private String thumbnailUrl;
	@ApiModelProperty(example = "영상 시간")
	private String playTime;
	@ApiModelProperty(example = "영상 링크")
	private String playerUrl;
	@ApiModelProperty(example = "위키 내용")
	private String wikiContent;
	@ApiModelProperty(example = "위키 HTML")
	private String wikiContentHtml;
	@ApiModelProperty(example = "조회수")
	private String viewCount;
	@ApiModelProperty(example = "요청 타입 video/wiki")
	private String reqType;
	@ApiModelProperty(example = "승인/거절/대기 타입")
	private boolean acceptYn;
	@ApiModelProperty(example = "사용자 ID")
	private long userId;
	@ApiModelProperty(example = "사용자 닉네임")
	private String nickname;
	@ApiModelProperty(example = "사용자 프로필")
	private String profile;
	@ApiModelProperty(example = "사용자 이메일")
	private String email;

	public ChangeHistoryResponse() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public long getSubHisId() {
		return subHisId;
	}

	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public boolean isAcceptYn() {
		return acceptYn;
	}

	public void setAcceptYn(boolean acceptYn) {
		this.acceptYn = acceptYn;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
		return "ChangeHistoryResponse [lectureId=" + lectureId + ", subId=" + subId + ", subHisId=" + subHisId
				+ ", created=" + created + ", modified=" + modified + ", title=" + title + ", thumbnailUrl="
				+ thumbnailUrl + ", playTime=" + playTime + ", playerUrl=" + playerUrl + ", wikiContent=" + wikiContent
				+ ", wikiContentHtml=" + wikiContentHtml + ", viewCount=" + viewCount + ", reqType=" + reqType
				+ ", acceptYn=" + acceptYn + ", userId=" + userId + ", nickname=" + nickname + ", profile=" + profile
				+ "]";
	}

}
