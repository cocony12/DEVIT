package com.ssafy.devit.model.request;

public class LectureAuthRequest {
	private long authId;
	private long lectureId;
	private long userId;
	private String lectureRole;

	
	
	public LectureAuthRequest() {
		super();
	}

	public LectureAuthRequest(long authId, long lectureId, long userId, String lectureRole) {
		super();
		this.authId = authId;
		this.lectureId = lectureId;
		this.userId = userId;
		this.lectureRole = lectureRole;
	}

	public long getAuthId() {
		return authId;
	}

	public void setAuthId(long authId) {
		this.authId = authId;
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

	public String getLectureRole() {
		return lectureRole;
	}

	public void setLectureRole(String lectureRole) {
		this.lectureRole = lectureRole;
	}

	@Override
	public String toString() {
		return "LectureAuthRequest [authId=" + authId + ", lectureId=" + lectureId + ", userId=" + userId
				+ ", lectureRole=" + lectureRole + "]";
	}

}
