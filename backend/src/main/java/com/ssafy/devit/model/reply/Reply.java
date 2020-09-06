package com.ssafy.devit.model.reply;

import java.util.Date;

public class Reply {
	private long boardReplyId;
	private long boardId;	
	private long userId;
	private Date replyCreated;
	private String replyContent;
	private long parentReplyId;
	//private Date replyModified;
	//private String deleteYn;
	
	public Reply(long boardId, long userId, String replyContent, long parentReplyId){
		//this.boardReplyId = boardReplyId;
		this.boardId = boardId;
		this.userId = userId;
		// this.replyCreated = replyCreated;
		this.replyContent = replyContent;
		this.parentReplyId = parentReplyId;
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

	public Date getReplyCreated() {
		return replyCreated;
	}

	public void setReplyCreated(Date replyCreated) {
		this.replyCreated = replyCreated;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public long getParentReplyId() {
		return parentReplyId;
	}

	public void setParentReplyId(long parentReplyId) {
		this.parentReplyId = parentReplyId;
	}
	
	
}
