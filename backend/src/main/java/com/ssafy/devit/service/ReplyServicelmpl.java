package com.ssafy.devit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.reply.Reply;
import com.ssafy.devit.model.reply.ReplyResponse;
import com.ssafy.devit.model.request.ReplyUpdateRequest;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.repository.ReplyRepository;

@Service
public class ReplyServicelmpl implements ReplyService {
	
	@Autowired
	ReplyRepository replyRepository;
	
	@Override
	public void upload(Reply reply) throws Exception {
		if(reply == null) {
			throw new Exception("잘못된 reply가 나왔습니다.");
		}
		replyRepository.upload(reply);		
	}
	
	@Override
	public void uploadNotice(long boardReplyId) throws Exception {		
		replyRepository.uploadNotice(boardReplyId);		
	}
	
	@Override
	public List<ReplyResponse> info(long boardId) throws Exception {
		if(boardId < 1) {
			throw new Exception("잘못된 replyId가 나왔습니다.");
		}
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return replyRepository.info(boardId, user.getUserId());
	}
	
	@Override
	public void delete(long reply_id) throws Exception {
		if(reply_id < 1) {
			throw new Exception("잘못된 replyId가 나왔습니다.");
		}
		replyRepository.delete(reply_id);
	}
	
	@Override
	public void update(ReplyUpdateRequest request) throws Exception {
		if(request.getBoardId() < 1 || request.getBoardReplyId() < 1) {
			throw new Exception("잘못된 replyId가 나왔습니다.");
		}
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setUserId(user.getUserId());
		replyRepository.update(request);
	}
	
	@Override
	public List<ReplyResponse> listinfo(long startPage, long type, long itemsperpage) throws Exception {
		if(type < 1) {
			throw new Exception("잘못된 type이 나왔습니다.");
		}
		startPage = (startPage-1) * itemsperpage;
//		return replyRepository.listinfo(type);
		return replyRepository.listinfo(startPage, type, itemsperpage);
		
	}
}
