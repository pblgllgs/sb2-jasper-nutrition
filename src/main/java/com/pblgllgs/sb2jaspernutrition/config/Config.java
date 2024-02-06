package com.pblgllgs.sb2jaspernutrition.config;
/*
 *
 * @author pblgl
 * Created on 05-02-2024
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class Config {

    @Bean
    public LocaleResolver localResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:messages");
        reloadableResourceBundleMessageSource.setCacheMillis(2500000);
        reloadableResourceBundleMessageSource.setDefaultEncoding("ISO-8859-1");
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return reloadableResourceBundleMessageSource;
    }
}
