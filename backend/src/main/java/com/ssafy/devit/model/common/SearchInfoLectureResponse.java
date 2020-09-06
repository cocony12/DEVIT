package com.ssafy.devit.model.common;

import io.swagger.annotations.ApiModelProperty;

public class SearchInfoLectureResponse {
	@ApiModelProperty(example = "대표 프로젝트 ID")
	private long lectureId;
	@ApiModelProperty(example = "대표 프로젝트 제목")
	private String title;
	@ApiModelProperty(example = "대표  프로젝트 내용")
	private String content;
	@ApiModelProperty(example = "대표 프로젝트 썸네일")
	private String thumbnailUrl;
	@ApiModelProperty(example = "대표 프로젝트를 생성한 사용자 닉네임")
	private String nickname;
	@ApiModelProperty(example = "대표 프로젝트를 생성한 사용자 프로필")
	private String profile;
	@ApiModelProperty(example = "대표 프로젝트에 속해있는 목차의 개수, 총 강의 수")
	private int lectureCount;
	@ApiModelProperty(example = "대표 프로젝트 조회 수")
	private int viewCount;
	@ApiModelProperty(example = "대표 프로젝트 좋아요 수")
	private int likeCount;
	@ApiModelProperty(example = "해당 API 요청시 반환되는 대표 프로젝트 총 개수")
	private int totalCount;
	@ApiModelProperty(example = "태그들")
	private String tagName;
	@ApiModelProperty(example = "초,중,고급 타입")
	private int type;
	@ApiModelProperty(example = "사용자 좋아요 처리 여부")
	private boolean userLikeYn;

	public SearchInfoLectureResponse() {

	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public long getLectureId() {
		return lectureId;
	}

	public void setLectureId(long lectureId) {
		this.lectureId = lectureId;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
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

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isUserLikeYn() {
		return userLikeYn;
	}

	public void setUserLikeYn(boolean userLikeYn) {
		this.userLikeYn = userLikeYn;
	}

	@Override
	public String toString() {
		return "SearchInfoResponse [lectureId=" + lectureId + ", title=" + title + ", content=" + content
				+ ", thumbnailUrl=" + thumbnailUrl + ", nickname=" + nickname + ", lectureCount=" + lectureCount
				+ ", viewCount=" + viewCount + ", likeCount=" + likeCount + ", tagName=" + tagName + ", type=" + type
				+ ", userLikeYn=" + userLikeYn + "]";
	}

}
