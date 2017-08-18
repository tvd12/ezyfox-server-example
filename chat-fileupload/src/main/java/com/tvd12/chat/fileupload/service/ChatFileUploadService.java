package com.tvd12.chat.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tvd12.chat.fileupload.data.ChatSaveFileResult;

public interface ChatFileUploadService {

    ChatSaveFileResult saveFile(MultipartFile file);

    Resource getFileAsResource(String filename);

}
