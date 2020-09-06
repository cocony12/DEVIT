package com.ssafy.devit.service;

import com.ssafy.devit.model.request.SignUpRequest;

public interface MailService { // to MailServiceImpl
	
	public int checkEmailConfirm(String email) throws Exception;	
	
	public int getUserCode(String email) throws Exception;
	
	public void setUserCodeDefault(String email) throws Exception;
	
	public void sendConfirmEmail(SignUpRequest request) throws Exception;
	
	public void sendPasswordFindConfirmEmail(String email) throws Exception;
	
	public void updatePasswordRandom(String email) throws Exception;		
	
}
