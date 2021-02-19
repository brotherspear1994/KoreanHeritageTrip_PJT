package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HeritageDto : 문화재 정보", description = "문화재의 정보를 나타낸다")
public class Heritage {

	@ApiModelProperty(value = "문화재 테이블 pk")
	private int heritage_no;
	@ApiModelProperty(value = "문화재종목")
	private String ccma_name;
	@ApiModelProperty(value = "지정호수")
	private String crltsno_nm;
	@ApiModelProperty(value = "문화재명(국문)")
	private String ccba_mnm;
	@ApiModelProperty(value = "시도코드")
	private String ccba_ctcd_nm;
	@ApiModelProperty(value = "시군구명")
	private String ccsi_name;
	@ApiModelProperty(value = "관리자")
	private String ccba_admin;
	@ApiModelProperty(value = "종목코드")
	private String ccba_kdcd;
	@ApiModelProperty(value = "시도코드")
	private String ccba_ctcd;
	@ApiModelProperty(value = "지정번호")
	private String ccba_asno;
	@ApiModelProperty(value = "지정해제여부")
	private String ccba_cncl;
	@ApiModelProperty(value = "문화재연계번호")
	private String ccba_cpno;
	@ApiModelProperty(value = "경도")
	private String longitude;
	@ApiModelProperty(value = "위도")
	private String latitude;
	@ApiModelProperty(value = "문화재분류1")
	private String gcode_name;
	@ApiModelProperty(value = "문화재분류2")
	private String bcode_name;
	@ApiModelProperty(value = "문화재분류3")
	private String mcode_name;
	@ApiModelProperty(value = "문화재분류4")
	private String scode_name;
	@ApiModelProperty(value = "수량")
	private String ccba_quan;
	@ApiModelProperty(value = "지정(등록일)")
	private String ccba_asdt;
	@ApiModelProperty(value = "소재지 상세")
	private String ccba_lcad;
	@ApiModelProperty(value = "시대")
	private String ccce_name;
	@ApiModelProperty(value = "소유자")
	private String ccba_poss;
	@ApiModelProperty(value = "내용")
	private String content;

	public int getHeritage_no() {
		return heritage_no;
	}

	public void setHeritage_no(int heritage_no) {
		this.heritage_no = heritage_no;
	}

	public String getCcma_name() {
		return ccma_name;
	}

	public void setCcma_name(String ccma_name) {
		this.ccma_name = ccma_name;
	}

	public String getCrltsno_nm() {
		return crltsno_nm;
	}

	public void setCrltsno_nm(String crltsno_nm) {
		this.crltsno_nm = crltsno_nm;
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

	public String getCcba_admin() {
		return ccba_admin;
	}

	public void setCcba_admin(String ccba_admin) {
		this.ccba_admin = ccba_admin;
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

	public String getCcba_cncl() {
		return ccba_cncl;
	}

	public void setCcba_cncl(String ccba_cncl) {
		this.ccba_cncl = ccba_cncl;
	}

	public String getCcba_cpno() {
		return ccba_cpno;
	}

	public void setCcba_cpno(String ccba_cpno) {
		this.ccba_cpno = ccba_cpno;
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

	public String getGcode_name() {
		return gcode_name;
	}

	public void setGcode_name(String gcode_name) {
		this.gcode_name = gcode_name;
	}

	public String getBcode_name() {
		return bcode_name;
	}

	public void setBcode_name(String bcode_name) {
		this.bcode_name = bcode_name;
	}

	public String getMcode_name() {
		return mcode_name;
	}

	public void setMcode_name(String mcode_name) {
		this.mcode_name = mcode_name;
	}

	public String getScode_name() {
		return scode_name;
	}

	public void setScode_name(String scode_name) {
		this.scode_name = scode_name;
	}

	public String getCcba_quan() {
		return ccba_quan;
	}

	public void setCcba_quan(String ccba_quan) {
		this.ccba_quan = ccba_quan;
	}

	public String getCcba_asdt() {
		return ccba_asdt;
	}

	public void setCcba_asdt(String ccba_asdt) {
		this.ccba_asdt = ccba_asdt;
	}

	public String getCcba_lcad() {
		return ccba_lcad;
	}

	public void setCcba_lcad(String ccba_lcad) {
		this.ccba_lcad = ccba_lcad;
	}

	public String getCcce_name() {
		return ccce_name;
	}

	public void setCcce_name(String ccce_name) {
		this.ccce_name = ccce_name;
	}

	public String getCcba_poss() {
		return ccba_poss;
	}

	public void setCcba_poss(String ccba_poss) {
		this.ccba_poss = ccba_poss;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
