package com.web.heritage.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.heritage.model.Favor;

@Mapper
public interface FavorMapper {
	public int getCtcd(Favor favor) throws SQLException;
	public int updateCtcd(Favor favor) throws SQLException;
	public int getBcode(Favor favor) throws SQLException;
	public int getCcce(Favor favor) throws SQLException;
	public int getFavor(int user_no) throws SQLException;
	public int deleteInfo(int user_no) throws SQLException;
	public List<Favor> listRecommend(int user_no) throws SQLException;
}
