package com.ssafy.devit.model.lecture;

import java.util.Date;

public class LectureRoleUsersResponse {
	private long authId;
	private long lectureId;
	private long userId;
	private String nickname;
	private String profile;
	private String email;
	private String role;
	private Date created;
	private Date modified;

	public LectureRoleUsersResponse() {

	}

	public LectureRoleUsersResponse(long authId, long lectureId, long userId, String nickname, String profile,
			String email, String role, Date created, Date modified) {
		super();
		this.authId = authId;
		this.lectureId = lectureId;
		this.userId = userId;
		this.nickname = nickname;
		this.profile = profile;
		this.email = email;
		this.role = role;
		this.created = created;
		this.modified = modified;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "LectureRoleUsersResponse [authId=" + authId + ", lectureId=" + lectureId + ", userId=" + userId
				+ ", nickname=" + nickname + ", profile=" + profile + ", email=" + email + ", role=" + role
				+ ", created=" + created + ", modified=" + modified + "]";
	}

}
