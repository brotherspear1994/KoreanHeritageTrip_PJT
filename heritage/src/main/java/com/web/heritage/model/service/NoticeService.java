package com.web.heritage.model.service;

import java.util.List;

import com.web.heritage.model.Notice;
import com.web.heritage.model.NoticeParameter;

public interface NoticeService {

	public boolean writeNoticepage(Notice notice) throws Exception;

	public List<Notice> listNoticepage(NoticeParameter noticeParameter) throws Exception;
//	public PageNavigation makePageNavigation(NoticeParameter noticeParameter) throws Exception;

	public Notice getNoticepage(int notice_no) throws Exception;

	public boolean modifyNoticepage(Notice notice) throws Exception;

	public boolean deleteNoticepage(int notice_no) throws Exception;

}
