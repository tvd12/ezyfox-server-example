package com.tvd12.chat.fileupload.service;

public interface ChatFileIdService {

	/**
	 * Generate file id
	 * 
	 * @param original the original file name
	 * @return an id
	 */
    long generate(String original);
    
}
