package com.web.heritage.model.service;

import java.util.List;

import com.web.heritage.model.Favor;

public interface FavorService {

	public boolean getCtcd(Favor favor) throws Exception;
	public boolean updateCtcd(Favor favor) throws Exception;
	public boolean getBcode(Favor favor) throws Exception;
	public boolean getCcce(Favor favor) throws Exception;
	public boolean getFavor(int user_no) throws Exception;
	public List<Favor> listRecommend(int user_no) throws Exception;
	public boolean deleteInfo(int user_no) throws Exception;

}
