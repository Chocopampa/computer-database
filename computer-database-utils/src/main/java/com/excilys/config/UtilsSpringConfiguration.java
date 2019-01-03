package com.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ServiceSpringConfiguration.class})
@ComponentScan(value = { "com.excilys.mapper", "com.excilys.validator" })
public class UtilsSpringConfiguration {
}
