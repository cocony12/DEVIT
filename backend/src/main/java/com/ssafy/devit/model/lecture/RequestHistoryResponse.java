package com.ssafy.devit.model.lecture;

import io.swagger.annotations.ApiModelProperty;

public class RequestHistoryResponse {

	private String lectureThumbnailUrl;
	private String lectureTitle;
	@ApiModelProperty(example = "userProfileUrl")
	private String profile;
	@ApiModelProperty(example = "userNickname")
	private String nickname;
	@ApiModelProperty(example = "2020-08-06 05:41:06")
	private String created;
	@ApiModelProperty(example = "변수란!")
	private String subTitle;
	@ApiModelProperty(example = "1")
	private long subId;
	@ApiModelProperty(example = "Y 또는 N 또는 null")
	private String acceptYn;
	@ApiModelProperty(example = "video 또는 wiki")
	private String reqType;
	@ApiModelProperty(example = "videoThumbnailUrl")
	private String thumbnailUrl;
	@ApiModelProperty(example = "이런것이다!")
	private String hisTitle;
	@ApiModelProperty(example = "playerUrl")
	private String playerUrl;
	@ApiModelProperty(example = "13:30")
	private String playTime;
	@ApiModelProperty(example = "texttext.....")
	private String wikiContent;
	@ApiModelProperty(example = "<h1>text...</h1>")
	private String wikiContentHtml;
	@ApiModelProperty(example = "1")
	private long subHisId;
	private String indexWikiContent;
	private String indexWikiContentHtml;
	private boolean mainId;
	
	public RequestHistoryResponse() {

	}

	public String getIndexWikiContent() {
		return indexWikiContent;
	}

	
	public boolean isMainId() {
		return mainId;
	}

	public void setMainId(boolean mainId) {
		this.mainId = mainId;
	}

	public void setIndexWikiContent(String indexWikiContent) {
		this.indexWikiContent = indexWikiContent;
	}

	public String getIndexWikiContentHtml() {
		return indexWikiContentHtml;
	}

	public void setIndexWikiContentHtml(String indexWikiContentHtml) {
		this.indexWikiContentHtml = indexWikiContentHtml;
	}

	public String getLectureThumbnailUrl() {
		return lectureThumbnailUrl;
	}

	public void setLectureThumbnailUrl(String lectureThumbnailUrl) {
		this.lectureThumbnailUrl = lectureThumbnailUrl;
	}

	public String getLectureTitle() {
		return lectureTitle;
	}

	public void setLectureTitle(String lectureTitle) {
		this.lectureTitle = lectureTitle;
	}

	public long getSubHisId() {
		return subHisId;
	}

	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getAcceptYn() {
		return acceptYn;
	}

	public void setAcceptYn(String acceptYn) {
		this.acceptYn = acceptYn;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getHisTitle() {
		return hisTitle;
	}

	public void setHisTitle(String hisTitle) {
		this.hisTitle = hisTitle;
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

	@Override
	public String toString() {
		return "RequestHistoryResponse [lectureThumbnailUrl=" + lectureThumbnailUrl + "lectureTitle=" + lectureTitle
				+ ", profile=" + profile + ", nickname=" + nickname + ", created=" + created + ", subTitle=" + subTitle
				+ ", subId=" + subId + ", acceptYn=" + acceptYn + ", reqType=" + reqType + ", thumbnailUrl="
				+ thumbnailUrl + ", hisTitle=" + hisTitle + ", playerUrl=" + playerUrl + ", playTime=" + playTime
				+ ", wikiContent=" + wikiContent + ", wikiContentHtml=" + wikiContentHtml + "]";
	}

}
