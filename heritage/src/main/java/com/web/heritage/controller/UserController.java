package com.web.heritage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.heritage.model.User;
import com.web.heritage.model.service.JwtServiceImpl;
import com.web.heritage.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("UserController V1")
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/confirm/login")
	public ResponseEntity<Map<String, Object>> login(

		@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) User user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			User loginUser = userService.login(user);
			if (loginUser != null) {
				String token = jwtService.create("user_id", loginUser.getUser_id(), "access-token");// key, data, subject
				logger.info("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{user_id}")
	public ResponseEntity<Map<String, Object>> getInfo(
		@PathVariable("user_id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String user_id,
		HttpServletRequest request) {
		logger.debug("user_id : {} ", user_id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				// 로그인 사용자 정보.
				User user = userService.userInfo(user_id);
				resultMap.put("userInfo", user);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "아이디 찾기", notes = "입력한 이름과 연락처에 해당하는 회원의 아이디를 반환한다.", response = String.class)
	@PostMapping("/find/id")
	public ResponseEntity<String> findId(
		@RequestBody @ApiParam(value = "아이디 찾기 시 필요한 회원정보(이름, 연락처).", required = true) User user)
		throws Exception {
		logger.debug("findId - 호출");
		String result = userService.findId(user);
		if (result != null) {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "비밀번호 찾기", notes = "입력한 아이디와 이름에 해당하는 회원의 비밀번호를 반환한다.", response = String.class)
	@PostMapping("/find/password")
	public ResponseEntity<String> findPwd(
		@RequestBody @ApiParam(value = "비밀번호 찾기 시 필요한 회원정보(아이디, 이름).", required = true) User user)
		throws Exception {
		logger.debug("findPwd - 호출");
		String result = userService.findPwd(user);
		if (result != null) {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "아이디 중복 확인", notes = "아이디 중복을 확인한다. 그리고 확인여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/confirm/{user_id}")
	public ResponseEntity<String> confirmId(
		@PathVariable("user_id") @ApiParam(value = "중복 확인할 회원의 아이디.", required = true) String user_id,
		HttpServletRequest request) throws Exception {
		logger.debug("user_id : {} ", user_id);
		user_id = user_id + "@gmail.com";
		if (userService.confirmId(user_id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일 인증 확인", notes = "이메일 인증 여부를 확인한다. 그리고 인증 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/email")
	public ResponseEntity<String> confirmEmail(
		@RequestParam("user_id") @ApiParam(value = "이메일 인증할 회원의 아이디.", required = true) String user_id,
		@RequestParam("auth_key") @ApiParam(value = "이메일 인증 키.", required = true) String auth_key,
		HttpServletRequest request) throws Exception {
		logger.debug("user_id : {} ", user_id);
		logger.debug("auth_key : {} ", auth_key);
		String success = "이메일 인증에 성공했습니다. 로그인이 가능합니다.";

		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("auth_key", auth_key);
		if (userService.confirmEmail(map)) {
			return new ResponseEntity<String>(success, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "회원가입", notes = "새로운 회원 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> signUp(@RequestBody @ApiParam(value = "회원 정보", required = true) User user)
		throws Exception {
		logger.debug("signUp - 호출");
		user.setUser_id(user.getUser_id() + "@gmail.com");
		if (userService.signUp(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "회원 정보 수정", notes = "새로운 회원 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modifyUser(@RequestBody @ApiParam(value = "수정할 회원 정보.", required = true) User user)
		throws Exception {
		logger.debug("modifyUser - 호출");
		if (userService.modifyUser(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "회원 탈퇴", notes = "회원 번호에 해당하는 회원 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{user_no}")
	public ResponseEntity<String> deleteUser(
		@PathVariable("user_no") @ApiParam(value = "삭제할 회원의 회원 번호.", required = true) int user_no) throws Exception {
		logger.debug("deleteUser - 호출");
		if (userService.deleteUser(user_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "비밀번호 일치 여부", notes = "회원 아이디에 해당하는 비밀번호의 일치 여부를 반환한다.", response = String.class)
	@PostMapping("/confirm")
	public ResponseEntity<String> getPassword(
		@RequestBody @ApiParam(value = "필요한 회원정보(아이디, 확인할 비밀번호).", required = true) User user)
		throws Exception {
		logger.debug("getPassword - 호출");
		if (userService.getPassword(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}