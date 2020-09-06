package com.ssafy.devit.controller;

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
import com.ssafy.devit.model.lecture.BoardResponse;
import com.ssafy.devit.model.request.BoardRequest;
import com.ssafy.devit.model.request.BoardUpdateRequest;
import com.ssafy.devit.model.request.BoardUploadRequest;
import com.ssafy.devit.model.request.BoardWithLectureRequest;
import com.ssafy.devit.model.request.LectureRequest;
import com.ssafy.devit.model.request.UserProfileUpdateReqeust;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.model.user.UserResponse;
import com.ssafy.devit.service.BoardService;
import com.ssafy.devit.service.LectureService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommonResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = CommonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = CommonResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = CommonResponse.class) })

@RestController
@RequestMapping("/api/v1")
public class MyActivityController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	LectureService lectureService;

	@Autowired
	BoardService boardService;
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myMngLecture")
	@ApiOperation(value = "관리중인 프로젝트 목록 조회")
	public ResponseEntity<CommonResponse> myMngLectureList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my managing lecture list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = lectureService.myMngLectureList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myMngLectureList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myboard")
	@ApiOperation(value = "내가 쓴 게시글 목록 조회")
	public ResponseEntity<CommonResponse> myBoardList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my board list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = boardService.myBoardList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myBoardList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myreply")
	@ApiOperation(value = "내가 쓴 댓글 목록 조회")
	public ResponseEntity<CommonResponse> myReplyList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my reply list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = boardService.myReplyList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myReplyList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myLikeLecture")
	@ApiOperation(value = "좋아요한 프로젝트 목록 조회")
	public ResponseEntity<CommonResponse> myLikeLectureList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my like lecture list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = lectureService.myLikeLectureList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myLikeLectureList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myLikeVideo")
	@ApiOperation(value = "좋아요한 동영상 목록 조회")
	public ResponseEntity<CommonResponse> myLikeVideoList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my like video list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = lectureService.myLikeVideoList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myLikeVideoList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "프로젝트 삭제")
	@PutMapping("deleteLecture")
	public ResponseEntity<CommonResponse> deleteLecture(@RequestBody LectureRequest request) {
		log.info(">> Load : deleteLecture <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {

			//프로젝트 Owner 여부 가져오기
			String isOwner = lectureService.checkUserManageAuth(request.getLectureId());
			String hasSubHistory = lectureService.checkHasSubHistory(request.getLectureId());
			
			if("N".equals(isOwner)) {

				result.msg = "noauth";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				
			}else if("Y".equals(hasSubHistory)){

				result.msg = "hashistory";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}else {

				lectureService.deleteLecture(request.getLectureId());
				result.msg = "success";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.info(">> Error : deleteLecture <<");
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("myReqList")
	@ApiOperation(value = "요청 목록 조회")
	public ResponseEntity<CommonResponse> myReqList(@RequestParam("page") int startPage, @RequestParam("itemsperpage") int itemsperpage) throws Exception {
		
		log.info(">> my Request list info <<");
		
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			
			result.msg = "success";
			result.result = lectureService.myReqList(startPage, itemsperpage);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> Error : myReqList <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
}
