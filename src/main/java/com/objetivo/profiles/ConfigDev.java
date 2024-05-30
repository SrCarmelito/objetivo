package com.objetivo.profiles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class ConfigDev implements ApplicationRunner {

    @Value("${hello}")
    String hello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(hello);
    }

}
