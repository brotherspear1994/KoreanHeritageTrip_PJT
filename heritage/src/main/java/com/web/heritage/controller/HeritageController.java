package com.web.heritage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.heritage.model.Heritage;
import com.web.heritage.model.HeritageImage;
import com.web.heritage.model.Marker;
import com.web.heritage.model.service.HeritageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("HeritageController V1")
@RestController
public class HeritageController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private HeritageService heritageService;

	@ApiOperation(value = "문화재 목록", notes = "해당하는 문화재 정보 리스트를 반환한다.", response = List.class)
	@GetMapping("/heritages/{word}")
	public ResponseEntity<List<Marker>> listHeritage(
		@PathVariable("word") @ApiParam(value = "검색할 키워드", required = true) String word) throws Exception {
		logger.debug("listHeritage - 호출");
		return new ResponseEntity<List<Marker>>(heritageService.listHeritage(word.trim()), HttpStatus.OK);
	}

	@ApiOperation(value = "문화재 정보", notes = "해당하는 문화재 상세 정보를 반환한다.", response = Heritage.class)
	@GetMapping("/heritage")
	public ResponseEntity<Heritage> getHeritage(
		@RequestParam(value = "kdcd", required = true) String kdcd,
		@RequestParam(value = "ctcd", required = true) String ctcd,
		@RequestParam(value = "asno", required = true) String asno) throws Exception {
		logger.debug("Heritage - 호출");
		return new ResponseEntity<Heritage>(heritageService.getHeritage(kdcd, ctcd, asno), HttpStatus.OK);
	}

	@ApiOperation(value = "문화재 이미지 정보", notes = "해당하는 문화재의 이미지 정보를 반환한다.", response = HeritageImage.class)
	@GetMapping("/images")
	public ResponseEntity<List<HeritageImage>> getImages(
		@RequestParam(value = "kdcd", required = true) String kdcd,
		@RequestParam(value = "ctcd", required = true) String ctcd,
		@RequestParam(value = "asno", required = true) String asno) throws Exception {
		logger.debug("getHeritageImage - 호출");
		return new ResponseEntity<List<HeritageImage>>(heritageService.getImages(kdcd, ctcd, asno), HttpStatus.OK);
	}
}
