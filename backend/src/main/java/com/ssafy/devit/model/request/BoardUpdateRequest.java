package com.ssafy.devit.model.request;

import java.util.Date;

public class BoardUpdateRequest {

	private long boardId;
	private String boardTitle;
	private String boardContent;
	private String boardContentHtml;
	private long boardType;
	private long boardCount; // 조회수

	public BoardUpdateRequest(long boardId, String boardTitle, String boardContent, String boardContentHtml, long boardType, long boardCount) {
		super();
		this.boardId = boardId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardContentHtml = boardContentHtml;
		this.boardType = boardType;
		this.boardCount = boardCount;
	}

	public BoardUpdateRequest() {
		super();
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

	public long getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(long boardCount) {
		this.boardCount = boardCount;
	}

}
