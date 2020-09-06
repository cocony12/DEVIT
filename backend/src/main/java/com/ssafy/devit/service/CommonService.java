package com.ssafy.devit.service;

import java.util.List;

import com.ssafy.devit.model.common.SearchInfoIndexResponse;
import com.ssafy.devit.model.common.SearchInfoLectureResponse;
import com.ssafy.devit.model.lecture.MainRankResponse;
import com.ssafy.devit.model.lecture.MainStatusResponse;

public interface CommonService {
	public List<SearchInfoLectureResponse> findInfomationLectureBySearch(String[] searchText, int startPage) throws Exception;
	
	public List<SearchInfoIndexResponse> findInfomationIndexBySearch(String[] searchText, int startPage) throws Exception;
	
	public MainStatusResponse getMainStatus() throws Exception;
	public List<MainRankResponse> getMainRank() throws Exception;
}
