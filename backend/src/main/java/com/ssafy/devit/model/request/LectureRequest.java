package com.ssafy.devit.model.request;

import java.util.Arrays;

public class LectureRequest {
	private long userId;
	private long lectureId;
	private long commonId;
	private String title;
	private String content;
	private String thumbnailUrl;
	private int type;
	private String[] tags;

	public LectureRequest() {

	}

	public LectureRequest(long userId, long lectureId, long commonId, String title, String content, String thumbnailUrl,
			int type, String[] tags) {
		super();
		this.userId = userId;
		this.lectureId = lectureId;
		this.commonId = commonId;
		this.title = title;
		this.content = content;
		this.thumbnailUrl = thumbnailUrl;
		this.type = type;
		this.tags = tags;
	}

	public long getLectureId() {
		return lectureId;
	}

	public void setLectureId(long lectureId) {
		this.lectureId = lectureId;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LectrueRequest [userId=" + userId + ", lectureId=" + lectureId + ", commonId=" + commonId + ", title="
				+ title + ", content=" + content + ", thumbnailUrl=" + thumbnailUrl + ", type=" + type + ", tags="
				+ Arrays.toString(tags) + "]";
	}

}
