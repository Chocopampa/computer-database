package com.excilys.config;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan(value = {"com.excilys.persistence",
		"com.excilys.service",
		"com.excilys.mapper",
		"com.excilys.validator"})
public class SpringConfiguration {
}
