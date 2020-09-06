package com.ssafy.devit.model.lecture;

public class MainStatusResponse {
	private String totalUsers; //누적 회원 수
	private String totalDeviters; //강의에 참여한 사람
	private String totalLectures; //누적 강의 수
	
	
	
	public String getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
	}
	public String gettotalDeviters() {
		return totalDeviters;
	}
	public void settotalDeviters(String totalDeviters) {
		this.totalDeviters = totalDeviters;
	}
	public String getTotalLectures() {
		return totalLectures;
	}
	public void setTotalLectures(String totalLectures) {
		this.totalLectures = totalLectures;
	}
	

	public MainStatusResponse() {}
	
	public MainStatusResponse(String totalUsers, String totalDeviters, String totalLectures){
		this.totalUsers = totalUsers;
		this.totalDeviters = totalDeviters;
		this.totalLectures = totalLectures;
	}

}
