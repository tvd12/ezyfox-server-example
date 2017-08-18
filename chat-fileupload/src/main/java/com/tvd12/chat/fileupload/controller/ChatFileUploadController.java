package com.tvd12.chat.fileupload.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.tvd12.chat.fileupload.data.ChatFileUploadResult;
import com.tvd12.chat.fileupload.data.ChatMessage;
import com.tvd12.chat.fileupload.data.ChatSaveFileResult;
import com.tvd12.chat.fileupload.service.ChatFileUploadService;
import com.tvd12.chat.fileupload.service.ChatLoggingService;
import com.tvd12.ezyfoxserver.exception.EzyFileNotFoundException;
import com.tvd12.ezyfoxserver.file.EzyFiles;
import com.tvd12.ezyfoxserver.util.EzyLoggable;

@RestController
@RequestMapping("file-upload")
public class ChatFileUploadController extends EzyLoggable {

	@Autowired
	protected ChatLoggingService loggingService;
	
	@Autowired
    protected ChatFileUploadService fileUploadService;
	
	@ResponseBody
    @GetMapping(value = {"/files/{filename:.+}"})
    public ResponseEntity<Resource> serveFile(
    		@PathVariable String fileName,
    		HttpServletRequest request) throws Exception {
		loggingService.whenServeFile(fileName, request);
        Resource file = fileUploadService.getFileAsResource(fileName);
        String type = EzyFiles.getFileType(file.getFile());
        return ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping
    public ResponseEntity<ChatFileUploadResult> uploadFile(
    		@RequestParam("file") MultipartFile file,
    		HttpServletRequest request) {
    	try {
			getLogger().info("user upload file {} with type {}", file.getOriginalFilename(), new Tika().detect(file.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	loggingService.whenUploadFile(file, request);
    	// save file
        ChatSaveFileResult saveResult = fileUploadService.saveFile(file);
        
        // prepare response body
        ChatFileUploadResult result = new ChatFileUploadResult();
        
        // origin file name
        result.setFileName(file.getOriginalFilename());
        
        // file location
        result.setFileLocation(getFileUri(request, saveResult.getFileName()));
        
        // response file upload result
        return ResponseEntity.ok().body(result);
    }
    
    protected String getFileUri(HttpServletRequest request, String fileName) {
    	return new StringBuilder(request.getRequestURL())
    			.append("/")
    			.append(fileName)
    			.toString();
    }

	@ExceptionHandler(EzyFileNotFoundException.class)
    public ResponseEntity<ChatMessage> handleFileNotFound(EzyFileNotFoundException exc) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.headers(headers)
				.body(new ChatMessage(exc.getMessage()));
    }

}
