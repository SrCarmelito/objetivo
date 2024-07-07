package com.objetivo.converter;

import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import org.modelmapper.ModelMapper;

public class EnderecoDTOConverter implements DTOConverter<Endereco, EnderecoDTO> {

    private ModelMapper modelMapper;

    public EnderecoDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Endereco from(EnderecoDTO dto) {
        return modelMapper.map(dto, Endereco.class);
    }

    @Override
    public EnderecoDTO to(Endereco entity) {
        return modelMapper.map(entity, EnderecoDTO.class);
    }
}
