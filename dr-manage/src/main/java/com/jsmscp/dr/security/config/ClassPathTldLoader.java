package com.jsmscp.dr.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

public class ClassPathTldLoader {
    private static final String SECURITY_TLD = "/META-INF/security.tld";
    private final List<String> classPathTlds;
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public ClassPathTldLoader(String... classPathTlds) {
        super();
        if (classPathTlds.length == 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @PostConstruct
    public void loadClassPathTld() {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }

    @Autowired
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }
}
