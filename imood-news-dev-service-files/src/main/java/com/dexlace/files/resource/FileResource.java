package com.dexlace.files.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@Component
@PropertySource("classpath:file-${spring.profiles.active}.properties")
//@PropertySource("classpath:file-dev.properties")
@ConfigurationProperties(prefix = "file")
public class FileResource {

    private String host;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
}

