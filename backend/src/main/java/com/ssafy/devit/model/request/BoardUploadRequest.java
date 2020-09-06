package com.ssafy.devit.model.request;

import java.util.Date;

public class BoardUploadRequest {

	private long userId;
	private String boardTitle;
	private String boardContent;
	private String boardContentHtml;
	private long boardType;
	private long boardCount;

	public BoardUploadRequest(long userId, String boardTitle, String boardContent, String boardContentHtml, long boardType, Date boardCreated,
			long boardCount, Date boardModified) {
		super();
		this.userId = userId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardContentHtml = boardContentHtml;
		this.boardType = boardType;
		this.boardCount = boardCount;
	}

	public BoardUploadRequest() {
		super();
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

	public long getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(long boardCount) {
		this.boardCount = boardCount;
	}

}
