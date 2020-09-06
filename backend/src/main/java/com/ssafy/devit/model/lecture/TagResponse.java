package com.ssafy.devit.model.lecture;

// 각 태그 정보들을 담아서 넘겨줄 DTO
public class TagResponse {
	private int tagId;
	private String tagName;
	private int tagCount;

	public TagResponse() {
	}

	public TagResponse(int tagId, String tagName, int tagCount) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagCount = tagCount;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getTagCount() {
		return tagCount;
	}

	public void setTagCount(int tagCount) {
		this.tagCount = tagCount;
	}

	@Override
	public String toString() {
		return "TagResponse [tagId=" + tagId + ", tagName=" + tagName + ", tagCount=" + tagCount + "]";
	}

}
