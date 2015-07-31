package com.esha.urlmon.aws;

import com.esha.urlmon.aws.AWSResourceQueueProcessorIT.MessageListener;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableStackConfiguration
public class IntegrationTestConfiguration {

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }
}
