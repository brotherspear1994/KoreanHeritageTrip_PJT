package com.web.heritage.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.heritage.model.Favor;
import com.web.heritage.model.mapper.FavorMapper;

@Service
public class FavorServiceImpl implements FavorService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean getCtcd(Favor favor) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).getCtcd(favor) == 1;
	}

	@Override
	public boolean updateCtcd(Favor favor) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).updateCtcd(favor) == 1;
	}

	@Override
	public boolean getBcode(Favor favor) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).getBcode(favor) == 1;
	}

	@Override
	public boolean getCcce(Favor favor) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).getCcce(favor) == 1;
	}

	@Override
	public boolean getFavor(int user_no) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).getFavor(user_no) == 0;
	}

	@Override
	public List<Favor> listRecommend(int user_no) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).listRecommend(user_no);
	}

	@Override
	public boolean deleteInfo(int user_no) throws Exception {
		return sqlSession.getMapper(FavorMapper.class).deleteInfo(user_no) == 1;
	}

}
