package com.ssafy.devit.model.notice;

import java.util.Date;

public class NoticeDetailResponse {
	private long noticeId;
	private long noticeType;
	private long userId;
	private String noticeYn;
	private String readYn;
	private long boardId;
	private long boardReplyId;
	private long subHisId;
	private long lectureId;
	private long subId;
	private Date createdDate;
	private long boardType;
	private int indexOrder;
	
	public NoticeDetailResponse() {}
	

	////////////////////////////////////////////////////////
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
	////////// noticeYn
	public String getNoticeYn() {
		return noticeYn;
	}
	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}
	
	public String getReadYn() {
		return readYn;
	}


	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}


	///////////
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	//////////
	public long getBoardReplyId() {
		return boardReplyId;
	}
	public void setBoardReplyId(long boardReplyId) {
		this.boardReplyId = boardReplyId;
	}
	////////////
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
	/////////////
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public long getBoardType() {
		return boardType;
	}


	public void setBoardType(long boardType) {
		this.boardType = boardType;
	}


	public int getIndexOrder() {
		return indexOrder;
	}


	public void setIndexOrder(int indexOrder) {
		this.indexOrder = indexOrder;
	}

}
