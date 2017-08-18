package com.tvd12.chat.fileupload.service.impl;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FilenameUtils;

import com.tvd12.chat.fileupload.service.ChatFileNameService;

public class ChatSimpleFileNameService implements ChatFileNameService {

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.chat.fileupload.service.EzyFileNameGenerator#generate(long, java.lang.String, long)
	 */
	@Override
	public String generate(long fileId, String original, long time) {
		return new StringBuilder()
                .append(fileId)
                .append("_")
                .append(time)
                .append("_")
                .append(getRandom().nextInt(Integer.MAX_VALUE))
                .append(".")
                .append(FilenameUtils.getExtension(original).toLowerCase())
                .toString();
	}
	
	private Random getRandom() {
		return ThreadLocalRandom.current();
	}
	
}
