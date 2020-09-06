package com.ssafy.devit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.model.request.UserProfileUpdateReqeust;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.UserAuthDetailService;
import com.ssafy.devit.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping()
	@ApiOperation(value = "회원의 모든 정보를 가져옴")
	public ResponseEntity<CommonResponse> getUsers() {
		log.info(">> Load : getUsers <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			result.result = userService.getUsers();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUsers <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "단일 회원의 정보를 가져옴")
	@GetMapping("/user")
	public ResponseEntity<CommonResponse> getUserByUserId() {
		log.info(">> Load : getUserByUserId <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = userService.getUserByUserId();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUserByUserId <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/{lectureid}")
	@ApiOperation(value = "입력으로 준 닉네임이 포함 되어있는 회원 정보를 가져옴")
	public ResponseEntity<CommonResponse> getUsersByLikeNickname(@PathVariable("lectureid") long lectureId,
			@RequestParam("search") String search) {
		log.info(">> Load : GetUsersByLikeNickname <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = userService.getUsersByLikeSearchText(search, lectureId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUsersByLikeNickname <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping()
	@ApiOperation(value = "회원정보 수정")
	public ResponseEntity<CommonResponse> modifyUserInfo(@RequestBody UserProfileUpdateReqeust request) {
		log.info(">> Load : GetUsersByLikeNickname <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			
			// 사용자 id 가져오기
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			request.setUserId(user.getUserId());
			
			if("nickname".equals(request.getModifyType())) {
				//닉네임 중복검사
				if(userService.getUserByNickname(request.getNickname()) != null) { //사용 불가능
					
					result.msg = "duplicate";
					result.result = "이미 존재하는 닉네임입니다.";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}else {
					userService.modifyUserInfo(request);
					result.msg = "success";
					result.result = "성공적으로 변경 되었습니다";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}
				
			}else if("profile".equals(request.getModifyType())) {
				
				userService.modifyUserInfo(request);
				result.msg = "success";
				result.result = "성공적으로 변경 되었습니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				
			}else if("password".equals(request.getModifyType())) {
				
				if(!passwordEncoder.matches(request.getPassword(), userService.getUserPasswordByUserId(user.getUserId())) ) { //기존 비밀번호 불일치

					result.msg = "different";
					result.result = "기존 비밀번호가 일치하지 않습니다.";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}else if(passwordEncoder.matches(request.getNewPassword(), userService.getUserPasswordByUserId(user.getUserId()) )){ //기존 비밀번호와 새 비밀번호가 일치함.
					result.msg = "same";
					result.result = "기존 비밀번호와 다른 새 비밀번호를 입력해주세요.";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}else {

					request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
					userService.modifyUserInfo(request);
					result.msg = "success";
					result.result = "성공적으로 변경 되었습니다";
					response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
				}
				
			}
			
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUsersByLikeNickname <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
	
	@GetMapping("/user/{nickname}")
	@ApiOperation(value = "닉네임 중복 체크")
	public ResponseEntity<CommonResponse> checkForNickName(@PathVariable String nickname) {
		log.info(">> Load : checkForNickName <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;

		try {
			if (userService.getUserByNickname(nickname) == null) {
				result.msg = "success";
				result.result = "사용 가능한 닉네임 입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
			} else {
				result.msg = "duplicate";
				result.result = "사용 불가능한 닉네임 입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.debug(">> Error : checkForNickName <<");
			log.debug(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@GetMapping("/email/{email}")
	@ApiOperation(value = "이메일 중복 체크")
	public ResponseEntity<CommonResponse> checkForEmail(@PathVariable String email) {
		log.info(">> Load : checkForEmail <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;

		try {
			if (userService.getUserByEmail(email) == null) {
				result.msg = "success";
				result.result = "가입 가능한 이메일입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
			} else {
				result.msg = "duplicate";
				result.result = "이미 가입된 계정입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.debug(">> Error : checkForNickName <<");
			log.debug(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@GetMapping("/user/isadmin")
	@ApiOperation(value = "유저 admin 여부")
	public ResponseEntity<CommonResponse> getUserIsAdmin() {
		log.info(">> Load : getUserIsAdmin <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		
		UserAuthDetails user = null;
		try {
			user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = new UserAuthDetails();
			user.setUserId(0);
		}
		
		try {
			result.result = userService.getUserIsAdmin(user.getUserId());
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUserIsAdmin <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/withdraw")
	@ApiOperation(value = "회원 탈퇴")
	public ResponseEntity<CommonResponse> userWithdraw() {
		log.info(">> Load : userWithdraw <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			// 사용자 id 가져오기
			UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userService.userWithdraw(user.getUserId());
			
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : userWithdraw <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
}
