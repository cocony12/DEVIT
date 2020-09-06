package com.ssafy.devit.service;

import java.util.List;

import com.ssafy.devit.model.reply.Reply;
import com.ssafy.devit.model.reply.ReplyResponse;
import com.ssafy.devit.model.request.ReplyUpdateRequest;

public interface ReplyService {
	public void upload(Reply reply) throws Exception;
	public void uploadNotice(long boardReplyId) throws Exception;
	public List<ReplyResponse> info(long boardId) throws Exception;
	public void delete(long reply_id) throws Exception;
	public void update(ReplyUpdateRequest request) throws Exception;
	public List<ReplyResponse> listinfo(long startPage, long type, long itemsperpage) throws Exception;
}
