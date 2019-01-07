package com.excilys.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { PersistenceSpringConfiguration.class, SecurityConfiguration.class  };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ViewConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
