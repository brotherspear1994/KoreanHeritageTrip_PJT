package com.web.heritage.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.web.heritage.model.Notice;
import com.web.heritage.model.NoticeParameter;

@MapperScan
public interface NoticeMapper {

	public int writeNoticepage(Notice notice) throws SQLException;

	public List<Notice> listNoticepage(NoticeParameter noticeParameter) throws SQLException;

	public Notice getNoticepage(int notice_no) throws SQLException;

	public int modifyNoticepage(Notice notice) throws SQLException;

	public int deleteNoticepage(int notice_no) throws SQLException;
//	public int getTotalCount(NoticeParameter noticeParameter) throws SQLException;

}
