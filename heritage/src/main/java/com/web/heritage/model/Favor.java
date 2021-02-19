package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "FavorDto : 선호 정보", description = "선호하는 정보를 나타낸다")
public class Favor {
	@ApiModelProperty(value = "선호 정보 테이블 pk")
	private int favor_no;
	@ApiModelProperty(value = "회원 번호")
	private int user_no;
	@ApiModelProperty(value = "시도코드1")
	private String ccba_ctcd_nm1;
	@ApiModelProperty(value = "시도코드2")
	private String ccba_ctcd_nm2;
	@ApiModelProperty(value = "시도코드3")
	private String ccba_ctcd_nm3;
	@ApiModelProperty(value = "문화재 분류1")
	private String bcode_name1;
	@ApiModelProperty(value = "문화재 분류2")
	private String bcode_name2;
	@ApiModelProperty(value = "문화재 분류3")
	private String bcode_name3;
	@ApiModelProperty(value = "시대1")
	private String ccce_name1;
	@ApiModelProperty(value = "시대2")
	private String ccce_name2;
	@ApiModelProperty(value = "시대3")
	private String ccce_name3;

	public int getFavor_no() {
		return favor_no;
	}

	public void setFavor_no(int favor_no) {
		this.favor_no = favor_no;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getCcba_ctcd_nm1() {
		return ccba_ctcd_nm1;
	}

	public void setCcba_ctcd_nm1(String ccba_ctcd_nm1) {
		this.ccba_ctcd_nm1 = ccba_ctcd_nm1;
	}

	public String getCcba_ctcd_nm2() {
		return ccba_ctcd_nm2;
	}

	public void setCcba_ctcd_nm2(String ccba_ctcd_nm2) {
		this.ccba_ctcd_nm2 = ccba_ctcd_nm2;
	}

	public String getCcba_ctcd_nm3() {
		return ccba_ctcd_nm3;
	}

	public void setCcba_ctcd_nm3(String ccba_ctcd_nm3) {
		this.ccba_ctcd_nm3 = ccba_ctcd_nm3;
	}

	public String getBcode_name1() {
		return bcode_name1;
	}

	public void setBcode_name1(String bcode_name1) {
		this.bcode_name1 = bcode_name1;
	}

	public String getBcode_name2() {
		return bcode_name2;
	}

	public void setBcode_name2(String bcode_name2) {
		this.bcode_name2 = bcode_name2;
	}

	public String getBcode_name3() {
		return bcode_name3;
	}

	public void setBcode_name3(String bcode_name3) {
		this.bcode_name3 = bcode_name3;
	}

	public String getCcce_name1() {
		return ccce_name1;
	}

	public void setCcce_name1(String ccce_name1) {
		this.ccce_name1 = ccce_name1;
	}

	public String getCcce_name2() {
		return ccce_name2;
	}

	public void setCcce_name2(String ccce_name2) {
		this.ccce_name2 = ccce_name2;
	}

	public String getCcce_name3() {
		return ccce_name3;
	}

	public void setCcce_name3(String ccce_name3) {
		this.ccce_name3 = ccce_name3;
	}

	@Override
	public String toString() {
		return "Favor [favor_no=" + favor_no + ", user_no=" + user_no + ", ccba_ctcd_nm1=" + ccba_ctcd_nm1
			+ ", ccba_ctcd_nm2=" + ccba_ctcd_nm2 + ", ccba_ctcd_nm3=" + ccba_ctcd_nm3 + ", bcode_name1=" + bcode_name1
			+ ", bcode_name2=" + bcode_name2 + ", bcode_name3=" + bcode_name3 + ", ccce_name1=" + ccce_name1
			+ ", ccce_name2=" + ccce_name2 + ", ccce_name3=" + ccce_name3 + "]";
	}

}
