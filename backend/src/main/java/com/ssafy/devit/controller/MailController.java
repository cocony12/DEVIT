package com.ssafy.devit.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.model.request.BoardWithLectureRequest;
import com.ssafy.devit.model.request.EmailRequest;
import com.ssafy.devit.model.request.SignUpRequest;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.service.MailService;
import com.ssafy.devit.service.UserAuthDetailService;
import com.ssafy.devit.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommonResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = CommonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = CommonResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = CommonResponse.class) })
@Controller
@RequestMapping("/api/v1/mail")
public class MailController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MailService mailService;
	
	@Autowired // 회원 관리
	UserAuthDetailService userAuthDetailService;

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Async
	@GetMapping("/confirmCheck")
	// 이메일인증을 마친 계정인지 확인
	public ResponseEntity<CommonResponse> confirmCheck(@RequestParam("email") String email) throws Exception {
		log.info(">> Load : confirmCheck <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			int check = mailService.checkEmailConfirm(email);
			if (check > 0) {
				result.msg = "success: already confirmed";
				result.result = "이미 인증된 계정입니다.";
				response = new ResponseEntity<>(result, HttpStatus.OK);
				return response;
			} else {
				result.msg = "success: need to confirm";
				result.result = "인증이 필요한 계정입니다..";
				response = new ResponseEntity<>(result, HttpStatus.OK);
				return response;
			}
		} catch (Exception e) {
			log.info(">> Error : emailConfirmMail <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = "계정 인증여부 확인 실패.";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return response;
	}	

	@PostMapping("/confirm/auth")
	@ApiOperation(value = "회원가입을 위한 이메일 인증")
	public ResponseEntity<CommonResponse> confirmAuthUser(@RequestBody @Valid SignUpRequest request) {
		log.info(">> Load : confirmAuthUser <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		try {
			mailService.sendConfirmEmail(request);
			result.msg = "success";
			result.result = "성공적으로 이메일 확인 메일이 보내졌습니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.info(">> Error : emailConfirmMail <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = "계정 인증 메일 발송 실패.";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@GetMapping("/confirm/auth")
	@ApiOperation(value = "회원가입 인증 메일에 보내진 링크를 클릭하면")
	public String signUp(@RequestParam("e") String encodedEmail, @RequestParam("p") String encodedPassword,
			@RequestParam("n") String encodedNickname) {
		log.info(">> Load : confirmAuthUser <<");
		final CommonResponse result = new CommonResponse();
		//ResponseEntity<CommonResponse> response = null;		

		try {
			byte[] emailB64 = Base64.getDecoder().decode(encodedEmail);
			byte[] passwordB64 = Base64.getDecoder().decode(encodedPassword);
			byte[] nicknameB64= Base64.getDecoder().decode(encodedNickname);
			
			String email = new String(emailB64);		
			String password = new String(passwordB64);
			String nickname = new String(nicknameB64);
			
			UserAuthDetails user = new UserAuthDetails(email, nickname,
					passwordEncoder.encode(password));
			
			userAuthDetailService.signUp(user);
			result.msg = "success";
			result.result = "가입이 완료 되었습니다";
			//response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:http://i3a101.p.ssafy.io/";
	}
	

	@PostMapping("/password")
	@ApiOperation(value = "비밀번호 변경 확인 메일을 보낸다")
	// 비밀번호 찾기 요청으로써, 실행되면 비밀번호 찾기 요청을 본인이 한게 맞는지 확인하는 메일이 보내진다.
	public ResponseEntity<CommonResponse> passwordConfirmMail(@RequestBody EmailRequest request) {
		log.info(">> Load : passeordConfirmMail <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			String email = request.getEmail();
			if (userService.getUserByEmail(email) == null) {
				result.msg = "notexist";
				result.result = "가입되지 않은 이메일입니다";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
			}else {
				
				mailService.sendPasswordFindConfirmEmail(email);
				result.msg = "success";
				result.result = "성공적으로 비밀번호 변경 확인 메일이 보내졌습니다.";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.info(">> Error : passwordConfirmMail <<");
//			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = "비밀번호 변경 확인 메일이 발송 실패.";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@GetMapping("/password")
	@ApiOperation(value = "링크를 클릭하면 비밀번호가 변경되고 메일이 새로 발송된다.")
	// 링크를 클릭하면 임의의 비밀번호로 변경이 되고 그 비밀번호가 메일로 보내진다.
	public String passwordChangeRandom(@RequestParam("e") String encodedEmail, @RequestParam("c") String encodedCode) {
		log.info(">> Load : passeordChange <<");		
		final CommonResponse result = new CommonResponse();

		try {			
			byte[] emailB64 = Base64.getDecoder().decode(encodedEmail);
			byte[] codeB64 = Base64.getDecoder().decode(encodedCode);			
			
			String email = new String(emailB64);		
			String code = new String(codeB64);			
			
			if(Integer.parseInt(code) == mailService.getUserCode(email)) {
				mailService.updatePasswordRandom(email);
				mailService.setUserCodeDefault(email);
				
				result.msg = "success";
				result.result = "성공적으로 비밀번호가 변경 되었습니다.";				
			}else {								
				result.msg = "success";
				result.result = "code matching fail.";				
			}
		} catch (Exception e) {
			log.info(">> Error : passwordChange <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = "비밀번호 변경 실패.";			
		}
		
		return "redirect:http://i3a101.p.ssafy.io/changedpw";
	}

	


}
