package com.kakuiwong.messagecenterthanos.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: gaoyang
 * @Description:
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext context) {
        ApplicationContextHolder.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
