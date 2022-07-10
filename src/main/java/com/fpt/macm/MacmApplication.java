package com.fpt.macm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fpt.macm.config.AppConfig;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppConfig.class)
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
//	        .allowedOriginPatterns("*")
//	        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//	        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                    "Access-Control-Request-Headers")
//            .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//            .allowCredentials(true).maxAge(3600);
//		}
//		};
//	}

}
