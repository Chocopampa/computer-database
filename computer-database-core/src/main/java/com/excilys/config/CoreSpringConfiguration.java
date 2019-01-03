package com.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(value = {ConverterSpringConfiguration.class})
@ComponentScan(value = { "com.excilys.model", "com.excilys.dto" })
public class CoreSpringConfiguration {
}
