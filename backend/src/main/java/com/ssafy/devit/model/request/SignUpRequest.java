package com.ssafy.devit.model.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class SignUpRequest implements Serializable {

	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	@ApiModelProperty(value = "이메일", dataType = "String", required = true)
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
	@ApiModelProperty(value = "비밀번호", dataType = "String", required = true)
	private String password;

	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	@ApiModelProperty(value = "닉네임", dataType = "String", required = true)
	private String nickname;

	public SignUpRequest() {

	}

	public SignUpRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "SignUpRequest [email=" + email + ", password=" + password + ", nickname=" + nickname + "]";
	}

}
