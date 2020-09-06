package com.ssafy.devit.model.request;

import java.util.Date;

public class ReplyUpdateRequest {

	private long boardReplyId;
	private long boardId;
	private long userId;
	//private long userId;	
	private String replyContent;
//	private long parentReplyId;
//	private Date replyModified;
	//private String deleteYn;

	public ReplyUpdateRequest(long boardReplyId, long boardId, String replyContent) {
		super();
		this.boardReplyId = boardReplyId;
		this.boardId = boardId;
		//this.userId = userId;		
		this.replyContent = replyContent;
		//this.parentReplyId = parentReplyId;
		//this.replyModified = replyModified;
	}

	
	
	public ReplyUpdateRequest(long boardReplyId, long boardId, long userId, String replyContent) {
	super();
	this.boardReplyId = boardReplyId;
	this.boardId = boardId;
	this.userId = userId;
	this.replyContent = replyContent;
}



	public ReplyUpdateRequest() {
		super();
	}

	public long getBoardReplyId() {
		return boardReplyId;
	}

	public void setBoardReplyId(long boardReplyId) {
		this.boardReplyId = boardReplyId;
	}
	
	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

//	public long getParentReplyId() {
//		return parentReplyId;
//	}
//
//	public void setParentReplyId(long parentReplyId) {
//		this.parentReplyId = parentReplyId;
//	}
//	
//	public Date getReplyModified() {
//		return replyModified;
//	}
//	public void setReplyModified(Date replyModified) {
//		this.replyModified = replyModified;
//	}
//	

}
