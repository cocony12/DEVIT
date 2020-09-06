package com.ssafy.devit.model.lecture;

public class LikeDTO {
	private int likeCount;
	private String likeFlag;

	public LikeDTO() {

	}

	public LikeDTO(int likeCount, String likeFlag) {
		this.likeCount = likeCount;
		this.likeFlag = likeFlag;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(String likeFlag) {
		this.likeFlag = likeFlag;
	}

	@Override
	public String toString() {
		return "LikeDTO [likeCount=" + likeCount + ", likeFlag=" + likeFlag + "]";
	}

}
