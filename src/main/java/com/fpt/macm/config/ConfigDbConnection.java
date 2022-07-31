package com.fpt.macm.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "spring.datasource")
public class ConfigDbConnection {
	@Bean
	public DataSource dataSource() {
	    return DataSourceBuilder
	            .create()
	            .build();
	}
}
