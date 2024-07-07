package com.objetivo.converter;

import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaDTOConverter implements DTOConverter<Pessoa, PessoaDTO> {

    private ModelMapper modelMapper;

    public PessoaDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Pessoa from(PessoaDTO dto) {
        return modelMapper.map(dto, Pessoa.class);
    }

    @Override
    public PessoaDTO to(Pessoa entity) {
        return modelMapper.map(entity, PessoaDTO.class);
    }

}
