package com.ssafy.devit.model.notice;

import java.util.Date;

public class NoticeResponse {
	private long noticeId;
	private long noticeType;
	private long userId;
	private String noticeYn;
	private String readYn;
	private Date createdDate;
	private long boardId;
	private long boardReplyId;
	private long boardType;
	private String boardTitle;
	private String boardContent;
	private String replyContent;
	private long lectureId;
	private long subId;
	private long subHisId;
	private String lectureTitle;
	private String subIndexTitle;
	private String reqType;
	private long createUserId;
	private String createUserNickname;
	private String profile;
	
	public NoticeResponse() {}
	
	

	public String getReadYn() {
		return readYn;
	}



	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}



	public long getBoardType() {
		return boardType;
	}



	public void setBoardType(long boardType) {
		this.boardType = boardType;
	}



	public String getBoardTitle() {
		return boardTitle;
	}



	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}



	public String getBoardContent() {
		return boardContent;
	}



	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}



	public String getReplyContent() {
		return replyContent;
	}



	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}



	public String getLectureTitle() {
		return lectureTitle;
	}



	public void setLectureTitle(String lectureTitle) {
		this.lectureTitle = lectureTitle;
	}



	public String getSubIndexTitle() {
		return subIndexTitle;
	}



	public void setSubIndexTitle(String subIndexTitle) {
		this.subIndexTitle = subIndexTitle;
	}



	public String getReqType() {
		return reqType;
	}



	public void setReqType(String reqType) {
		this.reqType = reqType;
	}



	public long getCreateUserId() {
		return createUserId;
	}



	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}



	public String getCreateUserNickname() {
		return createUserNickname;
	}



	public void setCreateUserNickname(String createUserNickname) {
		this.createUserNickname = createUserNickname;
	}



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



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}




}
