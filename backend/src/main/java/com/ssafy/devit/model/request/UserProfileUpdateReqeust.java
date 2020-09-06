package com.ssafy.devit.model.request;

public class UserProfileUpdateReqeust {
	private long userId;
	private String nickname;
	private String profile;
	private String password;
	private String newPassword;
	private String modifyType;

	public UserProfileUpdateReqeust(long userId, String nickname, String profile, String password, String newPassword, String modifyType) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.profile = profile;
		this.password = password;
		this.newPassword = newPassword;
		this.modifyType = modifyType;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public UserProfileUpdateReqeust() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UserProfileUpdateReqeust [userId=" + userId + ", nickname=" + nickname + ", profile=" + profile
				+ ", password=" + password + "]";
	}

}
