package com.ssafy.devit.service;

import java.util.List;

import com.ssafy.devit.model.notice.Notice;
import com.ssafy.devit.model.notice.NoticeResponse;
import com.ssafy.devit.model.notice.NoticeDetailResponse;
import com.ssafy.devit.model.request.ReplyUpdateRequest;

public interface NoticeService { // to NoticeServiceImpl
	
	public List<NoticeResponse> getNotice(long user_id) throws Exception;
	
	public String getNoticeCnt(long user_id) throws Exception;
	
	public void setNoticeRead(long notice_id) throws Exception;

	public void setNoticeAlert(long notice_id) throws Exception;

	public void setNoticeAlertAll(long user_id) throws Exception;
	
	public NoticeDetailResponse getNoticeDetailInfo(long notice_id) throws Exception;
	
	public void removeNoticeAll() throws Exception;
	
	public void removeNoticeEach(long notice_id) throws Exception;
	
	public void removeNoticeRead() throws Exception;
}
