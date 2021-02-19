package com.web.heritage.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.web.heritage.model.Heritage;
import com.web.heritage.model.Marker;

@MapperScan
public interface HeritageMapper {

	public List<Marker> listHeritage(String word) throws SQLException;

	public Heritage getHeritage(@Param("kdcd") String kdcd, @Param("ctcd") String ctcd, @Param("asno") String asno)
		throws SQLException;
}
