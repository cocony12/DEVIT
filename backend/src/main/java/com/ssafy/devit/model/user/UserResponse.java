package com.ssafy.devit.model.user;

import java.util.Date;

public class UserResponse {
	private long userId;
	private String nickname;
	private String email;
	private String profile;
	private String active;
	private Date created;
	private Date deleted;
	private String emailConfirm;

	public UserResponse() {

	}

	public UserResponse(long userId, String nickname, String email, String profile, String active, Date created,
			Date deleted, String emailConfirm) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.profile = profile;
		this.active = active;
		this.created = created;
		this.deleted = deleted;
		this.emailConfirm = emailConfirm;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public String getEmailConfirm() {
		return emailConfirm;
	}

	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}

	@Override
	public String toString() {
		return "UserResponse [userId=" + userId + ", nickname=" + nickname + ", email=" + email + ", profile=" + profile
				+ ", active=" + active + ", created=" + created + ", deleted=" + deleted + ", emailConfirm="
				+ emailConfirm + "]";
	}

	public void refact(User user) {
		userId = user.getUserId();
		nickname = user.getNickname();
		email = user.getEmail();
		profile = user.getProfile();
		active = user.getActive();
		created = user.getCreated();
		deleted = user.getDeleted();
		emailConfirm = user.getEmail();
	}

}
