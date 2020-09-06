package com.ssafy.devit.model.request;

import java.util.Arrays;

public class LectureSubsRequest {
	private long subId;
	private long lectureId;
	private long userId;
	private long commonId;
	private int order;
	private String title;
	private String[] tags;

	public LectureSubsRequest(long subId, long lectureId, long userId, long commonId, int order, String title,
			String[] tags) {
		super();
		this.subId = subId;
		this.lectureId = lectureId;
		this.userId = userId;
		this.commonId = commonId;
		this.order = order;
		this.title = title;
		this.tags = tags;
	}

	public LectureSubsRequest() {
		super();
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
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

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "LectureSubsRequest [subId=" + subId + ", lectureId=" + lectureId + ", userId=" + userId + ", commonId="
				+ commonId + ", order=" + order + ", title=" + title + ", tags=" + Arrays.toString(tags) + "]";
	}

}
