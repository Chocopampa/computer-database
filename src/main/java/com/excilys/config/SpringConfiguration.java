package com.excilys.config;


//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan(value = {"com.excilys.persistence",
		"com.excilys.service",
		"com.excilys.servlet",
		"com.excilys.mapper"})
public class SpringConfiguration {
//
//	@Bean
//	@ConfigurationProperties(prefix="spring.datasource")
//	public HikariDataSource dataSource() {
//		return (HikariDataSource) DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//	
}
