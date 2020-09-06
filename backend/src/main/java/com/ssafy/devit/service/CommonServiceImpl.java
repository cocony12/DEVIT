package com.ssafy.devit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.common.Common;
import com.ssafy.devit.model.common.SearchInfoIndexResponse;
import com.ssafy.devit.model.common.SearchInfoLectureResponse;
import com.ssafy.devit.model.lecture.MainRankResponse;
import com.ssafy.devit.model.lecture.MainStatusResponse;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.repository.CommonRepository;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	CommonRepository commonRepository;

	@Override
	public List<SearchInfoLectureResponse> findInfomationLectureBySearch(String[] searchText, int startPage)
			throws Exception {
		if (searchText.length == 0) {
			throw new Exception("1개 이상의 검색어가 필요합니다.");
		}
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		startPage = (startPage - 1) * 20;
		return commonRepository.selectInfomationLectureBySearch(user.getUserId(), searchText, startPage);
	}

	@Override
	public List<SearchInfoIndexResponse> findInfomationIndexBySearch(String[] searchText, int startPage)
			throws Exception {
		if (searchText.length == 0) {
			throw new Exception("1개 이상의 검색어가 필요합니다.");
		}
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		startPage = (startPage - 1) * 20;
		return commonRepository.selectInfomationIndexBySearch(user.getUserId(), searchText, startPage);
	};

	@Override
	public MainStatusResponse getMainStatus() throws Exception {

		return commonRepository.getMainStatus();
	}
	
	@Override
	public List<MainRankResponse> getMainRank() throws Exception{
		return commonRepository.getMainRank();
	}

}
