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
import com.ssafy.devit.model.lecture.BoardResponse;
import com.ssafy.devit.model.notice.Notice;
import com.ssafy.devit.model.notice.NoticeDetailResponse;
import com.ssafy.devit.model.request.BoardUpdateRequest;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.NoticeService;
import com.ssafy.devit.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommonResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = CommonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = CommonResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = CommonResponse.class) })

@RestController
@RequestMapping("/api/v1")
public class NoticeController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NoticeService noticeService;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/notice/getInfoCnt")
	@ApiOperation(value = "알림 개수 조회")
	public ResponseEntity<CommonResponse> getNoticeInfoCnt() throws Exception {
		log.info(">> notice Count info <<");
		// PathVariable로 bid를 받아서 해당 게시글을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		
		try {			
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			result.msg = "success";
			result.result = noticeService.getNoticeCnt(user.getUserId());
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> get Notice count <<");
			result.msg = "fail to getNotice count";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} 
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/notice/getList")
	@ApiOperation(value = "알림 조회")
	public ResponseEntity<CommonResponse> getNoticeList() throws Exception {
		log.info(">> notice info <<");
		// PathVariable로 bid를 받아서 해당 게시글을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		
		try {			
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			result.msg = "success";
			result.result = noticeService.getNotice(user.getUserId());
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> setNoticeRead <<");
//			log.info(e.getMessage().toString());
			result.msg = "fail to getNotice";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} 
		return response;
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/notice/setRead")
	@ApiOperation(value = "알림 읽음 처리")
	public ResponseEntity<CommonResponse> setNoticeRead(@RequestParam("notice_id") long noticeId) throws Exception{
		log.info(">> notice Read Yn Update <<");

		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		
		try {
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			noticeService.setNoticeRead(noticeId);
			result.msg = "success";			
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> setNoticeRead <<");
			result.msg = "fail to setNotice";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} 
		return response;
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/notice/setAlert")
	@ApiOperation(value = "알림 표시 처리")
	public ResponseEntity<CommonResponse> setNoticeAlert(@RequestParam("notice_id") long noticeId) throws Exception{
		log.info(">> notice yn Update <<");

		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		
		try {
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			noticeService.setNoticeAlert(noticeId);
			result.msg = "success";			
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> setNoticeRead <<");
			result.msg = "fail to setNotice";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} 
		return response;
	}

	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/notice/{noticeid}")
	@ApiOperation(value = "알림 상세 조회")
	public ResponseEntity<CommonResponse> getNoticeDetailInfo(@PathVariable final long noticeid) throws Exception {
		log.info(">> notice detail info <<");
		
		// PathVariable로 noticeid를 받아서 해당 알림의 상세 내용을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		
		try {			
			
			result.msg = "success";
			result.result = noticeService.getNoticeDetailInfo(noticeid);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> setNoticeRead <<");
			result.msg = "fail to getNotice";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} 
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "알람 전체 삭제")
	@DeleteMapping("/notice/all")
	public ResponseEntity<CommonResponse> removeNoticeAll() {
		log.info(">> Load : removeNoticeAll <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			noticeService.removeNoticeAll();
			result.msg = "success";
			result.result = "성공적으로 삭제되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "알림을 삭제할 권한이 없습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : removeNoticeAll <<");
//			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "알람 개별 삭제")
	@DeleteMapping("/notice/each")
	public ResponseEntity<CommonResponse> removeNoticeEach(@RequestParam("notice_id") long noticeId) {
		log.info(">> Load : removeNotice <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			noticeService.removeNoticeEach(noticeId);
			result.msg = "success";
			result.result = "성공적으로 삭제되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "알림을 삭제할 권한이 없습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : removeNotice <<");
//			log.info(e.getMessage().toString());
		}
		return response;
	}


	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "읽은 알람 삭제")
	@DeleteMapping("/notice/read")
	public ResponseEntity<CommonResponse> removeNoticeRead() {
		log.info(">> Load : removeNoticeRead <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			noticeService.removeNoticeRead();
			result.msg = "success";
			result.result = "성공적으로 삭제되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "알림을 삭제할 권한이 없습니다";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			log.info(">> Error : removeNoticeRead <<");
//			log.info(e.getMessage().toString());
		}
		return response;
	}
}
