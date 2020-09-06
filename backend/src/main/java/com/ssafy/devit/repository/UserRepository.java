package com.ssafy.devit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.devit.model.request.UserProfileUpdateReqeust;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.model.user.UserResponse;

public interface UserRepository {

	// 회원 가입(권한 부여)
	public void insertUser(UserAuthDetails user) throws Exception;
	
	// 회원가입 시 닉네임, 이메일 체크 중복 체크
	public int checkValidEmailOrNickname(@Param("email") String email, @Param("nickname") String nickname) throws Exception;
	
	// 등록된 이메일로 유저 검색
	public UserAuthDetails findUserByEmail(String email) throws Exception;
	
	// 등록된 유저 id로 검색
	public UserAuthDetails findUserByUserId(long userId) throws Exception;
	
	// 권한 검색
	public List<String> findRoles(long userId) throws Exception;
	
	// 사용자 상세 정보
	public User selectOneUserInfo(long userId) throws Exception;
	
	public List<UserResponse> findUsers() throws Exception;
	
	public int fundUserByEmailAndPassword(String eamil, String password) throws Exception;

	public User findUserByNickname(String nickname) throws Exception;

	public User findUserByEmailAddress(String email) throws Exception;

	public void insertRole(@Param("userId") long userId, @Param("role") String role) throws Exception;
	
	public List<UserResponse> findUsersByLikeSearchText(@Param("search") String search, @Param("lectureId") long lectureId) throws Exception;
	
	// 사용자 프로필 변경
	public void updateUserInfo(UserProfileUpdateReqeust request) throws Exception; 
	
	public String getUserPasswordByUserId(long userId) throws Exception;

	public String getUserIsAdmin(long userId) throws Exception;
	
	//회원 탈퇴
	public void userWithdraw(long userId) throws Exception;
	
	public String findUserActiveByEmail(String email) throws Exception;
}
