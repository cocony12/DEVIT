package com.ssafy.devit.model.lecture;

import java.util.Date;

public class BoardResponse {
	private long boardId;
	private long userId;
	private String userName;
	private String profile;
	private String boardTitle;
	private String boardContent;
	private String boardContentHtml;
	private long boardType;
	private Date boardCreated;
	private Date boardModified;
	private long boardCount;
	private long replyCount;
	private String isMine;
	private long pageCnt;
	private long lectureId;
	private long subId;
	private long boardReplyId;
	
	public BoardResponse() {}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public long getBoardType() {
		return boardType;
	}

	public void setBoardType(long boardType) {
		this.boardType = boardType;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getNickName() {
		return userName;
	}

	public void setNickName(String nickName) {
		this.userName = nickName;
	}

	public Date getBoardCreated() {
		return boardCreated;
	}

	public void setBoardCreated(Date boardCreated) {
		this.boardCreated = boardCreated;
	}

	public Date getBoardModified() {
		return boardModified;
	}

	public void setBoardModified(Date boardModified) {
		this.boardModified = boardModified;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(long boardCount) {
		this.boardCount = boardCount;
	}
	
	
	
	public long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(long replyCount) {
		this.replyCount = replyCount;
	}

	public String getIsMine() {
		return isMine;
	}
	
	public void setIsMine(String isMine) {
		this.isMine = isMine;
	}
	
	public long getPageCnt() {
		return pageCnt;
	}
	
	public void setPageCnt(long pageCnt) {
		this.pageCnt = pageCnt;
	}

	public long getLectureId() {
		return lectureId;
	}

	public void setLectureId(long lectureId) {
		this.lectureId = lectureId;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public long getBoardReplyId() {
		return boardReplyId;
	}

	public void setBoardReplyId(long boardReplyId) {
		this.boardReplyId = boardReplyId;
	}

	public String getBoardContentHtml() {
		return boardContentHtml;
	}

	public void setBoardContentHtml(String boardContentHtml) {
		this.boardContentHtml = boardContentHtml;
	}
	
}
