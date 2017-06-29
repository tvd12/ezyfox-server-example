package com.tvd12.ezyfoxserver.testing;

import java.security.KeyPair;

import com.tvd12.ezyfoxserver.sercurity.EzyBase64;
import com.tvd12.ezyfoxserver.sercurity.EzyKeysGenerator;

public class RsaKeyGenerator {

	public static void main(String[] args) {
		KeyPair keyPair = EzyKeysGenerator.builder()
				.algorithm("RSA")
				.keysize(512)
				.build().generate();
		System.out.println(EzyBase64.encode2utf(keyPair.getPublic().getEncoded()));
	}
	
}
