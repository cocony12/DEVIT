package com.ssafy.devit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.common.Common;
import com.ssafy.devit.model.lecture.ChangeHistoryResponse;
import com.ssafy.devit.model.lecture.LectureOneResponse;
import com.ssafy.devit.model.lecture.LectureRoleUsersResponse;
import com.ssafy.devit.model.lecture.LectureSubIndexResponse;
import com.ssafy.devit.model.lecture.LectureSubOneResponse;
import com.ssafy.devit.model.lecture.LecturesResponse;
import com.ssafy.devit.model.lecture.LikeDTO;
import com.ssafy.devit.model.lecture.RequestHistoryResponse;
import com.ssafy.devit.model.lecture.TagResponse;
import com.ssafy.devit.model.lecture.TheOhterSubLectureResponse;
import com.ssafy.devit.model.request.HistoryLikeRequest;
import com.ssafy.devit.model.request.LectureAuthRequest;
import com.ssafy.devit.model.request.LectureRequest;
import com.ssafy.devit.model.request.LectureSubHistoryRequest;
import com.ssafy.devit.model.request.LectureSubOtherRequest;
import com.ssafy.devit.model.request.LectureSubsRequest;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.repository.LectureRepository;

@Service
public class LectureServiceImpl implements LectureService {

	@Autowired
	LectureRepository lectureRepository;

	@Override
	public LectureOneResponse createDummyLecture() throws Exception { // 더미 프로젝트 생성 및 해당 프로젝트 권한 부여
		// common id 생성 및 Common id 받아오기
		Common common = new Common();
		lectureRepository.insertCommonId(common);

		// 사용자 id 가져오기
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("프로젝트를 생성할 권한이 없습니다");
		}
		
		// DAO에게 넘겨줄 DTO 생성
		LectureOneResponse lectureOneResponse = new LectureOneResponse();
		lectureOneResponse.setUserId(user.getUserId());
		lectureOneResponse.setCommonId(common.getCommonId());

		// 더미 프로젝트 강의 생성 및 권한 생성
		lectureRepository.insertLecture(lectureOneResponse);

		return lectureOneResponse;
	}

	@Override
	public void updateFoundationLecture(LectureRequest lecture) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("프로젝트를 수정할 권한이 없습니다");
		}
		lectureRepository.updateFoundationLecture(lecture);
		lectureRepository.insertTags(lecture.getCommonId(), lecture.getTags());
	}

	@Override
	public void updateContentLecture(LectureRequest lecture) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("프로젝트를 수정할 권한이 없습니다");
		}
		lectureRepository.updateContentLecture(lecture);
	}

	@Override // 대표 프로젝트 리스트 가져오기
	public List<LecturesResponse> getLectures(int startPage, int type) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		startPage = (startPage - 1) * 20;
		return lectureRepository.selectLectures(user.getUserId(), startPage, type);
	}

	@Override
	public List<TagResponse> getTags() throws Exception {
		return lectureRepository.selectTags();
	}

	@Override // 대표 프로젝트 상세 정보
	public LectureOneResponse getLectureBylectureId(long lectureId) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		lectureRepository.updateLectureViewCount(lectureId);
		return lectureRepository.selectLectureByLectureId(lectureId, user.getUserId());
	}

	@Override
	public void updateLikeLectureByUserId(long lectureId) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		LikeDTO dto = lectureRepository.checkLikeLectureByUserId(user.getUserId(), lectureId);
		if (dto.getLikeCount() > 0) {
			// 좋아요를 누른 적이 있다면
			String likeFlag = dto.getLikeFlag().equals("Y") ? "N" : "Y";
			lectureRepository.updateLikeLectureByUserId(user.getUserId(), lectureId, likeFlag);
		} else {
			// 좋아요를 누른 적이 한번도 없다면
			lectureRepository.insertLikeLectureByUserId(user.getUserId(), lectureId);
		}
	}

	@Override
	public void updateLikeHistoryByUserId(HistoryLikeRequest request) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		LikeDTO dto = lectureRepository.checkLikeHistoryByUserId(user.getUserId(), request.getLectureId(),
				request.getSubId(), request.getSubHisId());
		if (dto.getLikeCount() > 0) {
			// 좋아요를 누른 적이 있다면
			String likeFlag = dto.getLikeFlag().equals("Y") ? "N" : "Y";
			lectureRepository.updateLikeHistoryByUserId(user.getUserId(), request.getLectureId(), request.getSubId(),
					request.getSubHisId(), likeFlag);
		} else {
			// 좋아요를 누른 적이 한번도 없다면
			lectureRepository.insertLikeHistoryByUserId(user.getUserId(), request.getLectureId(), request.getSubId(),
					request.getSubHisId());
		}
	}

	@Override
	public void insertAuthLecture(long lectureId, String role) throws Exception {
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		lectureRepository.insertAuthLecture(lectureId, user.getUserId(), role);
	}

	@Override
	public List<LectureRoleUsersResponse> selectRoleUsersByLectureId(long lectureId) throws Exception {
		return lectureRepository.selectRoleUsersByLectureId(lectureId);
	}

	// 소강의 생성 및 변경된 개수 반환
	@Override
	public int createSubLectures(List<LectureSubsRequest> lectures) throws Exception {
		int count = 0;
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("소강의를 생성할 권한이 없습니다");
		}
		for (LectureSubsRequest lecture : lectures) {
			lecture.setUserId(user.getUserId());
			if (lecture.getSubId() == 0) {
				// 아직 db에 있지 않은 테이블이라 간주 하며 INSERT dao를 호출

				// common id 생성 및 Common id 받아오기
				Common common = new Common();
				lectureRepository.insertCommonId(common);

				lecture.setCommonId(common.getCommonId());
				lectureRepository.insertSubLecture(lecture);
			} else {
				// db에는 존재하지만 order를 변경 해야해 UPDATE dao를 호출
				lectureRepository.updateSubLecture(lecture);
			}
			lectureRepository.insertTags(lecture.getCommonId(), lecture.getTags());
			count++;
		}
		return count;
	}

	@Override
	public LectureSubOneResponse getOneSubLecture(long lectureId, int order) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		LectureSubsRequest request = new LectureSubsRequest();
		request.setLectureId(lectureId);
		request.setOrder(order);
		request.setUserId(user.getUserId());
		return lectureRepository.selectOneSubLecture(request);
	}

	@Override
	public List<LectureSubIndexResponse> getSubLectureIndex(long lectureId) throws Exception {
		return lectureRepository.selectSubLectureIndex(lectureId);
	}

	@Override
	public void registrySubHistory(LectureSubHistoryRequest lecture) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("요청할 권한이 없습니다. 로그인이 필요합니다.");
		}
		lecture.setUserId(user.getUserId());
		lectureRepository.insertSubHistory(lecture);
	}

	@Override
	public void uploadNoticeAuth(LectureSubHistoryRequest lecture) throws Exception {
		lectureRepository.uploadNoticeAuth(lecture);
	}

	@Override
	public void updateLectureAuth(List<LectureAuthRequest> auths) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		for (LectureAuthRequest auth : auths) {
			if("Owner".equals(auth.getLectureRole())) { //Owner 권한 위임 시 lecture.user_id 변경
				lectureRepository.changeLectureOwner(auth);
			}
			
			if (auth.getAuthId() == 0) { // 서버에 등록되어있지 않은 사람
				lectureRepository.insertLectureAuth(auth);
			} else { // 서버에 등록되어있는 사람
				lectureRepository.updateLectureAuth(auth);
			}
		}
	}

	@Override
	public List<TheOhterSubLectureResponse> getTheOtherSubLectures(long subId) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return lectureRepository.selectTheOtherSubLectures(subId, user.getUserId());
	}

	@Override
	public void deleteLectureAuth(long authId) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("로그인이 필요합니다");
		}
		lectureRepository.deleteLectureAuth(authId);
	}

	@Override
	public LectureSubOneResponse getOneOtherSubLecture(LectureSubOtherRequest request) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return lectureRepository.selectOneOtherSubLecture(user.getUserId(), request.getLectureId(), request.getSubId(),
				request.getSubHisId());
	}

	@Override
	public List<RequestHistoryResponse> getRequestLecturesList(long lectureId, int startPage, String req,
			String acceptType) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("요청 리스트를 볼 권한이 없습니다");
		}
		startPage = (startPage - 1) * 20;
		return lectureRepository.selectRequestLecturesList(lectureId, startPage, req.split(","), acceptType);
	}

	@Override
	public void updateRequestLecture(long subId, long subHisId, String type, String lwType) throws Exception {
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("요청 리스트를 처리할 권한이 없습니다");
		}
		lectureRepository.updateRequestLecture(subId, subHisId, type, lwType);
	}

	@Override
	public void uploadNoticeUser(long subHisId) throws Exception {
		lectureRepository.uploadNoticeUser(subHisId);
	}

	@Override
	public List<LecturesResponse> myLikeLectureList(long startPage, long itemsperpage) throws Exception {

		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		startPage = (startPage - 1) * itemsperpage;
		return lectureRepository.myLikeLectureList(user.getUserId(), startPage, itemsperpage);
	}

	@Override
	public List<TheOhterSubLectureResponse> myLikeVideoList(long startPage, long itemsperpage) throws Exception {

		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		startPage = (startPage - 1) * itemsperpage;
		return lectureRepository.myLikeVideoList(user.getUserId(), startPage, itemsperpage);
	}

	@Override
	public List<LecturesResponse> myMngLectureList(long startPage, long itemsperpage) throws Exception {

		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		startPage = (startPage - 1) * itemsperpage;
		return lectureRepository.myMngLectureList(user.getUserId(), startPage, itemsperpage);
	}

	@Override
	public List<ChangeHistoryResponse> getChangeHistoryList(long lectureId) throws Exception {
		return lectureRepository.selectChangeHistoryList(lectureId);
	}

	@Override
	public String checkUserManageAuth(long lectureId) throws Exception {
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return lectureRepository.checkUserManageAuth(user.getUserId(), lectureId);
	}
	
	@Override
	public String checkHasSubHistory(long lectureId) throws Exception {
//		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return lectureRepository.checkHasSubHistory(lectureId);
	}

	@Override
	public void deleteLecture(long lectureId) throws Exception {
		// 사용자 id 가져오기
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		lectureRepository.deleteLecture(user.getUserId(), lectureId);
	}

	@Override
	public List<RequestHistoryResponse> myReqList(long startPage, long itemsperpage) throws Exception {

		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		startPage = (startPage - 1) * itemsperpage;
		return lectureRepository.myReqList(user.getUserId(), startPage, itemsperpage);
	};
	

	// 최근 좋아요 가장 많이 받은 프로젝트 가져오기
	@Override
	public List<LecturesResponse> bestLectureList() throws Exception {
		
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return lectureRepository.bestLectureList(user.getUserId());
	}

	// 최근 좋아요 가장 많이 받은 동영상 가져오기
	@Override
	public List<TheOhterSubLectureResponse> bestVideoList() throws Exception {
		
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		return lectureRepository.bestVideoList(user.getUserId());
	}
		
}
