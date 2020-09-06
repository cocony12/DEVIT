package com.ssafy.devit.model.notice;

public class Notice {
	private long noticeId;
	private long noticeType;
	private long userId;
	private long boardId;
	private long subHisId;
	private long lectureId;
	private long subId;
	
	public Notice(long noticeId, long noticeType, long userId, long boardId, long subHisId, long lectureId, long subId){
		this.noticeId = noticeId;
		this.noticeType = noticeType;
		this.userId = userId;
		this.boardId = boardId;
		this.subHisId = subHisId;
		this.lectureId = lectureId;
		this.subId = subId;
	}


	public long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}
	//////////
	public long getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(long noticeType) {
		this.noticeType = noticeType;
	}
	//////////
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	//////////
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	//////////
	public long getSubHisId() {
		return subHisId;
	}
	public void setSubHisId(long subHisId) {
		this.subHisId = subHisId;
	}
	//////////	
	public long getLectureId() {
		return lectureId;
	}
	public void setLectureId(long lectureId) {
		this.lectureId = lectureId;
	}
	//////////
	public long getSubId() {
		return subId;
	}
	public void setSubId(long subId) {
		this.subId = subId;
	}
}
