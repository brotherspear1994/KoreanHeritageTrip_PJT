package com.web.heritage.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;

@Mapper
public interface UserMapper {

	public User login(User user) throws SQLException;
	public User userInfo(String user_id) throws SQLException;
	public String findPwd(User user) throws SQLException;
	public int modifyPwd(User user) throws SQLException;
	public String findId(User user) throws SQLException;
	public int insertAuthKey(User user) throws SQLException;
	public int alterAuthKey(Map<String, String> map) throws SQLException;
	public String getAuthKey(Map<String, String> map) throws SQLException;

	public int signUp(User user) throws SQLException;
	public int getTotalCount(UserParameter userParameter) throws SQLException;
	public int modifyUser(User user) throws SQLException;
	public int deleteUser(int user_no) throws SQLException;
	public String getPassword(String user_id) throws SQLException;

}
