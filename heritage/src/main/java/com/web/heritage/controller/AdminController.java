package com.web.heritage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.heritage.model.User;
import com.web.heritage.model.UserParameter;
import com.web.heritage.model.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("AdminController V1")
@RestController
@RequestMapping("/admin")
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "회원가입", notes = "새로운 회원 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> signUp(
		@RequestBody @ApiParam(value = "회원 정보", required = true) User user) throws Exception {
		logger.debug("signUp - 호출");
		if (adminService.signUp(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원수정", notes = "수정할 회원 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{user_no}")
	public ResponseEntity<String> modifyUser(
		@RequestBody @ApiParam(value = "회원 정보", required = true) User user) throws Exception {
		logger.debug("modifyUser - 호출");
		if (user.getUser_password() != null && user.getUser_password().trim().equals("")) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		if (adminService.modifyUser(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원삭제", notes = "삭제할 회원 정보를 입력한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{user_no}")
	public ResponseEntity<String> deleteUser(
		@RequestBody @ApiParam(value = "회원 정보", required = true) int user_no) throws Exception {
		logger.debug("modifyUser - 호출");
		if (adminService.deleteUser(user_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원목록", notes = "모든 회원 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<User>> listUser(
		@ApiParam(value = "회원 정보를 얻기위한 부가정보.", required = true) UserParameter userParameter) throws Exception {
		logger.debug("listUser - 호출");
		return new ResponseEntity<List<User>>(adminService.listUser(userParameter), HttpStatus.OK);
	}
}
