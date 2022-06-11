package com.fpt.macm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MacmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacmApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry reg) {
			reg.addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true)
	        .maxAge(3600);
		}
		};
	}

}
