package com.objetivo.converter.configuration;

import com.objetivo.converter.EnderecoDTOConverter;
import com.objetivo.converter.PessoaDTOConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PessoaDTOConverter pessoaDTOConverter() {
        return new PessoaDTOConverter();
    }

    @Bean
    public EnderecoDTOConverter enderecoDTOConverter() {
        return new EnderecoDTOConverter();
    }

}
