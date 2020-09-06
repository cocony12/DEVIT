package com.ssafy.devit.model.request;

public class LectureSubHistoryRequest {
	private long subHisId;
	private long lectureId;
	private long subId;
	private long userId;
	private String playTime;
	private String playerUrl;
	private String wikiContent;
	private String wikiContentHtml;
	private String reqType;
	private String title;
	private String thumbnailUrl;

	public LectureSubHistoryRequest() {

	}

	public LectureSubHistoryRequest(long lectureId, long subId, long userId, String playTime, String playerUrl,
			String wikiContent, String wikiContentHtml, String reqType, String title, String thumbnailUrl) {
		super();
		this.lectureId = lectureId;
		this.subId = subId;
		this.userId = userId;
		this.playTime = playTime;
		this.playerUrl = playerUrl;
		this.wikiContent = wikiContent;
		this.wikiContentHtml = wikiContentHtml;
		this.reqType = reqType;
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
	}

	public long getSubHisId() {
		return subHisId;
	}
	
	public void setSubHisId(long subHisId) {
		this.subHisId =  subHisId;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
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

	@Override
	public String toString() {
		return "LectureSubHistoryRequest [lectureId=" + lectureId + ", subId=" + subId + ", userId=" + userId
				+ ", playTime=" + playTime + ", playerUrl=" + playerUrl + ", wikiContent=" + wikiContent
				+ ", wikiContentHtml=" + wikiContentHtml + ", reqType=" + reqType + ", title=" + title
				+ ", thumbnailUrl=" + thumbnailUrl + "]";
	}

}
