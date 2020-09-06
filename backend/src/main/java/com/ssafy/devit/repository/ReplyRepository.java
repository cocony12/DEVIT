package com.ssafy.devit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.devit.model.reply.Reply;
import com.ssafy.devit.model.reply.ReplyResponse;
import com.ssafy.devit.model.request.ReplyUpdateRequest;

public interface ReplyRepository {
	public void upload(Reply reply);
	public void uploadNotice(@Param("boardReplyId") long boardReplyId);
	public List<ReplyResponse> info(@Param("bid") long bid, @Param("userId") long userId) throws Exception;
	public void delete(long reply_id) throws Exception;
	public void update(ReplyUpdateRequest request) throws Exception;
	public List<ReplyResponse> listinfo(@Param("startPage") long startPage, @Param("type") long type, @Param("itemsperpage") long itemsperpage) throws Exception;
}
