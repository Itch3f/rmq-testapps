package com.dmrg.rmqsender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class AppConfig {

	@Value("${rmqserver}")
    private String rmqserver;
    
    public String getRmqServer() {
        return rmqserver;
    }

}
