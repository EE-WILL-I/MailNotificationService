package com.malevtool.Application.Configurations;

import com.malevtool.Application.TerminateBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig {
    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
