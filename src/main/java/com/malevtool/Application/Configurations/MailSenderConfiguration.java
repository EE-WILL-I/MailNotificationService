package com.malevtool.Application.Configurations;

import Utils.Properties.PropertyReader;
import Utils.Properties.PropertyType;
import com.malevtool.Application.MailService.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MailSenderConfiguration {
    @Bean
    @Scope("singleton")
    public MailSender mailSender() {
        return new MailSender(PropertyReader.getProperties(PropertyType.MAILSERVICE));
    }
}
