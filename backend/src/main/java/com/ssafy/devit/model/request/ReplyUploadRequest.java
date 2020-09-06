package com.ssafy.devit.model.request;

import java.util.Date;

public class ReplyUploadRequest {

	private long boardReplyId;
	private long boardId;
	private long userId;	
	private String replyContent;
	private long parentReplyId;

	public ReplyUploadRequest(long boardId, long userId, String replyContent, long parentReplyID) {
		super();
		//this.boardReplyId = boardReplyId;
		this.boardId = boardId;
		this.userId = userId;		
		this.replyContent = replyContent;
		this.parentReplyId = parentReplyId;
	}

	public ReplyUploadRequest() {
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

	public long getParentReplyId() {
		return parentReplyId;
	}

	public void setParentReplyId(long parentReplyId) {
		this.parentReplyId = parentReplyId;
	}


}
