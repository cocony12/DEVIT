package com.ssafy.devit.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.devit.config.security.JwtTokenProvider;
import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.model.request.LoginRequest;
import com.ssafy.devit.model.request.SignUpRequest;
import com.ssafy.devit.model.user.LoginResponse;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.UserAuthDetailService;
import com.ssafy.devit.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommonResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = CommonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = CommonResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = CommonResponse.class) })
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired // 회원 관리
	UserAuthDetailService userAuthDetailService;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = LoginResponse.class) })
	@PostMapping("/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest request) {
		log.info(">> Load : login <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		// 사용자가 입력한 이메일로 DB검색
		try {
			UserAuthDetails user = userAuthDetailService.getUserByEmail(request.getEmail());
			// user 정보가 조회 됐다면
			if (user != null) {
				// 비밀번호 매칭
				if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
					result.msg = "fail";
					result.result = "비밀번호가 일치하지 않습니다.";
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else if("N".equals(userAuthDetailService.getUserActiveByEmail(request.getEmail()))){
					result.msg = "fail";
					result.result = "이미 탈퇴한 계정입니다.";
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					LoginResponse loginResponse = new LoginResponse();
					loginResponse.setToken(jwtTokenProvider.createToken(String.valueOf(user.getUserId()),
							userAuthDetailService.getRoles(user.getUserId())));
					loginResponse.setUserId(user.getUserId());
					loginResponse.setNickname(user.getNickname());
					loginResponse.setProfile(user.getProfile());
					result.msg = "success";
					result.result = loginResponse;
//					result.result = jwtTokenProvider.createToken(String.valueOf(user.getUserId()),
//							userAuthDetailService.getRoles(user.getUserId()));
					response = new ResponseEntity<>(result, HttpStatus.OK);
				}
			}else {
				result.msg = "fail";
				result.result = "이메일 또는 비밀번호가 틀렸습니다.";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info(">> Error : login <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PostMapping("/signup")
	@ApiOperation(value = "가입하기")
	public ResponseEntity<CommonResponse> signUp(@RequestBody @Valid SignUpRequest request) {
		log.info(">> Load : signUp <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;

		// DB에 user Email이 있다면 그에 해당하는 user 정보를 / 그렇지 않을 경우 null 반환
		try {
			// email, nickname 중복 체크
			if (checkUser(request.getEmail(), request.getNickname())) {
				UserAuthDetails user = new UserAuthDetails(request.getEmail(), request.getNickname(),
						passwordEncoder.encode(request.getPassword()));
				userAuthDetailService.signUp(user);
				result.msg = "success";
				result.result = "가입이 완료 되었습니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
			} else {
				// if 중복되는 계정이 있다면 중복
				result.msg = "duplicate";
				result.result = "중복 계정입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info(">> Error : signUp <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	public boolean checkUser(String email, String nickname) throws Exception {
		return userAuthDetailService.checkValidEmailOrNickname(email, nickname) == 0;
	}
}