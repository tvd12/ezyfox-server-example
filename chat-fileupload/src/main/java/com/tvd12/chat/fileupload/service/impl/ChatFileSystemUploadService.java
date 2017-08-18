package com.tvd12.chat.fileupload.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import com.tvd12.chat.fileupload.data.ChatSaveFileResult;
import com.tvd12.chat.fileupload.exception.ChatFileUploadException;
import com.tvd12.chat.fileupload.service.ChatFileIdService;
import com.tvd12.chat.fileupload.service.ChatFileNameService;
import com.tvd12.chat.fileupload.service.ChatFilePathService;
import com.tvd12.chat.fileupload.service.ChatFileUploadService;
import com.tvd12.ezyfoxserver.exception.EzyFileNotFoundException;

public class ChatFileSystemUploadService implements ChatFileUploadService {
	
	protected final Path storageLocation;
	protected final ChatFileIdService fileIdService;
	protected final ChatFileNameService fileNameService;
	protected final ChatFilePathService filePathService;
	
	public ChatFileSystemUploadService() {
		this.storageLocation = Paths.get(new File("files").toString());
		this.fileIdService = new ChatSimpleFileIdService();
		this.fileNameService = new ChatSimpleFileNameService();
		this.filePathService = new ChatSimpleFilePathFetcher();
	}

    @Override
    public ChatSaveFileResult saveFile(MultipartFile file) {
        validateUploadFile(file);
        return acceptUploadFile(file);
    }
    
    protected ChatSaveFileResult acceptUploadFile(MultipartFile file) {
    	try {
    		// original file name
    		String original = file.getOriginalFilename();
    		
    		// generate file id
    		long fileId = fileIdService.generate(original);
    		
    		long time = System.currentTimeMillis();
    		
    		// generate file name
    		String fileName = fileNameService.generate(fileId, original, time);
    		
    		// generate file path
    		String filePath = filePathService.generate(time, fileName);
    		
    		// make full file path
    		Path fullFilePath = storageLocation.resolve(filePath);
    		
    		// create directories
    		Files.createDirectories(fullFilePath.getParent());
    		
    		//save file
            Files.copy(file.getInputStream(), fullFilePath);
            
            // make result
            ChatSaveFileResult answer = new ChatSaveFileResult();
            answer.setFileName(fileName);
            
            return answer;
        } catch (IOException e) {
            throw new ChatFileUploadException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
    
    protected void validateUploadFile(MultipartFile file) {
    	if (file.isEmpty())
            throw new ChatFileUploadException("Failed to store empty file " + file.getOriginalFilename());
    }
    
    @Override
    public Resource getFileAsResource(String fileName) {
        try {
        	// get file path from file name
        	String filePath = filePathService.get(fileName);
        	
        	// get full file path
            Path file = storageLocation.resolve(filePath);
            
            // get resource
            Resource resource = new UrlResource(file.toUri());
            
            // validate resource
            if(resource.exists() || resource.isReadable())
                return resource;
            else 
                throw new EzyFileNotFoundException("File " + fileName + " not found");
        } catch (MalformedURLException e) {
            throw new EzyFileNotFoundException("Could not read file " + fileName, e);
        }
    }

}
