package com.fpt.macm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fpt.macm.config.AppProperties;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppProperties.class)
public class MacmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacmApplication.class, args);
	}
	
//	@Bean
//	public WebMvcConfigurer configure() {
//		return new WebMvcConfigurer() {
//		@Override
//		public void addCorsMappings(CorsRegistry reg) {
//			reg.addMapping("/**")
//	        .allowedOrigins("*")
//	        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//	        .maxAge(3600);
//		}
//		};
//	}

}
