package com.ssafy.devit.model.lecture;

public class LectureSubIndexResponse {
	private long lectureid;
	private long subId;
	private String title;
	private String playTime;
	private int order;
	private long commonId;
	private String tags;
	private boolean wikiYn;
	private boolean videoYn;

	public LectureSubIndexResponse() {
		super();
	}

	public LectureSubIndexResponse(long lectureid, long subId, String title, String playTime, int order, long commonId,
			String tags, boolean wikiYn, boolean videoYn) {
		super();
		this.lectureid = lectureid;
		this.subId = subId;
		this.title = title;
		this.playTime = playTime;
		this.order = order;
		this.commonId = commonId;
		this.tags = tags;
		this.wikiYn = wikiYn;
		this.videoYn = videoYn;
	}

	public long getLectureid() {
		return lectureid;
	}

	public void setLectureid(long lectureid) {
		this.lectureid = lectureid;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public boolean isWikiYn() {
		return wikiYn;
	}

	public void setWikiYn(boolean wikiYn) {
		this.wikiYn = wikiYn;
	}

	public boolean isVideoYn() {
		return videoYn;
	}

	public void setVideoYn(boolean videoYn) {
		this.videoYn = videoYn;
	}

	@Override
	public String toString() {
		return "LectureSubIndexResponse [lectureid=" + lectureid + ", subId=" + subId + ", title=" + title
				+ ", playTime=" + playTime + ", order=" + order + "]";
	}

}
