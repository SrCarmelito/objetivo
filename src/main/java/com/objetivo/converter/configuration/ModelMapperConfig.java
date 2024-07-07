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
    public PessoaDTOConverter pessoaDTOConverter(ModelMapper modelMapper) {
        return new PessoaDTOConverter(modelMapper);
    }

    @Bean
    public EnderecoDTOConverter enderecoDTOConverter(ModelMapper modelMapper) {
        return new EnderecoDTOConverter(modelMapper);
    }

}
