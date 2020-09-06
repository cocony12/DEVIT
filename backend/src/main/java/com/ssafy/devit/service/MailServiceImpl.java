package com.ssafy.devit.service;

import java.util.Base64;
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.request.SignUpRequest;
import com.ssafy.devit.repository.MailRepository;

@Service
public class MailServiceImpl implements MailService { // to MailRepository

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	MailRepository mailRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override	
	// user테이블의 email_confirm이 'Y'면 1 아니면 0 리턴
	public int checkEmailConfirm(String email) throws Exception{		
		return mailRepository.checkEmailConfirm(email);
	}
			
	@Override	
	// user테이블의 code 리턴
	public int getUserCode(String email) throws Exception{		
		return mailRepository.getUserCode(email);
	}
	
	@Override	
	// user테이블의 code 리턴
	public void setUserCodeDefault(String email) throws Exception{		
		mailRepository.setUserCodeDefault(email);
	}
	
	// 링크 클릭하면 회원가입이 완료되는 메일
	@Override
	public void sendConfirmEmail(SignUpRequest request) throws Exception{
		String encodedEmail = Base64.getEncoder().encodeToString(request.getEmail().getBytes());
		String encodedPassword = Base64.getEncoder().encodeToString(request.getPassword().getBytes());
		String encodedNickname = Base64.getEncoder().encodeToString(request.getNickname().getBytes());
		
		String email_from = "i3a101.devit.io@gmail.com";
		String subject = "[DEVIT] " + request.getNickname() + "님의 회원가입 인증 메일입니다.";		
		String text = "DEVIT에 가입해주셔서 감사합니다!<br>아래의 링크를 클릭하면 회원가입이 완료됩니다.<br>"; 
		text += "<h3><a href='http://i3a101.p.ssafy.io:8080/api/v1/mail/confirm/auth?e=" 
		+ encodedEmail + "&p=" + encodedPassword + "&n=" + encodedNickname + "'> 회원가입 </a></h3>";
		
		sendMail(email_from, request.getEmail(), subject, text);
	}
	
	// *
	public void sendPasswordFindConfirmEmail(String email_to) throws Exception{
		int code = getRandomCode();
		mailRepository.updateCode(email_to, code);
		
		String encodedEmail = Base64.getEncoder().encodeToString(email_to.getBytes());
		String encodedCode = Base64.getEncoder().encodeToString(Integer.toString(code).getBytes());
				
		String email_from = "i3a101.devit.io@gmail.com";
		String subject = "[DEVIT] 비밀번호 변경 요청 인증 메일입니다.";		
		String text = "<h1>비밀번호 변경 요청 인증 메일</h1><br><hr><br>";
		text += "DEVIT 비밀번호 찾기<br />아래 경로를 통해 비밀번호 변경을 진행하시기 바랍니다..<br />"
				+ "링크를 클릭하면 새로운 임시 비밀번호가 메일로 발송됩니다.<br /><br />";
		text += "<h3><a href='http://i3a101.p.ssafy.io:8080/api/v1/mail/password?e=" + encodedEmail + "&c=" + encodedCode + "'> 비밀번호 변경하기 </a></h3>";
		
		sendMail(email_from, email_to, subject, text);
	}
			
	// *
	public void updatePasswordRandom(String email_to) throws Exception{
		String password = getRandomString(12); // 12자리 랜덤 비밀번호	(70^12)
		String password_encoded = passwordEncoder.encode(password);
		
		mailRepository.updatePasswordRandom(email_to, password_encoded);
		
		String email_from = "i3a101.devit.io@gmail.com";
		String subject = "[DEVIT] 비밀번호가 변경되었습니다.";		
		String text = "해당 계정의 임시 비밀번호가 발급되었습니다.<br />비밀번호 변경을 통해 개인정보를 보호하세요.<br />";
		text += "password : " + password;
				
		sendMail(email_from, email_to, subject, text);
	}
	
	public String getRandomString(int length){
		String Candidate="0123456789!@#$%^&*abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String new_password = "";
		Random ran = new Random();
		
		for(int i=0; i < length; i++)
			new_password+= Candidate.charAt(ran.nextInt(Candidate.length()));
		
		return new_password;
	}
	
	public int getRandomCode() {
		return new Random().nextInt(90000) + 10000;
	}
	
	// 메일 전송
		public void sendMail(String email_from, String email_to, String subject,  String text) throws Exception{		
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setSubject(subject);
			message.setFrom(new InternetAddress(email_from));
			message.addRecipient(RecipientType.TO, new InternetAddress(email_to));
			// message.setText(text, "UTF-8", "html");
			message.setContent(text, "text/html;charset=euc-kr");
			
			javaMailSender.send(message);		
		}
}
