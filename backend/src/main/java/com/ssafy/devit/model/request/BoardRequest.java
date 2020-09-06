package com.ssafy.devit.model.request;

import java.util.Date;

public class BoardRequest {

	private long boardId;
	private long userId;
	private String boardTitle;
	private String boardContent;
	private String boardContentHtml;
	private long boardType;
	private Date boardCreated;
	private long boardCount;
	private Date boardModified;
	
	public BoardRequest() {

	}

	public BoardRequest(long userId, String boardTitle, String boardContent, String boardContentHtml, long boardType, long boardCount) {
		this.userId = userId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardContentHtml = boardContentHtml;
		this.boardType = boardType;
		this.boardCount = boardCount;
	}
	
	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
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

	public String getBoardContentHtml() {
		return boardContentHtml;
	}

	public void setBoardContentHtml(String boardContentHtml) {
		this.boardContentHtml = boardContentHtml;
	}

	public long getBoardType() {
		return boardType;
	}

	public void setBoardType(long boardType) {
		this.boardType = boardType;
	}

	public Date getBoardCreated() {
		return boardCreated;
	}

	public void setBoardCreated(Date boardCreated) {
		this.boardCreated = boardCreated;
	}

	public long getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(long boardCount) {
		this.boardCount = boardCount;
	}

	public Date getBoardModified() {
		return boardModified;
	}

	public void setBoardModified(Date boardModified) {
		this.boardModified = boardModified;
	}
}
