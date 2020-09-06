package com.ssafy.devit.repository;

import org.apache.ibatis.annotations.Param;

public interface MailRepository { // to Mail.xml
	
	public void updateEmailConfirm(@Param("email") String email) throws Exception;
	
	public void updateCode(@Param("email") String email, @Param("code") int code) throws Exception;
	
	public int confirmEmailCode(@Param("email") String email, @Param("code") int code) throws Exception;
	
	public int getUserCode(@Param("email") String email) throws Exception;
	
	public void setUserCodeDefault(@Param("email") String email) throws Exception;
	
	public void updatePasswordRandom(@Param("email") String email, @Param("password") String password) throws Exception;
		
	public int checkEmailConfirm(@Param("email") String email) throws Exception;
}
