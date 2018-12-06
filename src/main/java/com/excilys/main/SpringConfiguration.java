package com.excilys.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.excilys.persistence",
		"com.excilys.service",
		"com.excilys.ui.cli",
		"com.excilys.servlet"})
public class SpringConfiguration {

}
