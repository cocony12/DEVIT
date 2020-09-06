package com.ssafy.devit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.devit.model.notice.NoticeResponse;
import com.ssafy.devit.model.notice.NoticeDetailResponse;

public interface NoticeRepository { // to Notice.xml	
	
	public List<NoticeResponse> getNoticeList(@Param("userId") long userId) throws Exception;

	public String getNoticeCount(@Param("userId") long userId) throws Exception;

	public void setNoticeRead(long noticeId) throws Exception;

	public void setNoticeAlert(long noticeId) throws Exception;

	public void setNoticeAlertAll(long userId) throws Exception;

	public void removeNoticeAll(long userId) throws Exception;

	public void removeNoticeEach(long noticeId) throws Exception;
	
	public void removeNoticeRead(long userId) throws Exception;

	public NoticeDetailResponse getNoticeDetailInfo(@Param("noticeId") long noticeId) throws Exception;
}
