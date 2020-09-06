package com.ssafy.devit.model.user;

import io.swagger.annotations.ApiModelProperty;

public class LoginResponse {
	@ApiModelProperty(example = "Token 값")
	private String token;
	@ApiModelProperty(example = "User 닉네임")
	private String nickname;
	@ApiModelProperty(example = "User 프로필 사진 URL")
	private String profile;
	@ApiModelProperty(example = "User ID")
	private long userId;

	public LoginResponse(String token, String nickname, String profile, long userId) {
		super();
		this.token = token;
		this.nickname = nickname;
		this.profile = profile;
		this.userId = userId;
	}

	public LoginResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LoginResponse [token=" + token + ", nickname=" + nickname + ", profile=" + profile + ", userId="
				+ userId + "]";
	}

}
