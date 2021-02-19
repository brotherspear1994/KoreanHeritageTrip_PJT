package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class User {

	@ApiModelProperty(value = "회원 번호")
	private int user_no;
	@ApiModelProperty(value = "회원 아이디")
	private String user_id;
	@ApiModelProperty(value = "회원 이름")
	private String user_name;
	@ApiModelProperty(value = "회원 비밀번호")
	private String user_password;
	@ApiModelProperty(value = "회원 연락처")
	private String user_phone;
	@ApiModelProperty(value = "이메일 인증키")
	private String auth_key;

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getAuth_key() {
		return auth_key;
	}

	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}

	@Override
	public String toString() {
		return "user_ [user_no=" + user_no + ", user_id=" + user_id + ", user_name=" + user_name + ", user_password="
			+ user_password + ", user_phone=" + user_phone + "]";
	}

}
