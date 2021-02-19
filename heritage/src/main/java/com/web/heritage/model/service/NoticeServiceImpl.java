package com.web.heritage.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.heritage.model.Notice;
import com.web.heritage.model.NoticeParameter;
import com.web.heritage.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	/* 공지사항 작성 */
	public boolean writeNoticepage(Notice notice) throws Exception {
		if (notice.getNotice_title() == null || notice.getNotice_content() == null) { // 공지사항 작성시 제목 또는 내용이 비었을 시
			throw new Exception();
		}
		return sqlSession.getMapper(NoticeMapper.class).writeNoticepage(notice) == 1;
	}

	@Override
	public List<Notice> listNoticepage(NoticeParameter noticeParameter) throws Exception {
		int start = noticeParameter.getPg() == 0 ? 0
				: (noticeParameter.getPg() - 1) * noticeParameter.getSpp();
		noticeParameter.setStart(start);
		return sqlSession.getMapper(NoticeMapper.class).listNoticepage(noticeParameter);
	}

	@Override
	public Notice getNoticepage(int notice_no) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).getNoticepage(notice_no);
	}

	@Override
	@Transactional
	public boolean modifyNoticepage(Notice notice) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).modifyNoticepage(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNoticepage(int notice_no) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).deleteNoticepage(notice_no) == 1;
	}

//	@Override
//	public PageNavigation makePageNavigation(NoticeParameter noticeParameter) throws Exception {
//		int naviSize = 5;
//		PageNavigation pageNavigation = new PageNavigation();
//		pageNavigation.setCurrentPage(noticeParameter.getPg());
//		pageNavigation.setNaviSize(naviSize);
//		int totalCount = sqlSession.getMapper(NoticeMapper.class).getTotalCount(noticeParameter);
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / noticeParameter.getSpp() + 1;//27
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = noticeParameter.getPg() <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < noticeParameter.getPg();
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//		return pageNavigation;
//	}

}
