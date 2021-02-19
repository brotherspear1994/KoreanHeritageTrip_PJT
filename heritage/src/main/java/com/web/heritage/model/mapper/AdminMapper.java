package com.web.heritage.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;

@MapperScan
public interface AdminMapper {

	public int signUp(User user) throws SQLException;

	public int modifyUser(User user) throws SQLException;

	public int deleteUser(int user_id) throws SQLException;

	public List<User> listUser(UserParameter userParameter) throws SQLException;

	public int getTotalCount(UserParameter userParameter) throws SQLException;
}
