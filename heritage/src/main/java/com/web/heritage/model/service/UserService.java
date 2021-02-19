package com.web.heritage.model.service;

import java.util.Map;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;
import com.web.util.PageNavigation;

public interface UserService {

	public User login(User user) throws Exception;

	public User userInfo(String user_id) throws Exception;

	public String findId(User user) throws Exception;

	public String findPwd(User user) throws Exception;

	public boolean modifyPwd(User user) throws Exception;

	public boolean confirmId(String user_id) throws Exception;

	public boolean confirmEmail(Map<String, String> map) throws Exception;

	public boolean alterAuthKey(Map<String, String> map) throws Exception;

	public String getAuthKey(Map<String, String> map) throws Exception;

	public boolean signUp(User user) throws Exception;

	public PageNavigation makePageNavigation(UserParameter userParameter) throws Exception;

	public boolean modifyUser(User user) throws Exception;

	public boolean deleteUser(int user_no) throws Exception;

	public boolean getPassword(User user) throws Exception;

}
