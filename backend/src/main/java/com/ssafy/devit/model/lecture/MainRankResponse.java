package com.ssafy.devit.model.lecture;

import java.util.Date;

public class MainRankResponse {
	private long userId; 
	private String nickname; 
	private String email; 
	private String profile; 
	private String totalScore; 
	
	public MainRankResponse() {}
	
	public MainRankResponse(long userId, String nickname, String email, String profile, String totalScore){
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.profile = profile;
		this.totalScore = totalScore;
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


	public String getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	
}
