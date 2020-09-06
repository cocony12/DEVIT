package com.ssafy.devit.model.reply;

import java.util.Date;

public class ReplyResponse {
	private long boardReplyId;
	private long boardId;	
	private long userId;
	private String userName;
	private String profile;
	private Date replyCreated;
	private String replyContent;
	private long parentReplyId;
	private Date replyModified;
	private String deleteYn;
	private String isMine;
	
	public ReplyResponse() {}
	
	public ReplyResponse(long boardReplyId, long boardId, long userId, String userName, String profile, Date replyCreated, String replyContent, long parentReplyId, Date replyModified,
	String deleteYn, String isMine){
		this.boardReplyId = boardReplyId;
		this.boardId = boardId;
		this.userId = userId;
		this.userName = userName;
		this.profile = profile;
		this.replyCreated = replyCreated;
		this.replyContent = replyContent;
		this.parentReplyId = parentReplyId;
		this.replyModified = replyModified;
		this.deleteYn = deleteYn;
		this.isMine = isMine;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	////////////////////////////////////////////////////////
	public long getBoardReplyId() {
		return boardReplyId;
	}
	public void setBoardReplyId(long boardReplyId) {
		this.boardReplyId = boardReplyId;
	}
	////////////////////////////////////////////////////////
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	////////////////////////////////////////////////////////
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	////////////////////////////////////////////////////////
	public Date getReplyCreated() {
		return replyCreated;
	}
	public void setReplyCreated(Date replyCreated) {
		this.replyCreated = replyCreated;
	}
	////////////////////////////////////////////////////////
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	////////////////////////////////////////////////////////
	public long getParentReplyId() {
		return parentReplyId;
	}
	public void setParentReplyId(long parentReplyId) {
		this.parentReplyId = parentReplyId;
	}	
	////////////////////////////////////////////////////////
	public Date getReplyModified() {
		return replyModified;
	}
	public void setReplyModified(Date replyModified) {
		this.replyModified = replyModified;
	}
	////////////////////////////////////////////////////////
	public String getDeleteYn() {
		return deleteYn;
	}	
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	////////////////////////////////////////////////////////
	public String getIsMine() {
		return isMine;
	}	
	public void setIsMine(String isMine) {
		this.isMine = isMine;
	}
	
	
}
