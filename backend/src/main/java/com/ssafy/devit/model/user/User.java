package com.ssafy.devit.model.user;

import java.util.Date;

public class User {

	private long userId;
	private String nickname;
	private String email;
	private String emailConfirm;
	private String active;
	private String profile;
	private Date created;
	private Date deleted;

	public User() {

	}

	public User(long userId, String nickname, String email, String emailConfirm, String active, String profile,
			Date created, Date deleted) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.emailConfirm = emailConfirm;
		this.active = active;
		this.profile = profile;
		this.created = created;
		this.deleted = deleted;
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

	public String getEmailConfirm() {
		return emailConfirm;
	}

	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", nickname=" + nickname + ", email=" + email + ", emailConfirm="
				+ emailConfirm + ", active=" + active + ", profile=" + profile + ", created=" + created + ", deleted="
				+ deleted + "]";
	}

}
