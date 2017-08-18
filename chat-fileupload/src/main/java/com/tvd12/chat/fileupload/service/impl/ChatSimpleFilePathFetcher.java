package com.tvd12.chat.fileupload.service.impl;

import java.io.File;
import java.util.Calendar;

import com.tvd12.chat.fileupload.service.ChatFilePathService;
import com.tvd12.ezyfoxserver.exception.EzyFileNotFoundException;

public class ChatSimpleFilePathFetcher implements ChatFilePathService {

	@Override
	public String get(String fileName) {
		return generate(getTime(fileName), fileName);
	}
	
	@Override
	public String generate(long time, String fileName) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return new StringBuilder()
				.append(cal.get(Calendar.YEAR))
				.append(File.separator)
				.append(cal.get(Calendar.MONTH) + 1)
				.append(File.separator)
				.append(cal.get(Calendar.DAY_OF_MONTH))
				.append(File.separator)
				.append(cal.get(Calendar.HOUR_OF_DAY))
				.append(File.separator)
				.append(cal.get(Calendar.MINUTE))
				.append(File.separator)
				.append(cal.get(Calendar.SECOND))
				.append(File.separator)
				.append(cal.get(Calendar.MILLISECOND))
				.append(File.separator)
				.append(fileName)
				.toString();
	}
	
	private long getTime(String fileName) {
		try {
			String[] strs = fileName.split("_");
			return Long.valueOf(strs[1]);
		}
		catch(Exception e) {
			throw new EzyFileNotFoundException(fileName + " not found ", e);
		}
	}
	
}
