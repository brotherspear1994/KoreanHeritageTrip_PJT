package com.web.heritage.model.service;

import java.util.List;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;
import com.web.util.PageNavigation;

public interface AdminService {

	public boolean signUp(User user) throws Exception;

	public boolean modifyUser(User user) throws Exception;

	public boolean deleteUser(int user_id) throws Exception;

	public List<User> listUser(UserParameter userParameter) throws Exception;

	public PageNavigation makePageNavigation(UserParameter userParameter) throws Exception;
}
