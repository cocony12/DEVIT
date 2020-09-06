package com.ssafy.devit.service;

import java.util.List;

import com.ssafy.devit.model.request.UserProfileUpdateReqeust;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserResponse;

public interface UserService {
	public List<UserResponse> getUsers() throws Exception;

	public User getUserByUserEmail(String email) throws Exception;

	public UserResponse getUserByUserId() throws Exception;

	public User getUserByNickname(String nickname) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public void registRole(long userId, String role) throws Exception;

	public List<UserResponse> getUsersByLikeSearchText(String search, long lectureId) throws Exception;

	// 사용자 프로필 변경
	public void modifyUserInfo(UserProfileUpdateReqeust request) throws Exception;
	
	public String getUserPasswordByUserId(long userId) throws Exception;

	public String getUserIsAdmin(long userId) throws Exception;
	
	public void userWithdraw(long userId) throws Exception;
}
