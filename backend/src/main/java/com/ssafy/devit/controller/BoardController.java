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
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.model.user.UserResponse;
import com.ssafy.devit.service.BoardService;
import com.ssafy.devit.service.UserService;

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
public class BoardController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;

	@Autowired
	BoardService boardService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/board")
	@ApiOperation(value = "게시물 등록")
	public ResponseEntity<CommonResponse> upload(@RequestBody BoardUploadRequest request) throws Exception{
		log.info(">> boardUpLoad <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		try {
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			BoardRequest board = null;
			board = new BoardRequest(user.getUserId(), request.getBoardTitle(), request.getBoardContent(), request.getBoardContentHtml(), request.getBoardType(), request.getBoardCount());
			
			String isAdmin = "Y";
			if(board.getBoardType() == 1) { //공지사항에 글 작성할 경우 권한 체크
				isAdmin = userService.getUserIsAdmin(user.getUserId());
			}
			if("N".equals(isAdmin)) {
				result.msg = "noauth";
				result.result = "글쓰기 권한이 없습니다.";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}else {
				
				boardService.upload(board);
				// bid에 해당하는 게시글을 조회한다.
				if(board.getBoardType() == 1) { // 공지사항 알림
					boardService.uploadNotice(board.getBoardId());
				}
				result.msg = "success";
				result.result = board.getBoardId();
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}
		

		} catch (Exception e) {
			result.msg = "fail";
			result.result = "권한 오류";
			log.info(">> boardUpLoad Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("board/{bid}")
	@ApiOperation(value = "게시글 상세 조회")
	public ResponseEntity<CommonResponse> info(@PathVariable final long bid) throws Exception {
		log.info(">> boardinfo <<");
		// PathVariable로 bid를 받아서 해당 게시글을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		BoardResponse boardResponse = null;
		try {
			
			

			boardResponse = boardService.info(bid);
			
			if(boardResponse != null) {
				result.msg = "success";
				result.result = boardResponse;
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			} else {
				result.msg = "not found";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "권한 오류";
			log.info(">> boardinfo Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} // bid에 해당하는 게시글을 조회한다.
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@DeleteMapping("board/{bid}")
	@ApiOperation(value = "게시글 삭제")
	public ResponseEntity<CommonResponse> delete(@PathVariable final long bid) throws Exception {
		log.info(">> board delete <<");
		// PathVariable로 bid를 받아서 해당 게시글을 삭제한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {

			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String isAdmin = "Y";
			if(boardService.getBoardTypeById(bid) == 1) { //공지사항에 글 작성할 경우 권한 체크
				isAdmin = userService.getUserIsAdmin(user.getUserId());
			}
			if("N".equals(isAdmin)) {
				result.msg = "noauth";
				result.result = "삭제 권한이 없습니다.";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}else {
				result.msg = "success";
				boardService.delete(bid);
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "오류";
			log.info(">> delete Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/board")
	@ApiOperation(value = "게시물 수정")
	public ResponseEntity<CommonResponse> update(@RequestBody BoardUpdateRequest request) throws Exception{
		log.info(">> boardUpdate <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		//Board board = null;
		BoardResponse boardResponse = null;
		try {
			boardResponse = boardService.info(request.getBoardId());
			
			if(boardResponse != null) {
				
				boardResponse.setBoardTitle(request.getBoardTitle());
				boardResponse.setBoardContent(request.getBoardContent());
				boardResponse.setBoardContentHtml(request.getBoardContentHtml());
				boardResponse.setBoardType(request.getBoardType());
				boardResponse.setBoardCount(request.getBoardCount());
				
				UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String isAdmin = "Y";
				if(boardResponse.getBoardType() == 1) { //공지사항에 글 작성할 경우 권한 체크
					isAdmin = userService.getUserIsAdmin(user.getUserId());
				}
				if("N".equals(isAdmin)) {
					result.msg = "noauth";
					result.result = "수정 권한이 없습니다.";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}else {
					boardService.update(boardResponse, request.getBoardId());
					result.msg = "Success";
					result.result = request.getBoardId();
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}
			} else {
				result.msg = "not found";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("board/list")
	@ApiOperation(value = "게시글 목록 조회")
	public ResponseEntity<CommonResponse> listinfo(@RequestParam("page") int startPage,
			@RequestParam("type") int type, @RequestParam("itemsperpage") int itemsperpage,
			@ApiParam(value = "검색조건", required = false)@RequestParam("searchselect") String searchselect, 
			@ApiParam(value = "검색할단어", required = false)@RequestParam("searchtxt") String searchtxt) throws Exception {
		log.info(">> board list info <<");
		// PathVariable로 type를 받아서 해당 게시글 목록을 조회한다.
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.msg = "success";
			result.result = boardService.listinfo(startPage, type, itemsperpage, searchselect, searchtxt);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "오류";
			log.info(">> listinfo Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/board/lecture")
	@ApiOperation(value = "강의 관련 질문 게시물 등록")
	public ResponseEntity<CommonResponse> createBoardWithLecture(@RequestBody BoardWithLectureRequest request) throws Exception{
		log.info(">> createBoardWithLecture <<");
		
		final CommonResponse result = new CommonResponse();
		
		ResponseEntity<CommonResponse> response = null;
		
		try {
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			BoardWithLectureRequest boardWithLectureRequest = null;
			
//			boardWithLectureRequest = new BoardWithLectureRequest(request.getLectureId(), request.getSubId(), user.getUserId(), request.getBoardTitle(), request.getBoardContent(), request.getBoardType(), request.getBoardCount());
			boardWithLectureRequest = new BoardWithLectureRequest(request.getLectureId(), request.getSubId(), user.getUserId(), request.getBoardTitle(), request.getBoardContent(), request.getBoardContentHtml());
			
			boardService.createBoardWithLecture(boardWithLectureRequest);
			 // bid에 해당하는 게시글을 조회한다.
			result.msg = "success";
			result.result = boardWithLectureRequest.getBoardId();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "권한 오류";
			log.info(">> createBoardWithLecture Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}	
	
	@GetMapping("board/lecture")
	@ApiOperation(value = "강의 관련 게시글 목록 조회")
	public ResponseEntity<CommonResponse> lectureQnaList(@RequestParam("lectureId") long lectureId, @RequestParam("subId") long subId) throws Exception {
		log.info(">> lecture qna list info <<");

		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			result.msg = "success";
			result.result = boardService.lectureQnaList(lectureId, subId);
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "에러";
			log.info(">> lectureQnaList Error <<");
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
}
