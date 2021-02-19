package com.web.heritage.model.service;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;
import com.web.heritage.model.mapper.UserMapper;
import com.web.heritage.security.RandomKey;
import com.web.heritage.security.SHA512;
import com.web.util.MailUtils;
import com.web.util.PageNavigation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private JavaMailSender mailSender;
	private String from = "A207@gmail.com";
	private String subject = "회원가입 인증메일입니다.";

	private String emailY = "Y";

	@Override
	public User login(User user) throws Exception {
		if (user.getUser_id() == null || user.getUser_password() == null) {
			return null;
		}
		String hash = SHA512.sha(user.getUser_password(), user.getUser_id()); // 비밀번호 암호화
		user.setUser_password(hash);

		User loginUser = sqlSession.getMapper(UserMapper.class).login(user);
		if(loginUser != null) {
			if (loginUser.getAuth_key().equals(emailY)) { // 이메일 인증 성공
				return loginUser;
			}
		}
		return null;
	}

	@Override
	public User userInfo(String user_id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(user_id);
	}

	@Override
	public String findId(User user) throws Exception {
		String id = sqlSession.getMapper(UserMapper.class).findId(user);
		if(id != null) {
			return id;
		}
		return null;
	}

	@Override
	public String findPwd(User user) throws Exception {
		String password = sqlSession.getMapper(UserMapper.class).findPwd(user); //기존 비밀번호
		if (password != null) {
			password = RandomKey.create(8);
			String hash = SHA512.sha(password, user.getUser_id()); // 비밀번호 암호화
			user.setUser_password(hash);
			if (modifyPwd(user)) {
				return password;
			}
		}
		return null;
	}

	@Override
	public boolean modifyPwd(User user) throws Exception {
		return sqlSession.getMapper(UserMapper.class).modifyPwd(user) == 1;
	}

	@Override
	public boolean confirmId(String user_id) throws Exception {
		if(userInfo(user_id) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean confirmEmail(Map<String, String> map) throws Exception {
		if (getAuthKey(map) != null) {
			map.put("auth_key", emailY);
			if(alterAuthKey(map)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean alterAuthKey(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).alterAuthKey(map) == 1;
	}

	@Override
	public String getAuthKey(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).getAuthKey(map);
	}

	@Override
	public boolean signUp(User user) throws Exception {
		if (user.getUser_id() == null || user.getUser_name() == null || user.getUser_password() == null
			|| user.getUser_phone() == null) {
			throw new Exception();
		}
		String auth_key = RandomKey.create(8); // auth_key 생성
		user.setAuth_key(auth_key);

		String hash = SHA512.sha(user.getUser_password(), user.getUser_id()); // 비밀번호 암호화
		user.setUser_password(hash);

		MailUtils mail; // 메일 발송
		mail = new MailUtils(mailSender);
		String mailContent = "<h1>[이메일 인증]</h1>"
			+ "<br>"
			+ "<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
			+ "<a href='http://i4a207.p.ssafy.io:8080/heritage/user/email?user_id="
			+ user.getUser_id() + "&auth_key=" + auth_key + "' target='_blenk'>"
			+ "이메일 인증 확인</a>";
		mail.setTo(user.getUser_id());
		mail.setText(mailContent);
		mail.setSubject(subject);
		mail.setFrom(from, "관리자");
		mail.send();

		return sqlSession.getMapper(UserMapper.class).signUp(user) == 1;
	}

	@Override
	public PageNavigation makePageNavigation(UserParameter userParameter) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(userParameter.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(UserMapper.class).getTotalCount(userParameter);
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

	@Override
	public boolean modifyUser(User user) throws Exception {
		String hash = SHA512.sha(user.getUser_password(), user.getUser_id()); // 비밀번호 암호화
		user.setUser_password(hash);

		return sqlSession.getMapper(UserMapper.class).modifyUser(user) == 1;
	}

	@Override
	public boolean deleteUser(int user_no) throws Exception {
		return sqlSession.getMapper(UserMapper.class).deleteUser(user_no) == 1;
	}

	@Override
	public boolean getPassword(User user) throws Exception {
		String hash = SHA512.sha(user.getUser_password(), user.getUser_id()); // 비밀번호 암호화
		String pwd = sqlSession.getMapper(UserMapper.class).getPassword(user.getUser_id());

		if(hash.equals(pwd)) {
			return true;
		}

		return false;
	}


}
