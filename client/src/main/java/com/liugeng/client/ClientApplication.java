package com.liugeng.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args){
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	public RestTemplate myRestTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder.requestFactory(HttpComponentsClientHttpRequestFactory.class)
					  .messageConverters(new MappingJackson2HttpMessageConverter())
					  .errorHandler(new DefaultResponseErrorHandler())
					  .setConnectTimeout(5000)
					  .setReadTimeout(5000)
				      .uriTemplateHandler(new DefaultUriTemplateHandler())
					  .build();
	}
}
