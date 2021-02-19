package com.web.heritage.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.web.heritage.model.Favor;
import com.web.heritage.model.service.FavorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("FavorController V1")
@RestController
@RequestMapping("/heritage")
public class FavorController {

	public static final Logger logger = LoggerFactory.getLogger(FavorController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private FavorService favorService;

	@ApiOperation(value = "시도명 입력", notes = "선호하는 시도명1,2,3을 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/ctcd")
	public ResponseEntity<String> getCtcd(@RequestBody @ApiParam(value = "선호 정보", required = true) Favor favor){
		logger.debug("getCtcd - 호출");
		HttpStatus status = HttpStatus.NO_CONTENT;
		String result = FAIL;

		try {
			if(favorService.getCtcd(favor)) {
				status = HttpStatus.OK;
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "시도명 수정", notes = "선호하는 시도명1,2,3을 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/ctcd")
	public ResponseEntity<String> updateCtcd(@RequestBody @ApiParam(value = "선호 정보", required = true) Favor favor) {
		logger.debug("updateCtcd - 호출");
		HttpStatus status = HttpStatus.NO_CONTENT;
		String result = FAIL;

		try {
			if(favorService.updateCtcd(favor)) {
				status = HttpStatus.OK;
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "분류 입력", notes = "선호하는 분류1,2,3을 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/bcode")
	public ResponseEntity<String> getBcode(@RequestBody @ApiParam(value = "선호 정보", required = true) Favor favor){
		logger.debug("getBcode - 호출");
		HttpStatus status = HttpStatus.NO_CONTENT;
		String result = FAIL;

		try {
			if(favorService.getBcode(favor)) {
				status = HttpStatus.OK;
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "시대 입력", notes = "선호하는 시대1,2,3을 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/ccce")
	public ResponseEntity<String> getCcce(@RequestBody @ApiParam(value = "선호 정보", required = true) Favor favor){
		logger.debug("getCcce - 호출");
		HttpStatus status = HttpStatus.NO_CONTENT;
		String result = FAIL;

		try {
			if(favorService.getCcce(favor)) {
				status = HttpStatus.OK;
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "추천 정보 유무 확인", notes = "회원번호에 해당하는 추천 정보의 수를 반환한다.", response = String.class)
	@GetMapping("/{user_no}")
	public ResponseEntity<String> getFavor(@PathVariable("user_no") @ApiParam(value = "회원 번호", required = true) int user_no){
		logger.debug("getFavor - 호출");
		HttpStatus status = HttpStatus.NO_CONTENT;
		String result = FAIL;

		try {
			if(favorService.getFavor(user_no)) {
				status = HttpStatus.OK;
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "정보 삭제", notes = "회원 번호에 해당하는 추천 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/recommend/{user_no}")
	public ResponseEntity<String> deleteInfo(
		@PathVariable("user_no") @ApiParam(value = "삭제할 회원의 회원 번호.", required = true) int user_no) throws Exception {
		logger.debug("deleteInfo - 호출");
		if (favorService.deleteInfo(user_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "추천 목록", notes = "추천 문화재 정보를 반환한다.", response = List.class)
	@GetMapping("/recommend/{user_no}")
	public ResponseEntity<List<Favor>> listRecommend(
		@PathVariable("user_no") @ApiParam(value = "추천 문화재 정보를 얻기위한 부가정보.", required = true) int user_no) throws Exception {
		logger.debug("listRecommend - 호출");
		return new ResponseEntity<List<Favor>>(favorService.listRecommend(user_no), HttpStatus.OK);
	}

}
