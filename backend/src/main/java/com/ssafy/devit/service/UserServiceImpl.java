package com.ssafy.devit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.devit.model.request.UserProfileUpdateReqeust;
import com.ssafy.devit.model.user.User;
import com.ssafy.devit.model.user.UserAuthDetails;
import com.ssafy.devit.model.user.UserResponse;
import com.ssafy.devit.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserResponse> getUsers() throws Exception {
		return userRepository.findUsers();
	}

	@Override
	public User getUserByUserEmail(String email) throws Exception {
		if (email.equals("")) {
			throw new Exception("Eamil의 값이 비어있습니다.");
		}
//		return userRepository.findUserByUserEmail(email);
		return null;
	}

	@Override
	public void registRole(long userId, String role) throws Exception {
		userRepository.insertRole(userId, role);
	}

	public Collection<GrantedAuthority> getAuthorities(long userId) throws Exception {
		List<String> stringAuthorities = userRepository.findRoles(userId);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : stringAuthorities) {
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		return authorities;
	}

	@Override
	public UserResponse getUserByUserId() throws Exception {
		// 사용자 id 가져오기
		UserAuthDetails user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserResponse result = new UserResponse();
		result.refact(userRepository.selectOneUserInfo(user.getUserId()));
		return result;
	}

	@Override
	public User getUserByNickname(String nickname) throws Exception {
		if (nickname.equals("")) {
			throw new Exception("Nickname의 값이 비어있습니다.");
		}
		return userRepository.findUserByNickname(nickname);
	}
	
	@Override
	public User getUserByEmail(String email) throws Exception {
		if (email.equals("")) {
			throw new Exception("Email의 값이 비어있습니다.");
		}
		return userRepository.findUserByEmailAddress(email);
	}

	@Override
	public List<UserResponse> getUsersByLikeSearchText(String search, long lectureId) throws Exception {
		return userRepository.findUsersByLikeSearchText(search, lectureId);
	}

	@Override
	public void modifyUserInfo(UserProfileUpdateReqeust request) throws Exception {
		
		userRepository.updateUserInfo(request);
	}
	
	@Override
	public String getUserPasswordByUserId(long userId) throws Exception {
		return userRepository.getUserPasswordByUserId(userId);
	}
	
	@Override
	public String getUserIsAdmin(long userId) throws Exception{
		return userRepository.getUserIsAdmin(userId);
	};
	
	@Override
	public void userWithdraw(long userId) throws Exception{
		userRepository.userWithdraw(userId);
	};
}
