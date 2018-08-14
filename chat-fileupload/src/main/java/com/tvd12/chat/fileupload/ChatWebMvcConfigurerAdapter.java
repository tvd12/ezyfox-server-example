package com.tvd12.chat.fileupload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.mapping.jackson.EzyObjectMapperBuilder;

@Configuration
public class ChatWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Autowired
	protected HandlerInterceptor handlerInterceptor;

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new EzyObjectMapperBuilder().build();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptor);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(new ByteArrayHttpMessageConverter());
		super.configureMessageConverters(converters);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentTypeStrategy(new ContentNegotiationStrategy() {
			@Override
			public List<MediaType> resolveMediaTypes(NativeWebRequest request) 
					throws HttpMediaTypeNotAcceptableException {
				List<MediaType> types = new ArrayList<>();
				types.add(MediaType.IMAGE_GIF);
				types.add(MediaType.IMAGE_JPEG);
				types.add(MediaType.IMAGE_PNG);
				types.add(MediaType.APPLICATION_OCTET_STREAM);
				types.add(MediaType.APPLICATION_PDF);
				types.add(MediaType.APPLICATION_JSON);
				return types;
			}
		});
		super.configureContentNegotiation(configurer);
	}
}
