package com.ssafy.devit.model.request;

import java.util.Date;

public class BoardWithLectureRequest {

	private long boardId;
	private long lectureId;
	private long subId;
	private long userId;
	private String boardTitle;
	private String boardContent;
	private String boardContentHtml;
	
	public BoardWithLectureRequest() {}
	
	public BoardWithLectureRequest(long lectureId, long subId, long userId, String boardTitle, String boardContent, String boardContentHtml) {
		this.lectureId = lectureId;
		this.subId = subId;
		this.userId = userId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardContentHtml = boardContentHtml;
	}
	
	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
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

}
