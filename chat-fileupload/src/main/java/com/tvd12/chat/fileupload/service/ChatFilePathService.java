package com.tvd12.chat.fileupload.service;

public interface ChatFilePathService {

	/**
	 * Get file path from file name
	 * 
	 * @param fileName the file name
	 * @return the file path
	 */
	String get(String fileName);
	
	/**
	 * Generate file path from time in millisecond and file name 
	 * 
	 * @param time the time in millisecond
	 * @param fileName the file name
	 * @return the file path
	 */
	String generate(long time, String fileName);
	
}
