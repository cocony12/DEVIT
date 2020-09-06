package com.ssafy.devit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.lecture.BoardResponse;
import com.ssafy.devit.model.request.BoardRequest;
import com.ssafy.devit.model.request.BoardWithLectureRequest;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.repository.BoardRepository;

@Service
public class BoardServicelmpl implements BoardService {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public void upload(BoardRequest board) throws Exception {
		if(board == null) {
			throw new Exception("잘못된 board가 나왔습니다.");
		}
		boardRepository.upload(board);
	}
	
	@Override
	public void uploadNotice(long boardId) throws Exception {		
		boardRepository.uploadNotice(boardId);
	}
	
	@Override
	public BoardResponse info(long bid) throws Exception {
		if(bid < 1) {
			throw new Exception("잘못된 boardId가 나왔습니다.");
		}
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return boardRepository.info(bid, user.getUserId());
	}
	
	@Override
	public void delete(long bid) throws Exception {
		if(bid < 1) {
			throw new Exception("잘못된 boardId가 나왔습니다.");
		}
		boardRepository.delete(bid);
	}
	
	@Override
	public void update(BoardResponse boardResponse, long bid) throws Exception {
		if(bid < 1) {
			throw new Exception("잘못된 boardId가 나왔습니다.");
		}
		boardRepository.update(boardResponse, bid);
	}
	
	@Override
	public List<BoardResponse> listinfo(long startPage, long type, long itemsperpage, String searchselect, String searchtxt) throws Exception {
		if(type < 1) {
			throw new Exception("잘못된 type이 나왔습니다.");
		}
		startPage = (startPage-1) * itemsperpage;
//		return boardRepository.listinfo(type);
		return boardRepository.listinfo(startPage, type, itemsperpage, searchselect, searchtxt);
		
	}
	
	@Override
	public void createBoardWithLecture(BoardWithLectureRequest boardWithLectureRequest) throws Exception{
		

		if(boardWithLectureRequest == null) {
			throw new Exception("잘못된 board가 나왔습니다.");
		}
		boardRepository.createBoardWithLecture(boardWithLectureRequest);
	};
	
	@Override
	public List<BoardResponse> lectureQnaList(long lectureId, long subId) throws Exception{
		if(lectureId < 1) {
			throw new Exception("잘못된 lectureId가 나왔습니다.");
		}else if(subId < 1) {
			throw new Exception("잘못된 subId가 나왔습니다.");
		}
		
		return boardRepository.lectureQnaList(lectureId, subId);
	}
	
	@Override
	public List<BoardResponse> myBoardList(long startPage, long itemsperpage) throws Exception {
		
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		startPage = (startPage-1) * itemsperpage;
		return boardRepository.myBoardList(user.getUserId(), startPage, itemsperpage);
	}
	
	@Override
	public List<BoardResponse> myReplyList(long startPage, long itemsperpage) throws Exception {
		
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		startPage = (startPage-1) * itemsperpage;
		return boardRepository.myReplyList(user.getUserId(), startPage, itemsperpage);
	}
	
	@Override
	public long getBoardTypeById(long boardId) throws Exception{
		if(boardId < 1) {
			throw new Exception("잘못된 boardId가 나왔습니다.");
		}
		return boardRepository.getBoardTypeById(boardId);
	}
}
