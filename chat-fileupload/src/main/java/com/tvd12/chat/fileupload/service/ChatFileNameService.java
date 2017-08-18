package com.tvd12.chat.fileupload.service;

public interface ChatFileNameService {

	/**
	 * Generate file name
	 * 
	 * @param fileId the file id
	 * @param original the original file name
	 * @param time current time in millisecond
	 * @return new file name
	 */
    String generate(long fileId, String original, long time);
    
}
