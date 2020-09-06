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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.model.reply.Reply;
import com.ssafy.devit.model.request.ReplyUpdateRequest;
import com.ssafy.devit.model.request.ReplyUploadRequest;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.ReplyService;
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
public class ReplyController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Autowired
	ReplyService replyService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/reply")
	@ApiOperation(value = "댓글 등록")
	public ResponseEntity<CommonResponse> upload(@RequestBody ReplyUploadRequest request) throws Exception {
		log.info(">> Reply UpLoad <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		try {
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Reply reply = null;
			reply = new Reply(request.getBoardId(), user.getUserId(), request.getReplyContent(),
					request.getParentReplyId());
			replyService.upload(reply);
			// bid에 해당하는 게시글을 조회한다.
			log.info("boardId :" + reply.getBoardReplyId());
			//// notice insert!!
			replyService.uploadNotice(reply.getBoardReplyId());
			////
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> upload <<");
			result.msg = "fail";
			result.result = "권한 오류";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@GetMapping("reply/{boardid}")
	@ApiOperation(value = "댓글 조회")
	public ResponseEntity<CommonResponse> info(@PathVariable("boardid") final long boardId) throws Exception {
		log.info(">> replyinfo <<");
		// PathVariable로 bid를 받아서 해당 게시글을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.msg = "success";
			result.result = replyService.info(boardId);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> info <<");
			result.msg = "not found";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		} // bid에 해당하는 게시글을 조회한다.
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@DeleteMapping("reply/{reply_id}")
	@ApiOperation(value = "댓글 삭제")
	public ResponseEntity<CommonResponse> delete(@PathVariable final long reply_id) throws Exception {
		log.info(">> reply delete <<");
		// PathVariable로 bid를 받아서 해당 게시글을 삭제한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.msg = "success";
			replyService.delete(reply_id);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> delete <<");
			result.msg = "not found";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/reply")
	@ApiOperation(value = "댓글 수정")
	public ResponseEntity<CommonResponse> update(@RequestBody ReplyUpdateRequest request) throws Exception {
		log.info(">> replyUpdate <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		try {
			replyService.update(request);
			result.msg = "Success";
			result.result = "성공적으로 수정 되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			// Service, DAO 에서 에러 발생 하면 나오는 곳
			log.info(">> update <<");
			result.msg = "error";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
