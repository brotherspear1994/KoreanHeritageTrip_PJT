package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HeritageDto : 문화재 마커 정보", description = "문화재의 정보를 나타낸다")
public class Marker {

	@ApiModelProperty(value = "마커 테이블 pk")
	private int marker_no;
	@ApiModelProperty(value = "문화재명(국문)")
	private String ccba_mnm;
	@ApiModelProperty(value = "시도코드")
	private String ccba_ctcd_nm;
	@ApiModelProperty(value = "시군구명")
	private String ccsi_name;
	@ApiModelProperty(value = "종목코드")
	private String ccba_kdcd;
	@ApiModelProperty(value = "시도코드")
	private String ccba_ctcd;
	@ApiModelProperty(value = "지정번호")
	private String ccba_asno;
	@ApiModelProperty(value = "경도")
	private String longitude;
	@ApiModelProperty(value = "위도")
	private String latitude;
	@ApiModelProperty(value = "이미지 url")
	private String img_url;
	@ApiModelProperty(value = "내용")
	private String content;

	public int getMarker_no() {
		return marker_no;
	}

	public void setMarker_no(int marker_no) {
		this.marker_no = marker_no;
	}

	public String getCcba_mnm() {
		return ccba_mnm;
	}

	public void setCcba_mnm(String ccba_mnm) {
		this.ccba_mnm = ccba_mnm;
	}

	public String getCcba_ctcd_nm() {
		return ccba_ctcd_nm;
	}

	public void setCcba_ctcd_nm(String ccba_ctcd_nm) {
		this.ccba_ctcd_nm = ccba_ctcd_nm;
	}

	public String getCcsi_name() {
		return ccsi_name;
	}

	public void setCcsi_name(String ccsi_name) {
		this.ccsi_name = ccsi_name;
	}

	public String getCcba_kdcd() {
		return ccba_kdcd;
	}

	public void setCcba_kdcd(String ccba_kdcd) {
		this.ccba_kdcd = ccba_kdcd;
	}

	public String getCcba_ctcd() {
		return ccba_ctcd;
	}

	public void setCcba_ctcd(String ccba_ctcd) {
		this.ccba_ctcd = ccba_ctcd;
	}

	public String getCcba_asno() {
		return ccba_asno;
	}

	public void setCcba_asno(String ccba_asno) {
		this.ccba_asno = ccba_asno;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
