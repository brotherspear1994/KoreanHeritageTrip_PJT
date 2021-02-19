package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Notice : 공지사항정보", description = "공지사항의 상세 정보를 나타낸다")
public class Notice {

	@ApiModelProperty(value = "공지사항번호")
	private int notice_no;
	@ApiModelProperty(value = "작성자번호")
	private int user_no;
//	@ApiModelProperty(value = "작성일자")
//	private String notice_create_date;
//	@ApiModelProperty(value = "수정일자")
//	private String notice_modify_date;
	@ApiModelProperty(value = "공지사항제목")
	private String notice_title;
	@ApiModelProperty(value = "공지사항내용")
	private String notice_content;
	
	public int getNotice_no() {
		return notice_no;
	}
	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
//	public String getNotice_create_date() {
//		return notice_create_date;
//	}
//	public void setNotice_create_date(String notice_create_date) {
//		this.notice_create_date = notice_create_date;
//	}
//	public String getNotice_modify_date() {
//		return notice_modify_date;
//	}
//	public void setNotice_modify_date(String notice_modify_date) {
//		this.notice_modify_date = notice_modify_date;
//	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
		
}
