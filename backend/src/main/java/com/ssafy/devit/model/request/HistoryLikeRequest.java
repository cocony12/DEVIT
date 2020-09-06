package com.ssafy.devit.model.request;

public class HistoryLikeRequest {
	private long lectureId;
	private long subId;
	private long subHisId;

	public HistoryLikeRequest(long lectureId, long subId, long subHisId) {
		super();
		this.lectureId = lectureId;
		this.subId = subId;
		this.subHisId = subHisId;
	}

	public HistoryLikeRequest() {
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

	public long getSubHisId() {
		return subHisId;
	}

	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
	}

	@Override
	public String toString() {
		return "HistoryLikeRequest [lectureId=" + lectureId + ", subId=" + subId + ", subHisId=" + subHisId + "]";
	}

}
