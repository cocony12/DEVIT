package com.ssafy.devit.service;

import java.util.List;

import com.ssafy.devit.model.lecture.BoardResponse;
import com.ssafy.devit.model.request.BoardRequest;
import com.ssafy.devit.model.request.BoardWithLectureRequest;

public interface BoardService {
	public void upload(BoardRequest board) throws Exception;
	public void uploadNotice(long  boardId) throws Exception;
	public BoardResponse info(long bid) throws Exception;
	public void delete(long bid) throws Exception;
	public void update(BoardResponse boardResponse, long bid) throws Exception;
	public List<BoardResponse> listinfo(long startPage, long type, long itemsperpage, String searchselect, String searchtxt) throws Exception;

	public void createBoardWithLecture(BoardWithLectureRequest boardWithLectureRequest) throws Exception;
	public List<BoardResponse> lectureQnaList(long lectureId, long subId) throws Exception;
	
	public List<BoardResponse> myBoardList(long startPage, long itemsperpage) throws Exception;
	public List<BoardResponse> myReplyList(long startPage, long itemsperpage) throws Exception;
	
	public long getBoardTypeById(long boardId) throws Exception;
}
