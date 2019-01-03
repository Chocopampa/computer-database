package com.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {PersistenceSpringConfiguration.class})
@ComponentScan(value = { "com.excilys.service" })
public class ServiceSpringConfiguration {
}
