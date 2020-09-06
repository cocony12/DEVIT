package com.ssafy.devit.service;

import java.util.List;
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.notice.NoticeResponse;
import com.ssafy.devit.model.notice.NoticeDetailResponse;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.repository.BoardRepository;
import com.ssafy.devit.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService { // to NoticeRepository
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Override	

	public List<NoticeResponse> getNotice(long user_id) throws Exception {
		if(user_id < 1) {
			throw new Exception("잘못된 type이 나왔습니다.");
		}
		
		noticeRepository.setNoticeAlertAll(user_id);
		
		return noticeRepository.getNoticeList(user_id);
		
	}
	
	public String getNoticeCnt(long user_id) throws Exception {
		if(user_id < 1) {
			throw new Exception("잘못된 type이 나왔습니다.");
		}

		return noticeRepository.getNoticeCount(user_id);
		
	}
	
	public void setNoticeRead(long notice_id) throws Exception {
		noticeRepository.setNoticeRead(notice_id);
	}
		
	public void setNoticeAlert(long notice_id) throws Exception {
		noticeRepository.setNoticeAlert(notice_id);
	}
	
	public void setNoticeAlertAll(long user_id) throws Exception {
		noticeRepository.setNoticeAlertAll(user_id);
	}
	
	
	public NoticeDetailResponse getNoticeDetailInfo(long notice_id) throws Exception {
		UserAuthDetails user = null;
		
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		
		return noticeRepository.getNoticeDetailInfo(notice_id);
		
	}
	
	public void removeNoticeAll() throws Exception {
		UserAuthDetails user = null;
		
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		
		noticeRepository.removeNoticeAll(user.getUserId());
	};
	
	public void removeNoticeEach(long notice_id) throws Exception {
		UserAuthDetails user = null;
		
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		
		noticeRepository.removeNoticeEach(notice_id);
	};
	
	public void removeNoticeRead() throws Exception {
		UserAuthDetails user = null;
		
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		
		noticeRepository.removeNoticeRead(user.getUserId());
	};
}
