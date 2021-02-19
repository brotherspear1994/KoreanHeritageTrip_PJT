package com.web.heritage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HeritageImageDto : 문화재 사진", description = "문화재 사진의 url 주소와 설명을 나타낸다")
public class HeritageImage {

	@ApiModelProperty(value = "문화재 사진의 url")
	private String url;
	@ApiModelProperty(value = "문화재 사진의 설명")
	private String desc;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
