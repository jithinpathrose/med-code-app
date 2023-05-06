package com.code.app.config;


import com.code.app.service.MedCodeService;
import com.code.app.service.MedCodeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.code.app")
public class ApplicationConfiguration {

    @Bean
    public MedCodeService service() {
        return new MedCodeServiceImpl();
    }
}
