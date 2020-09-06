package com.ssafy.devit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.devit.model.CommonResponse;
import com.ssafy.devit.service.FilesStorageService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommonResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = CommonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = CommonResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = CommonResponse.class) })
@RestController
@RequestMapping("/api/v1/file")
public class FilesController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilesStorageService filesStorageService;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/upload")
	@ApiOperation(value = "파일 업로드")
	public ResponseEntity<CommonResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		log.info(">> loadUploadFile <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {			
			result.result = filesStorageService.save(file);
			result.msg = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<>(result, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}
}
