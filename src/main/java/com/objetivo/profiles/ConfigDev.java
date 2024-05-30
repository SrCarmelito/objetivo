package com.objetivo.profiles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class ConfigDev {

    public ConfigDev() {
        log.info("Profile de Dev");
    }

}
