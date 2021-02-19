package com.web.heritage.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;
import com.web.heritage.model.mapper.AdminMapper;
import com.web.heritage.security.SHA512;
import com.web.util.PageNavigation;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean signUp(User user) throws Exception {
		if (user.getUser_id() == null || user.getUser_name() == null || user.getUser_password() == null
			|| user.getUser_phone() == null) {
			throw new Exception();
		}
		// 패스워드 암호화저장
		try {
			String hash = SHA512.sha(user.getUser_password(), user.getUser_name());
			user.setUser_password(hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminMapper.class).signUp(user) == 1;
	}

	@Override
	@Transactional
	public boolean modifyUser(User user) throws Exception {
		// 패스워드 암호화저장
		try {
			if (user.getUser_password() != null) {
				String hash = SHA512.sha(user.getUser_password(), user.getUser_name());
				user.setUser_password(hash);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminMapper.class).modifyUser(user) == 1;
	}

	@Override
	@Transactional
	public boolean deleteUser(int user_id) throws Exception {
		return sqlSession.getMapper(AdminMapper.class).deleteUser(user_id) == 1;
	}

	@Override
	public List<User> listUser(UserParameter userParameter) throws Exception {
		int start = userParameter.getPg() == 0 ? 0 : (userParameter.getPg() - 1) * userParameter.getSpp();
		userParameter.setStart(start);
		return sqlSession.getMapper(AdminMapper.class).listUser(userParameter);
	}

	@Override
	public PageNavigation makePageNavigation(UserParameter userParameter) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(userParameter.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(AdminMapper.class).getTotalCount(userParameter);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / userParameter.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = userParameter.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < userParameter.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

}
