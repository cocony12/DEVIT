package com.ssafy.devit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.model.lecture.ChangeHistoryResponse;
import com.ssafy.devit.model.lecture.LectureOneResponse;
import com.ssafy.devit.model.lecture.LectureSubOneResponse;
import com.ssafy.devit.model.lecture.RequestHistoryResponse;
import com.ssafy.devit.model.request.HistoryLikeRequest;
import com.ssafy.devit.model.request.LectureAuthRequest;
import com.ssafy.devit.model.request.LectureRequest;
import com.ssafy.devit.model.request.LectureSubHistoryRequest;
import com.ssafy.devit.model.request.LectureSubOtherRequest;
import com.ssafy.devit.model.request.LectureSubsRequest;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.LectureService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/lectures")
public class LectureController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LectureService lectureService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대표  프로젝트 생성")
	@PostMapping
	public ResponseEntity<CommonResponse> createLecture() {
		log.info(">> loadCreateLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			LectureOneResponse lectureOneResponse = lectureService.createDummyLecture();
			result.result = lectureOneResponse;
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : createLecture <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiOperation(value = "대표 프로젝트 목록 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getLectures(@RequestParam("page") int startPage,
			@RequestParam("type") int type) {
		log.info(">> loadGetLectures <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.result = lectureService.getLectures(startPage, type);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : getLectures <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiOperation(value = "서비스에 등록된 Tag들을 상위 20개 가져오기")
	@GetMapping("/tags")
	public ResponseEntity<CommonResponse> getTags() {
		log.info(">> Load : getTags <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.result = lectureService.getTags();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : getTags <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대표 프로젝트 상세 정보 가져오기")
	@GetMapping("/{lectureId}")
	public ResponseEntity<CommonResponse> getLecture(@PathVariable long lectureId) {
		log.info(">> Load : getLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.result = lectureService.getLectureBylectureId(lectureId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : getLecture <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대표 프로젝트 사용자 좋아요 처리")
	@PutMapping("/like")
	public ResponseEntity<CommonResponse> updateLectureLike(@RequestParam("lectureId") long lectureId) {
		log.info(">> Load : updateLectureLike <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			lectureService.updateLikeLectureByUserId(lectureId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : updateLectureLike <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "히스토리 사용자 좋아요 처리")
	@PutMapping("/like/history")
	public ResponseEntity<CommonResponse> updateHistoryLike(HistoryLikeRequest request) {
		log.info(">> Load : updateHistoryLike <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			lectureService.updateLikeHistoryByUserId(request);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : updateHistoryLike <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대표 프로젝트 기본 요소 수정")
	@PutMapping("/foundation")
	public ResponseEntity<CommonResponse> updateFoundationLecture(@RequestBody LectureRequest request) {
		log.info(">> Load : updateLectureLike <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			lectureService.updateFoundationLecture(request);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : updateLectureLike <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대표 프로젝트 내용 수정")
	@PutMapping("/content")
	public ResponseEntity<CommonResponse> updateContentLecture(@RequestBody LectureRequest request) {
		log.info(">> Load : updateContentLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			lectureService.updateContentLecture(request);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : updateContentLecture <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "프로젝트 공동 관리자 리스트 가져오기")
	@GetMapping("/auth/{lectureid}")
	public ResponseEntity<CommonResponse> selectRoleUsersByLectureId(@PathVariable("lectureid") long lectureId) {
		log.info(">> Load : selectRoleUsersByLectureId <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.result = lectureService.selectRoleUsersByLectureId(lectureId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : selectRoleUsersByLectureId <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "프로젝트 공동 관리자 수정")
	@PutMapping("/auth")
	public ResponseEntity<CommonResponse> updateLectureAuth(@RequestBody List<LectureAuthRequest> request) {
		log.info(">> Load : updateLectureAuth <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			lectureService.updateLectureAuth(request);
			result.msg = "success";
			result.result = "성공적으로 등록 되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : updateLectureAuth <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "프로젝트 공동 관리자 제거")
	@DeleteMapping("/auth/{authid}")
	public ResponseEntity<CommonResponse> removeLectureAuth(@PathVariable("authid") long authId) {
		log.info(">> Load : removeLectureAuth <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			lectureService.deleteLectureAuth(authId);
			result.msg = "success";
			result.result = "성공적으로 등록 되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "공동 관리자를 삭제할 권한이 없습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : removeLectureAuth <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "소강의 생성")
	@PostMapping("/sub")
	public ResponseEntity<CommonResponse> createSubLectures(@RequestBody List<LectureSubsRequest> requests) {
		log.info(">> Load : createSubLectures <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.result = lectureService.createSubLectures(requests) + "개 생성 및 수정 완료";
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : createSubLectures <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = LectureSubOneResponse.class) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "목차 상세정보 가져오기")
	@GetMapping("/sub/{lectureid}")
	public ResponseEntity<CommonResponse> getSubLecture(@PathVariable("lectureid") long lectureId,
			@RequestParam("order") int order) {
		log.info(">> Load : getSubLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = lectureService.getOneSubLecture(lectureId, order);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : getSubLecture <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiOperation(value = "목차 리스트 가져오기")
	@GetMapping("/subs/{lectureid}")
	public ResponseEntity<CommonResponse> getSubsLectureIndex(@PathVariable("lectureid") long lectureId) {
		log.info(">> Load : getSubsLectureIndex <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = lectureService.getSubLectureIndex(lectureId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : getSubsLectureIndex <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "강의, 위키 요청 리퀘스트")
	@PostMapping("/sub/history")
	public ResponseEntity<CommonResponse> registrySubHistory(@RequestBody LectureSubHistoryRequest request) {
		log.info(">> Load : registrySubHistory <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			lectureService.registrySubHistory(request);
			lectureService.uploadNoticeAuth(request);
			result.msg = "success";
			result.result = "성공적으로 등록 되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : registrySubHistory <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "같은종류 다른 강의 리스트 들고오기")
	@GetMapping("/sub/history/{subid}")
	public ResponseEntity<CommonResponse> getTheOtherSubLectures(@PathVariable("subid") long subId) {
		log.info(">> Load : getTheOtherSubLectures <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = lectureService.getTheOtherSubLectures(subId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getTheOtherSubLectures <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = LectureSubOneResponse.class) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "같은종류 다른 강의  상세정보 들고오기")
	@GetMapping("/sub/history")
	public ResponseEntity<CommonResponse> getTheOtherSubLecture(LectureSubOtherRequest request) {
		log.info(">> Load : getTheOtherSubLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = lectureService.getOneOtherSubLecture(request);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : getTheOtherSubLecture <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RequestHistoryResponse.class) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "요청 리스트 가져오기")
	@GetMapping("/historys")
	public ResponseEntity<CommonResponse> getRequestHistorys(@RequestParam("lectureId") long lectureId,
			@RequestParam("startPage") int startPage, @RequestParam("reqType") String reqType,
			@RequestParam("acceptType") String acceptType) {
		log.info(">> Load : getRequestHistorys <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = lectureService.getRequestLecturesList(lectureId, startPage, reqType, acceptType);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : getRequestHistory <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Void.class) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "요청 리스트 목록 처리")
	@PutMapping("/historys")
	public ResponseEntity<CommonResponse> updateRequestHistory(@RequestParam("type") String type,
			@RequestParam("reqType") String reqType, @RequestParam("subHisId") long subHisId,
			@RequestParam("subId") long subId) {
		log.info(">> Load : updateRequestHistory <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			lectureService.updateRequestLecture(subId, subHisId, type, reqType);
			// upload notice to request user
			lectureService.uploadNoticeUser(subHisId);
			result.msg = "success";
			result.result = "성공적으로 적용 되었습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "로그인 세션이 만료 되었거나 권한이 없습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : updateRequestHistory <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ChangeHistoryResponse.class) })
	@ApiOperation(value = "변경 사항 이력 가져오기")
	@GetMapping("/historys/{lectureId}")
	public ResponseEntity<CommonResponse> getChangeHistoryList(@PathVariable("lectureId") long lectureId) {
		log.info(">> Load : updateRequestHistory <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.msg = "success";
			result.result = lectureService.getChangeHistoryList(lectureId);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "변경 이력을 가져오는데 오류가 발생했습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : updateRequestHistory <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
}
