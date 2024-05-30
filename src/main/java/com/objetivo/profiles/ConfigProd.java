package com.objetivo.profiles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("prod")
public class ConfigProd {

    public ConfigProd() {
        log.info("Profile de Produção");
    }

}
