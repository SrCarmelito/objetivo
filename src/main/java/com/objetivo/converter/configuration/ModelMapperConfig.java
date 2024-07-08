package com.objetivo.converter.configuration;

import com.objetivo.converter.EnderecoDTOConverter;
import com.objetivo.converter.PessoaDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PessoaDTOConverter pessoaDTOConverter() {
        return new PessoaDTOConverter();
    }

    @Bean
    public EnderecoDTOConverter enderecoDTOConverter() {
        return new EnderecoDTOConverter();
    }

}
