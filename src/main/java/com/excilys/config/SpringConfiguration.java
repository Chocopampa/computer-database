package com.excilys.config;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan(value = {"com.excilys.persistence",
		"com.excilys.service",
		"com.excilys.servlet",
		"com.excilys.mapper"})
public class SpringConfiguration {
}
